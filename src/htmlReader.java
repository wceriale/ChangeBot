import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDefinitionDescription;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class htmlReader {

	public static void main(String[] args) throws Exception {
		
		// Remove Annoying Output
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		WebClient webClient = new WebClient();
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://www.fakenamegenerator.com");
		List<String> names = new ArrayList<String>();
		for(int i = 0; i < 2; i++) { 
			HtmlHeading3 webName = (HtmlHeading3) page.getByXPath("//div[@class='info']//div//div//h3").get(0);
	        System.out.println(webName.getTextContent());
	        HtmlForm form = page.getForms().get(0);
	        HtmlSubmitInput button = form.getInputByValue("Generate");
	        
			
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
			
			System.out.println(address);
			System.out.println(splitAdd[1].trim().split("\\s+")[1]);
			

			HtmlDefinitionDescription webEmail = (HtmlDefinitionDescription) page.getByXPath("//div[@class='extra']//dl//dd").get(8);
			System.out.println(webEmail.getTextContent().split("\\s+")[0]);
			
			System.out.println();
			
	        page = button.click();
	        
		}
		System.out.println(names);
        

//		WebResponse response = page.getWebResponse();
//		String content = response.getContentAsString();
//		System.out.println(content);

	}
	
	public static String cutAdd(String s) {
		if(s.length() > 2) {
			String r = "" + s.charAt(0);
			int i = 1;
			while(s.charAt(i) != Character.toUpperCase(s.charAt(i))) {
				if(s.length() == i + 1)
					return s;
				r += s.charAt(i);
				i++;
			}
			return r;
		} else {
			return s;
		}
	}
	
	public static boolean containsUpp(String s) {
		boolean r = false;
		for(int i = 1; i < s.length(); i ++) {
			if(s.charAt(i) == Character.toUpperCase(s.charAt(i))) {
				return true;
			}
		}
		return r;
		
	}

}
