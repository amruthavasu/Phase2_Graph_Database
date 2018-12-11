package combine;

import java.util.List;

public class DenyOverrides extends CombiningAlgorithm {

	public DenyOverrides(String identifer) {
		super(identifer);
	}

	@Override
	public String combine(List<String> results) {
		for (String result : results) {
			if (result.contains("deny")) {
				return "deny";
			}
		}
		return "permit";
	}

}
