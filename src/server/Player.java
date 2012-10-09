package server;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {
	
	private final int auth;
	private int id;
	private String name;

	public Player(int pCount) {
		this.auth = (int) Math.floor(Math.random()*1000000); //Random 6 digit number used as an auth for clients
		this.setId(pCount);
	}
	
	
	public static Player getPlayerById(int i){
		//TODO
		return null;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the auth
	 */
	public int getAuth() {
		return auth;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	public JSONObject toJSON(){
		try {
			return new JSONObject("{\"id\":"+id+",\"auth\":"+auth+"}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}


}
