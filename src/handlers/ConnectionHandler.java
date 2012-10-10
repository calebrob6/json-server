package handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;

import server.Game;
import server.ServerBootstrap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ConnectionHandler implements HttpHandler {

	private Game game;

	public ConnectionHandler(Game game) {
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
			ServerBootstrap.killServer(); //everything else kills the server for now
		}
		System.out.println(request);

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
			JSONObject playerData = game.connectPlayer();
			buf.append(playerData.toString());
			System.out.println(playerData.toString());
		} else if (uri.equals("/game/status")) {
			buf.append(game.getStatus().toString());
		} else if (uri.equals("/game/move")) {
			try{
				buf.append(game.doCommand(new JSONObject(request)));
			}catch(JSONException e){
				e.printStackTrace(); // something bad happened
			}

		} else {
			System.out.println("Recieved: " + request + " for: " + uri); //Not a default command
		}

		

		//this section sends back the return data
		try {
			response = buf.toString();
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