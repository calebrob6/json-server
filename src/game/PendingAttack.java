package game;

import org.json.JSONException;
import org.json.JSONObject;

public class PendingAttack implements Comparable<PendingAttack>{
	private int arrivalTick;
	private int startTick;
	private Planet targetPlanet;
	private int attackerId;
	private int attackTroops;
	private Planet attackerPlanet;
	
	
	public PendingAttack(int arrivalTick, int startTick, Planet targetPlanet,
			int attackerId, int attackTroops, Planet attackerPlanet) {
		super();
		this.arrivalTick = arrivalTick;
		this.startTick = startTick;
		this.targetPlanet = targetPlanet;
		this.attackerId = attackerId;
		this.attackTroops = attackTroops;
		this.attackerPlanet = attackerPlanet;
	}
	
	public int getArrivalTick() {
		return arrivalTick;
	}

	public void setArrivalTick(int arrivalTick) {
		this.arrivalTick = arrivalTick;
	}

	public int getStartTick() {
		return startTick;
	}

	public void setStartTick(int startTick) {
		this.startTick = startTick;
	}

	public Planet getTargetPlanet() {
		return targetPlanet;
	}

	public void setTargetPlanet(Planet targetPlanet) {
		this.targetPlanet = targetPlanet;
	}

	public int getAttackerId() {
		return attackerId;
	}

	public void setAttackerId(int attackerId) {
		this.attackerId = attackerId;
	}

	public int getAttackTroops() {
		return attackTroops;
	}

	public void setAttackTroops(int attackTroops) {
		this.attackTroops = attackTroops;
	}

	public Planet getAttackerPlanet() {
		return attackerPlanet;
	}

	public void setAttackerPlanet(Planet attackerPlanet) {
		this.attackerPlanet = attackerPlanet;
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
			json.put("TROOPS", attackTroops);
			json.put("TARGETPLANETID", targetPlanet.getPlanetId());
			json.put("ARRIVETICK", arrivalTick);
			json.put("DEPARTTICK", startTick);
			json.put("ATTACKERPLANETID", attackerPlanet.getPlanetId());
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
			int targetPlanetNum = json.getInt("TARGETPLANETID");
			Planet target = board[targetPlanetNum];
			int basePlanetNum = json.getInt("ATTACKERPLANETID");
			Planet base = board[basePlanetNum];
			attack = new PendingAttack(json.getInt("ARRIVETICK"), json.getInt("DEPARTTICK"), target, 
					json.getInt("ATTACKERID"), json.getInt("TROOPS"), base);
		}
		catch (JSONException e)
		{
			// this should never happen
			System.out.println("The World Ended");
		}
		
		return attack;
	}
}
