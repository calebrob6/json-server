package server;

public class Game {
	
	private static final int MAX_PLAYERS = 2;
	
	public volatile boolean running = false;
	public Player[] players = new Player[MAX_PLAYERS];
	private int pCount = 0;
	

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
	public int setPlayers() {
		if(pCount<this.players.length){
			this.players[pCount] = new Player(pCount);
			pCount++;
			return pCount-1;
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

}
