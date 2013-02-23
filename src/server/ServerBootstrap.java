package server;

import game.Checkers;
import game.TicTacToe;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

/**
 * 
 * Main class for the server
 * 
 * @author Caleb Robinson
 * 
 */
public class ServerBootstrap {

	private static int port = 8000;
	private static int concurrentConnectionsAllowed = 4;
	private static Class gameClass = Checkers.class;

	public static void main(String[] args) {

		GameManager game = new GameManager(gameClass);
		ConnectionHandler cHandle = new ConnectionHandler(game);

		try {
			// The second arguement to HttpServer.create is the number of allowed backlogged connections
			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/", cHandle);
			server.setExecutor(Executors.newFixedThreadPool(concurrentConnectionsAllowed));
			server.start();
		} catch (BindException e) {
			if (Debugger.DEBUG) {
				e.printStackTrace();
			} else {
				System.err.println("Couldn't bind to port 8000, most likely there is another instance of this server running");
				System.err.println("Exiting");
			}
			System.exit(-1);
		} catch (IOException e) {
			if (Debugger.DEBUG) {
				e.printStackTrace();
			} else {
				System.err.println("Creating the server failed and is not a BindException, enable debugging for more information");
				System.err.println("Exiting");
			}
			System.exit(-1);
		}
	}

	public static void killServer() {
		if (Debugger.DEBUG) {
			System.out.println("Bye");
		}
		System.exit(0);
	}

}
