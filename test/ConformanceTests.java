import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import evaluate.MainClass;
import evaluate.Result;

public class ConformanceTests {

	@Test
	public void test() {
		File dir = new File(TestUtils.REQUEST_DIRECTORY);
		for (File file : dir.listFiles()) {
			String policyId = file.getName().split("Request")[0];
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.println("Starting test for " + policyId);
			Result actualResult = MainClass.PDP(file);
			File responseFile = new File(TestUtils.RESPONSE_DIRECTORY + File.separator + "IIA001" + TestUtils.RESPONSE_EXT);
			Result expectedResult = parseResponse(responseFile);
			Assert.assertEquals(expectedResult.getResult(), actualResult.getResult());
			Assert.assertEquals(expectedResult.getStatus(), actualResult.getStatus());
			System.out.println("Test result: Passed!");
		}
	}
	
	public Result parseResponse(File file) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file.getAbsolutePath());
			Element decision = (Element) doc.getElementsByTagName("Decision").item(0);
			String result = decision.getTextContent();
			Element statusCode = (Element) doc.getElementsByTagName("StatusCode").item(0);
			String status = statusCode.getAttribute("Value");
			return new Result(result, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
