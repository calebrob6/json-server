package game;

import org.json.JSONObject;

public interface GenGame {

	public JSONObject getStatus();
	public JSONObject runCommand(JSONObject input);

	
}
