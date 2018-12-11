package evaluate;
import java.util.List;

public class Combine {
	public static String combine(List<String> results) {
		for (String result : results) {
			if (result.equals("deny")) {
				return ResultUtils.DENY;
			}
		}
		return ResultUtils.PERMIT;
	}
}
