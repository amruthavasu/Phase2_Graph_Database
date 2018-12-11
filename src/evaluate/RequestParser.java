package evaluate;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;


public class RequestParser {
	public static Map<String, String> parseRequest(File request) {
		Map<String, String> requestAttributes = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(request);
			
			try {
				// Get subject from request
				Element subject = (Element) doc.getElementsByTagName("subject").item(0);
				Element subjectValue = (Element) subject.getElementsByTagName("AttribteValue").item(0);
				requestAttributes.put("subject", subjectValue.getTextContent());
			} catch (Exception e) {
				
			}
			
			try {
				// Get resource from request
				Element resource = (Element) doc.getElementsByTagName("resource").item(0);
				Element resourceValue = (Element) resource.getElementsByTagName("AttribteValue").item(0);
				requestAttributes.put("subject", resourceValue.getTextContent());
			} catch (Exception e) {
				
			}
			
			try {
				// Get action from request
				Element action = (Element) doc.getElementsByTagName("action").item(0);
				Element actionValue = (Element) action.getElementsByTagName("AttribteValue").item(0);
				requestAttributes.put("subject", actionValue.getTextContent());
			} catch (Exception e) {
				
			}
			
			try {
				// Get environment from request
				Element environment = (Element) doc.getElementsByTagName("environment").item(0);
				Element value = (Element) environment.getElementsByTagName("AttribteValue").item(0);
				requestAttributes.put("subject", value.getTextContent());
			} catch (Exception e) {
				
			}
			
			return requestAttributes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
