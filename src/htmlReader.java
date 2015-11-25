import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
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
		for(int i = 0; i < 10; i++) { 
			HtmlHeading3 webName = (HtmlHeading3) page.getByXPath("//div[@class='info']//div//div//h3").get(0);
	        names.add(webName.getTextContent());
	        HtmlForm form = page.getForms().get(0);
	        HtmlSubmitInput button = form.getInputByValue("Generate");
	        page = button.click();
		}
		System.out.println(names);
        

//		WebResponse response = page.getWebResponse();
//		String content = response.getContentAsString();
//		System.out.println(content);

	}

}
