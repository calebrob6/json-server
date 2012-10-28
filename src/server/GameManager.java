package server;

import game.GenGame;
import game.Simplexity;

import java.util.ArrayList;

import org.json.JSONObject;

public class GameManager {

	public static final int MAX_PLAYERS = 2;
	private ArrayList<Game> games = new ArrayList<Game>();
	private Class<GenGame> gameType;
	
	public GameManager(Class class1){
		this.gameType = class1;
		
	}
	


	//will connect the player to the next available game or creates one if all games are full
	public int doConnect(){
		
		JSONObject connectResponse;
		boolean connected = false;
		
		for(int i=0;i<games.size();i++){
			Game g = games.get(i);
			if(g.getNumPlayers().get()< MAX_PLAYERS){
				connectResponse = g.connectPlayer(i);
				connected = true;
			}
		}
		
		if(!connected){
			
			// create new Game of the type that was passed to the game Manager
			try {
				games.add(new Game(gameType.newInstance()));
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			connectResponse = games.get(games.size()-1).connectPlayer();
		}
		
		
		return 0;
		
	}




	public Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}




	public Object doCommand(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
