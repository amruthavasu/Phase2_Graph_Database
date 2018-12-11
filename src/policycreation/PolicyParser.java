package policycreation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import DBUtils.GraphNode;
import DBUtils.Relationship;

public class PolicyParser {
	public static void main(String args[]) {
		File folder = new File("F:\\Eclipse\\Workspace\\PhaseTwo\\test\\policies\\IIA001Policy.xml");
		//for (File file : folder.listFiles()) {
			parse(folder);
		//}
		System.out.println("Done");
	}

	public static void parse(File file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(file);
			System.out.println("INFO: parsing file " + file);
			List<Node> nodes = new ArrayList<Node>();
			try {
				// Get subject from request
				System.out.println("INFO: Parsing subject attributes");
				Element subject = (Element) doc.getElementsByTagName("Subject").item(0);
				Map<String, String> subjectAttributes = new HashMap<String, String>();
				
				Element attrDesignator = (Element) subject.getElementsByTagName("SubjectAttributeDesignator").item(0);
				Element attributeValue = (Element) subject.getElementsByTagName("AttributeValue").item(0);
				subjectAttributes.put(attrDesignator.getAttribute("AttributeId").split("\\:")[7].replace("-", ""), attributeValue.getTextContent());
				
				
				Node node = new Node("Subject", subjectAttributes);
				nodes.add(node);
			} catch (Exception e) {
				System.out.println("No subject");
				e.printStackTrace();
			}
			
			try {
				// Get resource from request
				System.out.println("INFO: Parsing resource attributes");
				Element resource = (Element) doc.getElementsByTagName("Resource").item(0);
				Map<String, String> resourceAttributes = new HashMap<String, String>();
					Element attrDesignator = (Element) resource.getElementsByTagName("ResourceAttributeDesignator").item(0);
					Element attributeValue = (Element) resource.getElementsByTagName("AttributeValue").item(0);
					String attrId[] = attributeValue.getTextContent().split("\\:");
					System.out.println(attrId.length);
					resourceAttributes.put(attrDesignator.getAttribute("AttributeId").split("\\:")[7].replace("-", ""), attributeValue.getTextContent());
				
				
				Node node = new Node("Resource", resourceAttributes);
				nodes.add(node);
			} catch (Exception e) {
				System.out.println("No resource");
			}
			
			try {
				// Get action from request
				System.out.println("INFO: Parsing action attributes");
				NodeList actionElements = doc.getElementsByTagName("Action");
				for (int i=0; i<actionElements.getLength(); i++) {
					Element action = (Element) actionElements.item(i);
					Map<String, String> actionAttributes = new HashMap<String, String>();
						Element attrDesignator = (Element) action.getElementsByTagName("ActionAttributeDesignator").item(0);
						Element attributeValue = (Element) action.getElementsByTagName("AttributeValue").item(0);
						actionAttributes.put(attrDesignator.getAttribute("AttributeId").split("\\:")[7].replace("-", ""), attributeValue.getTextContent());
					Node node = new Node("Action", actionAttributes);
					nodes.add(node);
				}
				
			} catch (Exception e) {
				System.out.println("No action");
			}
			
			try {
				// Get environment from request
				System.out.println("INFO: Parsing environment attributes");
				Element environment = (Element) doc.getElementsByTagName("Environment").item(0);
				Map<String, String> envAttributes = new HashMap<String, String>();
					Element attrDesignator = (Element) environment.getElementsByTagName("ResourceAttributeDesignator").item(0);
					Element attributeValue = (Element) environment.getElementsByTagName("AttributeValue").item(0);
					envAttributes.put(attrDesignator.getAttribute("AttributeId").split("\\:")[7].replace("-", ""), attributeValue.getTextContent());
				
				Node node = new Node("Environment", envAttributes);
				nodes.add(node);
			} catch (Exception e) {
				System.out.println("No environment");
			}
			
			// Get policy attributes
			try {
				System.out.println("INFO: Getting policy attributes");
				Element policy = (Element) doc.getElementsByTagName("Policy").item(0);
				Map<String, String> policyMap = new HashMap<String, String>();
				policyMap.put("id", policy.getAttribute("PolicyId"));
				policyMap.put("combiningAlg", policy.getAttribute("RuleCombiningAlgId"));
				Node policyNode = new Node("Policy", policyMap);
				
				Element rule = (Element) doc.getElementsByTagName("Rule").item(0);
				policyNode.addAttribute("effect", rule.getAttribute("Effect"));
				GraphNode.createNode(policyNode.getType(), policyNode.getAttributes());
				// Add nodes to database
				
				for (Node node : nodes) {
					GraphNode.createNode(node.getType(), node.getAttributes());
					Relationship.createRelationship("ASSOCIATED_WITH", policyNode.getType(), node.getType(), policyNode.getAttributes(), node.getAttributes());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
