/**
 * 
 */

/**
 * @author Will
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

public class FakePeople extends Thread {

	int numberOfPeople;
	static int count;
	static List<Person> listOfFakePeople;
	static boolean done;

	public FakePeople(int numb) {
		this.numberOfPeople = numb;
		listOfFakePeople = new ArrayList<Person>();
		done = false;
	}

	public void run() {
		// for(int i = 0; i < lines.length; i++) {
		// System.out.println(lines[i]);
		// }
		// System.out.print(this.getName() + " is done");
		// this.interrupt();

		WebClient webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page;
		List<Person> people = new ArrayList<Person>();
		try {
			page = webClient.getPage("http://www.fakenamegenerator.com");
			for (int i = 0; i < this.numberOfPeople; i++) {
				HtmlHeading3 webName = (HtmlHeading3) page.getByXPath("//div[@class='info']//div//div//h3").get(0);
				String name = webName.getTextContent().trim();
				String[] fullName = name.split("\\s+");

				String fName = fullName[0];
				String lName = fullName[fullName.length - 1];

				HtmlDivision webAdd = (HtmlDivision) page.getByXPath("//div[@class='info']//div//div//div").get(0);
				String fullAddress = webAdd.getTextContent().trim();

				String[] splitAdd = fullAddress.split(",");
				String[] splitAddress = splitAdd[0].trim().split("\\s+");
				String address = splitAddress[0];
				int index = 1;
				while (!containsUpp(splitAddress[index])) {
					address += " " + splitAddress[index];
					index++;
				}
				address += " " + cutAdd(splitAddress[splitAddress.length - 1]);

				String zipCode = (splitAdd[1].trim().split("\\s+")[1]);

				HtmlDefinitionDescription webEmail = (HtmlDefinitionDescription) page
						.getByXPath("//div[@class='extra']//dl//dd").get(8);
				String email = (webEmail.getTextContent().split("\\s+")[0]);

				// System.out.println(count + " people done");
				// count++;

				listOfFakePeople.add(new Person(fName, lName, email, address, zipCode));
				people.add(new Person(fName, lName, email, address, zipCode));

				//Person target = new Person(fName, lName, email, address, zipCode);

				// System.out.println(target);

				HtmlForm form = page.getForms().get(0);
				HtmlSubmitInput button = form.getInputByValue("Generate");

				try {
					page = button.click();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(people);
		// System.out.println(this.getName() + " is done");
		this.interrupt();

	}

	public String cutAdd(String s) {
		if (s.length() > 2) {
			String r = "" + s.charAt(0);
			int i = 1;
			while (s.charAt(i) != Character.toUpperCase(s.charAt(i))) {
				if (s.length() == i + 1)
					return s;
				r += s.charAt(i);
				i++;
			}
			return r;
		} else {
			return s;
		}
	}

	public boolean containsUpp(String s) {
		boolean r = false;
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == Character.toUpperCase(s.charAt(i))) {
				return true;
			}
		}
		return r;

	}
}
