package combine;

import java.util.List;

public class PermitOverrides extends CombiningAlgorithm {

	public PermitOverrides(String identifer) {
		super(identifer);
	}

	@Override
	public String combine(List<String> results) {
		for (String result : results) {
			if (result.contains("permit")) {
				return "permit";
			}
		}
		return "deny";
	}

}
