package handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import server.Game;
import server.ServerBootstrap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class ConnectionHandler implements HttpHandler {
	
	private Game game;
	
	
	public ConnectionHandler(Game game) {
		this.game = game;
	}
	
	
	public void handle(HttpExchange t) {

		InputStream is;
		OutputStream os;
		String request, response;
		int rByte;

		try {

			String requestMethod = t.getRequestMethod();

			if (requestMethod.equalsIgnoreCase("POST")) {

				StringBuilder buf = new StringBuilder();
				is = t.getRequestBody();
				while ((rByte = is.read()) != -1) {
					buf.append((char) rByte);
				}
				is.close();

				if (buf.length() > 0) {
					request = URLDecoder.decode(buf.toString(), "UTF-8");
				} else {
					request = null;
				}

			} else {
				ServerBootstrap.killServer();
			}

			StringBuilder buf = new StringBuilder();

			buf.append("Request recieved");

			response = buf.toString();
			t.sendResponseHeaders(200, response.length());
			os = t.getResponseBody();
			os.write(response.getBytes());

			os.close();
			t.close();
		} catch (IOException e) { // Something happened
			e.printStackTrace();
		}
	}

}