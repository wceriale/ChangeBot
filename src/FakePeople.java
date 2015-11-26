/**
 * 
 */

/**
 * @author Will
 * @author Garrett
 *
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDefinitionDescription;
import com.gargoylesoftware.htmlunit.html.HtmlDefinitionList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class FakePeople implements Runnable {

	private int numberOfPeople;
	public static List<Person> listOfFakePeople;
	// Needs to be public so our main can access

	// Takes number of fake people to generate
	public FakePeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
		listOfFakePeople = new ArrayList<Person>();
	}

	// @Overide
	public void run() {

		WebClient webClient = new WebClient();

		webClient.getOptions().setThrowExceptionOnScriptError(false);
		// Prevents exceptions being printed to console

		HtmlPage page = null;
		List<Person> people = new ArrayList<Person>();
		try {
			page = webClient.getPage("http://www.fakenamegenerator.com");
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Issue with loading page from url");
			e.printStackTrace();
		}
		if (page != null) {
			for (int i = 0; i < this.numberOfPeople; i++) {
				// sometimes can throw IndexOutOfBoundsException, I guess it
				// happens if
				// getByXPath returns an empty whatever it is returning. -g

				// Grabs HTML element containing the name of the individual
				HtmlHeading3 webName = (HtmlHeading3) page.getByXPath("//div[@class='info']//div//div//h3").get(0);

				// Parses first/last name from the given html element
				String name = webName.getTextContent().trim();
				String[] fullName = name.split("\\s+");
				String fName = fullName[0];
				String lName = fullName[fullName.length - 1];
				

				// Grabs HTML element containing address information
				HtmlDivision webAdd = (HtmlDivision) page.getByXPath("//div[@class='info']//div//div//div").get(0);
				String fullAddress = webAdd.getTextContent().trim();

				// Parses address information
				String[] splitAdd = fullAddress.split(",");
				String[] splitAddress = splitAdd[0].trim().split("\\s+");
				String address = splitAddress[0]; // Sets address to "Street Address"

				// Hacky way to deal with removing Ciy from Address
				int index = 1;
				while (!containsUpp(splitAddress[index])) {
					address += " " + splitAddress[index];
					index++;
				}
				address += " " + cutAdd(splitAddress[splitAddress.length - 1]);

				// Parses and stores Zip Code from full address.
				String zipCode = (splitAdd[1].trim().split("\\s+")[1]);

				
				
				// Grabs and Parses email from HTML
				HtmlDefinitionDescription webEmail = (HtmlDefinitionDescription) page
						                             	.getByXPath("//div[@class='extra']//dl//dd").get(8);
				String email = (webEmail.getTextContent().split("\\s+")[0]);

				// Adds person to static list and local list
				Person target = new Person(fName, lName, email, address, zipCode);
				listOfFakePeople.add(target);
				people.add(target);

				// DEBUGGING CODE
				//
				// System.out.println(count + " people done");
				// count++;
				// Person target = new Person(fName, lName, email, address,
				// zipCode);
				// System.out.println(target);

				// Attempts to push "generate" button to get new fake person
				HtmlForm form = page.getForms().get(0);
				HtmlSubmitInput button = form.getInputByValue("Generate");
				try {
					page = button.click();
				} catch (IOException e) {
					System.out.println("Issue with button");
					e.printStackTrace();
				}
			}
		}

		// DEBUG CODE
		//
		// System.out.println(people);
		// System.out.println(this.getName() + " is done");

		// Stops the thread from continuing
		// ^ Not necessarily true, doesn't end immediately
		return;

	}

	// Given a String, returns everything before the
	// Second capitol letter
	private String cutAdd(String s) {
		if (s.length() > 2) {
			String result = "" + s.charAt(0);
			for (int i = 1; i < s.length() && 
					s.charAt(i) != Character.toUpperCase(s.charAt(i)); i++) {
				result += s.charAt(i);
			}
			return result;
		} else {
			return s;
		}
	}

	// Given a String, checks if it contains an uppercase letter
	// Ignores first letter
	private boolean containsUpp(String s) {
		if (!(s == null || s.isEmpty())) {
			s = s.substring(1); // Ignore first character
			return !s.toLowerCase().equals(s); // Garrett you're a genius
		}
		return false;
	}
}
