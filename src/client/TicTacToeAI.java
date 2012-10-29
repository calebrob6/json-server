package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class TicTacToeAI {

	public static final String host = "http://localhost:8000";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws JSONException {

		
		int id,gId,auth;
		
		JSONObject nullObject = new JSONObject("{}");
		
		JSONObject playerInfo = doRequest(nullObject,"/connect");
		id = playerInfo.getInt("ID");
		gId = playerInfo.getInt("GAMEID");
		auth = playerInfo.getInt("AUTH");
		
		

	}

	public static JSONObject doRequest(JSONObject req, String urlPath) {
		String response = "";
		JSONObject resp = null;
		String data;
		try {
			data = URLEncoder.encode(req.toString(), "UTF-8");
			// Send data
			URL url = new URL(host + urlPath);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response+=line;
				System.out.println(line);
			}
			wr.close();
			rd.close();
			resp = new JSONObject(response);

		} catch (UnsupportedEncodingException e) {  //PRO error handling!

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		} catch (JSONException e) {
			
		}
		
		return resp;

	}

}
