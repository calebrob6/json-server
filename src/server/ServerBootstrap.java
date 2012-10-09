package server;
import handlers.ConnectionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class ServerBootstrap {

	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws IOException {

		Game game = new Game();
		ConnectionHandler cHandle = new ConnectionHandler(game);
		
		
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/connect", cHandle);
		server.createContext("/game/status", cHandle);
		server.createContext("/game/move", cHandle);
		server.setExecutor(new GameExecutor());
		server.start();

	}
	
	
	public static void killServer(){
		System.out.println("Bye");
		System.exit(0);
	}

}
