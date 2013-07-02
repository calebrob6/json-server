package game;

import game.map.Map;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LightSpace implements GenGame{
	
	private ArrayList<LightSpacePlayer> playerList = new ArrayList<LightSpacePlayer>();
	
	public LightSpace(){
		
		Map map = new Map();
		map.initMap();
		
		
	}

	@Override
	public JSONObject getStatus() {

		return null;
	}

	@Override
	public JSONObject doCommand(JSONObject input) throws JSONException {

		int id = -1;
		int error = -1;
		JSONArray command = null;

		try {
			id = input.getInt("ID");
			command = input.getJSONArray("COMMAND");
		} catch (JSONException e) {
			System.err.println("Malformed JSON");
			error = 3;
		}

		String commandName = null;
		commandName = (String) command.getString(0);

		if (commandName.equalsIgnoreCase("move")) {
			

		}

		
		JSONObject rWhat = new JSONObject();
		try {
			rWhat.put("ERROR", error);
		} catch (JSONException e) {
			e.printStackTrace();
			System.err.println("Error creating return object");
		}
		
		return rWhat;
	}

	@Override
	public boolean doInit(int gId) {
		// TODO Auto-generated method stub
		return false;
	}

}
