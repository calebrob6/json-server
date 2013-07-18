package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;


import com.sun.net.httpserver.Headers;
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
		String requestString = "";
		JSONObject jsonRequest = new JSONObject();
		String response = "";
		
		StringBuilder responseBuffer = new StringBuilder(); // put the response text in this buffer to be sent out at the end
		int httpResponseCode = 200; // This is where the HTTP response code to send back to the client should go
		

		String uri = t.getRequestURI().getPath();
		String requestMethod = t.getRequestMethod();
		
		//We parse the GET parameters through a Filter object that is registered in ServerBootstrap
		//It is possible to parse POST parameters like this too, but I don't want to
		Map<String, Object> getParams = (Map<String, Object>) t.getAttribute("parameters");
		
		if (Debugger.DEBUG) {
			//Enumerates our GET parameters
			Set<String> keys = getParams.keySet();
			for (String key : keys) {
				System.out.println(key + " = " + getParams.get(key));
			}
		}

		//GET Requests won't have any data in the body
		if (requestMethod.equalsIgnoreCase("POST")) {

			try {
				StringBuilder requestBuffer = new StringBuilder();
				is = t.getRequestBody();
				int rByte;
				while ((rByte = is.read()) != -1) {
					requestBuffer.append((char) rByte);
				}
				is.close();

				if (requestBuffer.length() > 0) {
					requestString = URLDecoder.decode(requestBuffer.toString(), "UTF-8");
				} else {
					requestString = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			if(Debugger.DEBUG){
				System.err.println("We received something other then a POST request");
			}
		}
		
		
		if(Debugger.DEBUG){
			System.out.println("REQUEST STRING: " + requestString);
			System.out.println("REQUEST METHOD: " + requestMethod);
		}

		/*
		 * code to respond to each type of request goes here
		 * 
		 * requestString is a String that hold JSON (or not) data to be parsed and acted upon
		 * uri is a String that holds the request path (disregards http://hostname:port as well as any GET parameters)
		 * responseBuffer is a StringBuilder that you write your return data to in JSON format
		 * httpResponseCode is an int that holds what response code you want to return
		 */
		if (requestMethod.equalsIgnoreCase("POST")) {
			if (uri.equals("/connect")) {
				JSONObject playerData = game.doConnect();
				responseBuffer.append(playerData.toString());
				if(Debugger.DEBUG){
					System.out.println(playerData.toString());
				}
			} else if (uri.equals("/game/status")) {
				
				try {
					responseBuffer.append(game.getStatus(new JSONObject(requestString)).toString());
				} catch (JSONException e) {
					e.printStackTrace(); // something bad happened
				}
				
			} else if (uri.equals("/game/move")) {
				
				try{
					responseBuffer.append(game.doCommand(new JSONObject(requestString)));
				}catch(JSONException e){
					e.printStackTrace(); // something bad happened
				}
				
	
			} else {
				if(Debugger.DEBUG){
					System.out.println("Recieved: " + requestString + " for: " + uri);
				}
			}
		}

		//this section sends back the return data
		try {
			response = responseBuffer.toString();
			
			if(Debugger.DEBUG){
				System.out.println("Response: " + response);
			}
			
			Headers h = t.getResponseHeaders();
		    h.add("Content-Type", "application/jsonp; charset=UTF-8");
		    h.add("Access-Control-Allow-Origin","*");
		    h.add("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		    h.add("Access-Control-Allow-Methods","POST, GET, OPTIONS");
			
			t.sendResponseHeaders(httpResponseCode, response.length());
			os = t.getResponseBody();
			os.write(response.getBytes("UTF-8"));
			os.flush();
			os.close();
			t.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}