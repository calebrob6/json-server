package server;

public class Player {
	
	private final String auth;
	private int id;
	private String name;

	public Player(int pCount) {
		this.auth = (Double.toString(Math.floor(Math.random()*1000000)));
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
	public String getAuth() {
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


}
