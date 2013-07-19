package game;

import org.json.JSONException;
import org.json.JSONObject;

public class PendingAttack implements Comparable<PendingAttack>{
	private int arrivalTick;
	private Planet targetPlanet;
	private int attackerId;
	private int attackPower;
	private int attackTroops;
	private int attackerPlanetId;
	
	public PendingAttack(int arrivalTick, Planet targetPlanet, int attackerId,
			int attackPower, int attackTroops,int attackerPlanetId) {
		this.arrivalTick = arrivalTick;
		this.targetPlanet = targetPlanet;
		this.attackerId = attackerId;
		this.attackPower = attackPower;
		this.attackTroops = attackTroops;
		this.attackerPlanetId = attackerPlanetId;
	}
	public int getArrivalTick() {
		return arrivalTick;
	}
	public Planet getTargetPlanet() {
		return targetPlanet;
	}
	public int getAttackerId() {
		return attackerId;
	}
	public int getAttackPower() {
		return attackPower;
	}
	public int getAttackTroops() {
		return attackTroops;
	}
	public int compareTo(PendingAttack pa) {
		return this.arrivalTick - pa.arrivalTick;
	}
	public void attack() {
		targetPlanet.attack(this);
	}
	public JSONObject toJSON()
	{
		JSONObject json = new JSONObject();
		try {
			json.put("ATTACKERID", attackerId);
			json.put("POWER", attackPower);
			json.put("TROOPS", attackTroops);
			json.put("TARGETID", targetPlanet.getId());
			json.put("TICK", arrivalTick);
			json.put("ATTACKERPLANETID", attackerPlanetId);
		} catch (JSONException e) {
			// this should never happen
			System.out.println("The World Ended");
		}
		
		return json;
	
	}
	
	public static PendingAttack fromJSON(JSONObject json, Planet[] board)
	{
		PendingAttack attack = null;
		try{
			int planetNum = json.getInt("TARGETID");
			Planet target = board[planetNum];
			attack = new PendingAttack(json.getInt("TICK"), target, json.getInt("ATTACKERID"), 
					json.getInt("POWER"), json.getInt("TROOPS"), json.getInt("ATTACKERPLANETID"));
		}
		catch (JSONException e)
		{
			// this should never happen
			System.out.println("The World Ended");
		}
		
		return attack;
	}
	
	
	
	

}
