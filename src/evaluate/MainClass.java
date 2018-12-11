package evaluate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DBUtils.Execute;

public class MainClass {
	public static void main(String args[]) {
		File dir = new File("F:\\Eclipse\\Workspace\\PhaseTwo\\test\\requests");
		for (File file : dir.listFiles()) {
			System.out.println(PDP(file));
		}
	}

	public static Result PDP(File file) {
		try {
			List<String> effects = new ArrayList<String>();
			Map<String, String> reqAttr = RequestParser.parseRequest(file);
			for (String str : reqAttr.keySet()) {
				effects.add(getRuleEffect(str, reqAttr.get(str)));
			}
			return new Result(Combine.combine(effects));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRuleEffect(String type, String value) {
		String query = "Match (s:" + type + ")-[n]->(r:Rule) where s.value=\'" + value + "\' return r.effect";
		return Execute.execute(query, "r.effect");
	}
}
