package client;

import org.json.*;

public class JSONTest {

	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException {
		
		String test = "{\"string\": \"JSON\",\"integer\": \"1\",\"double\": \"2.0\",\"boolean\": \"true\" }";
		JSONObject a = new JSONObject(test);
		
		System.out.println(a.get("string"));
		
	}

}
