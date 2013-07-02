package game;

import java.util.HashMap;

public class LightSpacePlayer {

	private HashMap<String, Integer> resources = new HashMap<String,Integer>();
	
	//new player object
	public LightSpacePlayer(){
		resources.put("wood", 5);
		resources.put("meat",0);
		resources.put("fur",0);
	}
	
	/**
	 * 
	 * @param key What resource
	 * @param amount Amount of resource
	 * @return True if append succeeded, false if it didn't
	 */
	public boolean appendResource(String key, int amount){
		if(resources.containsKey(key)){
			resources.put(key, resources.get(key)+amount);
			return true;
		}else{
			return false;
		}
	}
	
}
