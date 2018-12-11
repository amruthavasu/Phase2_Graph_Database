package evaluate;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Result {
	private String result;
	private String status;

	public Result(String result) {
		this.result = result;
		if (result != null) {
			status = ResultUtils.STATUS_OK;
		}
	}
	
	public Result (String result, String status) {
		this.result = result;
		this.status = status;
	}

	public String createResult() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			// Create response element
			Element rootElement = doc.createElement("Response");
			rootElement.setAttribute("xmlns", "urn:oasis:names:tc:xacml:2.0:context:schema:os");
			rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElement.setAttribute("xsi:schemaLocation", "urn:oasis:names:tc:xacml:2.0:context:schema:os" + 
					"access_control-xacml-2.0-context-schema-os.xsd");
			doc.appendChild(rootElement);
			
			// Create result element
			Element result = doc.createElement("Result");
			rootElement.appendChild(result);
			
			// Create decision element
			Element decision = doc.createElement("Decision");
			decision.setTextContent(this.result);
			result.appendChild(decision);
			
			// Create status element
			Element status = doc.createElement("Status");
			result.appendChild(status);
			
			// Create status code
			Element statusCode = doc.createElement("StatusCode");
			statusCode.setAttribute("Value", this.status);
			status.appendChild(statusCode);
			
			// Get document as String
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
			
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public String getStatus() {
		return this.status;
	}
}
