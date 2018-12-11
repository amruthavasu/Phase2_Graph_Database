package policycreation;

import java.util.HashMap;
import java.util.Map;

public class Node {
	private String type;
	private Map<String, String> attributes;
	
	public Node (String type, Map<String, String> attributes) {
		this.type = type;
		this.attributes = attributes;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Map<String, String> getAttributes() {
		return this.attributes;
	}
	
	public void addAttribute(String key, String value) {
		this.attributes.put(key, value);
	}
}
