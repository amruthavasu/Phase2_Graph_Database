package combine;

import java.util.List;

public abstract class CombiningAlgorithm {
	private String identifer;
	
	public CombiningAlgorithm(String identifer) {
		this.identifer = identifer;
	}
	
	public abstract String combine(List<String> results);
	
	public String getIdentifer() {
		return this.identifer;
	}
}
