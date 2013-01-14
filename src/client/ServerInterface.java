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

public class ServerInterface {

	private String host = "";

	public ServerInterface(String hostname, int port) {
		this.host = "http://" + hostname + ":" + port;
	}

	/**
	 * 
	 * This is the method that you will use to do all of the calls to the server with
	 * 
	 * @param req The JSONObject you want to send to the server
	 * @param urlPath The path on the server to send it to ex: "/connect" or "/game/status"
	 * @return The response from the server as a JSONObject or a null object if something went wrong
	 */
	public JSONObject doRequest(JSONObject req, String urlPath) {
		String response = "";
		JSONObject resp = null;
		String data;
		try {
			data = URLEncoder.encode(req.toString(), "UTF-8");
			// Send data
			URL url = new URL(host + urlPath);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response += line;
				// System.out.println(line);
			}
			wr.close();
			rd.close();
			resp = new JSONObject(response);

		} catch (UnsupportedEncodingException e) { // PRO error handling!

		} catch (MalformedURLException e) { // PRO error handling!

		} catch (IOException e) { // PRO error handling!

		} catch (JSONException e) { // PRO error handling!

		}

		return resp;
	}

}
