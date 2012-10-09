package server;

import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

public class Game {
	
	private static final int MAX_PLAYERS = 2;
	
	public volatile boolean running = false;
	public Player[] players = new Player[MAX_PLAYERS];
	private AtomicInteger pCount = new AtomicInteger(0);
	

	public Game() {
		for(int i=0;i<MAX_PLAYERS;i++){
			players[i]=null;
		}
	}
	
	public Game(Player players[]) throws PlayerException {
		if(players.length<=this.players.length){
			this.players = players;
		}else{
			throw new PlayerException("Player array too large");
		}
	}


	/**
	 * @return the players
	 */
	public Player getPlayer(int i) {
		if(i<players.length){
			return players[i];
		}else{
			return null;
		}
	}

	/**
	 * @param players the players to set
	 */
	public int addPlayer() {
		if(pCount.get()<this.players.length){
			this.players[pCount.get()] = new Player(pCount.get());
			pCount.addAndGet(1);
			return pCount.get()-1;
		}else{
			return -1;
		}
	}
	
	
	class PlayerException extends Exception {
		private static final long serialVersionUID = 0L;

		public PlayerException(String msg){
			super(msg);
		}
	}


	public JSONObject connectPlayer() {
		int me = addPlayer();
		while(pCount.get()!=MAX_PLAYERS){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return players[me].toJSON();
		
	}

}
