package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ConnectionHandler2 implements HttpHandler {



	public void handle(HttpExchange t) {

		InputStream is;
		OutputStream os;
		String request = "";
		String response = "";
		JSONObject jsonRequest = new JSONObject();
		
		//default http response code is 200 OKAY
		
		StringBuilder buf = new StringBuilder(); // put the response text in this buffer to be sent out at the end
		int httpResponseCode = 200; // This is where the HTTP response code to send back to the client should go
		
		String uri = t.getRequestURI().getPath();
		String requestMethod = t.getRequestMethod();

		try {
			StringBuilder requestBuf = new StringBuilder();
			is = t.getRequestBody();
			int rByte;
			while ((rByte = is.read()) != -1) {
				requestBuf.append((char) rByte);
			}
			is.close();

			if (requestBuf.length() > 0) {
				request = URLDecoder.decode(requestBuf.toString(), "UTF-8");
			} else {
				request = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		if (requestMethod.equalsIgnoreCase("POST")) {
			System.out.println("POST request");
			try{
				jsonRequest = new JSONObject(request);
			}catch(JSONException e){
				System.err.println("Invalid JSON data");
			}
		
		} else if (requestMethod.equalsIgnoreCase("GET")) {
			if(Debugger.DEBUG){
				System.out.println("GET request recieved");
				System.out.println(t.getRequestURI().getRawQuery());
			}
		}

		if (Debugger.DEBUG) {
			System.out.println(request);
		}
		

		if (uri.equals("/connect")) {
			System.out.println("Attempting connection");
			//buf.append(tfsHandler.doConnect(jsonRequest));
			
		} else {
			if (Debugger.DEBUG) {
				System.out.println("Recieved: " + request + " for: " + uri);
			}
		}


		try {
			response = buf.toString();
			
			if (Debugger.DEBUG) {
				System.out.println("Response: " + response);
			}

			Headers h = t.getResponseHeaders();
			h.add("Content-Type", "application/jsonp; charset=UTF-8");	
			//h.add("Content-Type", "text/plain; charset=UTF-8");
			h.add("Access-Control-Allow-Origin", "*");

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