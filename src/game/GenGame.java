package game;

import org.json.JSONException;
import org.json.JSONObject;

public interface GenGame {

	public JSONObject getStatus();
	public JSONObject doCommand(JSONObject input) throws JSONException;

	
}
