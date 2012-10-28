package server;
import game.Simplexity;
import handlers.ConnectionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class ServerBootstrap {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		GameManager game = new GameManager(Simplexity.class);
		ConnectionHandler cHandle = new ConnectionHandler(game);
		
	
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/", cHandle);
		server.setExecutor(Executors.newFixedThreadPool(4));
		server.start();

	}
	
	
	public static void killServer(){
		System.out.println("Bye");
		System.exit(0);
	}

}
