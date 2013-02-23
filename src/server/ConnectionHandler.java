package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ConnectionHandler implements HttpHandler {

	private GameManager game;

	public ConnectionHandler(GameManager game) {
		this.game = game;
	}

	public void handle(HttpExchange t) {

		InputStream is; //used for reading in the request data
		OutputStream os; //used for writing out the response data
		String request = "";
		String response = "";

		String uri = t.getRequestURI().getPath();
		String requestMethod = t.getRequestMethod();

		//we only care about POST requests, everything else should be ignored
		if (requestMethod.equalsIgnoreCase("POST")) {

			try {
				StringBuilder buf = new StringBuilder();
				is = t.getRequestBody();
				int rByte;
				while ((rByte = is.read()) != -1) {
					buf.append((char) rByte);
				}
				is.close();

				if (buf.length() > 0) {
					request = URLDecoder.decode(buf.toString(), "UTF-8");
				} else {
					request = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			if(Debugger.DEBUG){
				System.err.println("We recieved something other then a POST request");
			}
			ServerBootstrap.killServer(); //everything else kills the server for now
		}
		
		
		if(Debugger.DEBUG){
			System.out.println(request);
		}

		StringBuilder buf = new StringBuilder(); // put the response text in this buffer to be sent out at the end
		int httpResponseCode = 200; // This is where the HTTP response code to send back to the client should go
		
		/*
		 * code to respond to each type of request goes here
		 * 
		 * request is a String that hold JSON (or not) data to be parsed and acted upon
		 * uri is a String that holds the request path (disregards http://hostname:port)
		 * buf is a StringBuilder that you write your return data to in JSON format
		 * httpResponseCode is an int that holds what response code you want to return
		 */
		
		if (uri.equals("/connect")) {
			JSONObject playerData = game.doConnect();
			buf.append(playerData.toString());
			if(Debugger.DEBUG){
				System.out.println(playerData.toString());
			}
		} else if (uri.equals("/game/status")) {
			
			try {
				buf.append(game.getStatus(new JSONObject(request)).toString());
			} catch (JSONException e) {
				e.printStackTrace(); // something bad happened
			}
			
		} else if (uri.equals("/game/move")) {
			
			try{
				buf.append(game.doCommand(new JSONObject(request)));
			}catch(JSONException e){
				e.printStackTrace(); // something bad happened
			}
			

		} else {
			if(Debugger.DEBUG){
				System.out.println("Recieved: " + request + " for: " + uri);
			}
		}

		

		//this section sends back the return data
		try {
			response = buf.toString();
			
			if(Debugger.DEBUG){
				System.out.println("Response: " + response);
			}
			
			t.sendResponseHeaders(httpResponseCode, response.length());
			os = t.getResponseBody();
			os.write(response.getBytes());

			os.close();
			t.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}