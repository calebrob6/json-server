package server;

import game.GenGame;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class GameManager {

	public static final int MAX_PLAYERS = 2;
	private ArrayList<Game> games = new ArrayList<Game>();
	private Class<GenGame> gameType;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GameManager(Class class1){
		System.out.println("GameManager running");
		this.gameType = class1;
		
	}
	


	//will connect the player to the next available game or creates one if all games are full
	//TODO add error checking on connectResposne to see if it actually returned anything
	public JSONObject doConnect(){
		
		JSONObject connectResponse = null;
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
			connectResponse = games.get(games.size()-1).connectPlayer(games.size()-1);
		}
		
		
		return connectResponse;
		
	}




	public Object getStatus(JSONObject a) {
		try {
			return games.get(a.getInt("GAMEID")).getStatus();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}




	public JSONObject doCommand(JSONObject a) {
		try {
			return games.get(a.getInt("GAMEID")).doCommand(a);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
