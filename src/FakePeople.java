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


public  class FakePeople extends Thread {
	
	private int numberOfPeople;
	private static int count;
	private static List<Person> listOfFakePeople;
	
	public FakePeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
		if(listOfFakePeople == null) {
			listOfFakePeople = new ArrayList<Person>();
		}
	}
	
	public void run() {

		WebClient webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = null;
		List<Person> people = new ArrayList<Person>();
		try {
			page = webClient.getPage("http://www.fakenamegenerator.com");
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(page != null) {
			for(int i = 0; i < this.numberOfPeople; i++) { 
				//sometimes can throw IndexOutOfBoundsException, I guess it happens if
				// getByXPath returns an empty whatever it is returning. -g
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
				while(!containsUpp(splitAddress[index])) {
					address += " " + splitAddress[index];
					index++;
				}
				address += " " + cutAdd(splitAddress[splitAddress.length - 1]);
				
				String zipCode = (splitAdd[1].trim().split("\\s+")[1]);
				

				HtmlDefinitionDescription webEmail = (HtmlDefinitionDescription) page.getByXPath("//div[@class='extra']//dl//dd").get(8);
				String email = (webEmail.getTextContent().split("\\s+")[0]);
				
				
				
				Person target = new Person (
						fName,
						lName,
						email,
						address,
						zipCode
				);
				
				listOfFakePeople.add(target);
				people.add(target);
				
				System.out.println(target);
				
				count++;
				System.out.println(count + " people done");
				
		        HtmlForm form = page.getForms().get(0);
		        HtmlSubmitInput button = form.getInputByValue("Generate");

		        try {
					page = button.click();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//		System.out.println(people);
//		System.out.println(this.getName() + " is done");
		this.interrupt();
		
	}
	
	private String cutAdd(String s) {
		if(s.length() > 2) {
			String result = "" + s.charAt(0);
			for(int i = 1; s.charAt(i) != Character.toUpperCase(s.charAt(i)); i++) {
				if(s.length() == i + 1)
					return s;
				result += s.charAt(i);
			}
			/* int i = 1;
			 * while(s.charAt(i) != Character.toUpperCase(s.charAt(i))) {
				if(s.length() == i + 1)
					return s;
				result += s.charAt(i);
				i++;
			}*/
			return result;
		} else {
			return s;
		}
	}
	
	private boolean containsUpp(String s) {
		// pretty sure this works, let me know -g
		//return !s.toLowerCase().equals(s);
		
		for(int i = 1; i < s.length(); i ++) {
			if(s.charAt(i) == Character.toUpperCase(s.charAt(i))) {
				return true;
			}
		}
		return false;
		
		
	}
}
