package client;

import game.PendingAttack;
import game.Planet;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


public class AlienGameState {
	
	private ArrayList<Planet> myPlanets;
	private ArrayList<Planet> theirPlanets;
	private ArrayList<Planet> neutralPlanets;
	private ArrayList<PendingAttack> myAttacks;
	private ArrayList<PendingAttack> theirAttacks;
	private String whoWon;
	private int currentTick;
	
	public ArrayList<Planet> getMyPlanets() {
		return myPlanets;
	}

	public void setMyPlanets(ArrayList<Planet> myPlanets) {
		this.myPlanets = myPlanets;
	}

	public ArrayList<Planet> getTheirPlanets() {
		return theirPlanets;
	}

	public void setTheirPlanets(ArrayList<Planet> theirPlanets) {
		this.theirPlanets = theirPlanets;
	}

	public ArrayList<Planet> getNeutralPlanets() {
		return neutralPlanets;
	}

	public void setNeutralPlanets(ArrayList<Planet> nuetralPlanets) {
		this.neutralPlanets = nuetralPlanets;
	}

	public ArrayList<PendingAttack> getMyAttacks() {
		return myAttacks;
	}

	public void setMyAttacks(ArrayList<PendingAttack> myAttacks) {
		this.myAttacks = myAttacks;
	}

	public ArrayList<PendingAttack> getTheirAttacks() {
		return theirAttacks;
	}

	public void setTheirAttacks(ArrayList<PendingAttack> theirAttacks) {
		this.theirAttacks = theirAttacks;
	}

	public String getWhoWon() {
		return whoWon;
	}

	public void setWhoWon(String whoWon) {
		this.whoWon = whoWon;
	}

	public int getCurrentTick() {
		return currentTick;
	}

	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	private AlienGameState() {
		setMyAttacks(new ArrayList<PendingAttack>());
		setTheirAttacks(new ArrayList<PendingAttack>());
		setMyPlanets(new ArrayList<Planet>());
		setTheirPlanets(new ArrayList<Planet>());
		setNeutralPlanets(new ArrayList<Planet>());
		setWhoWon("");
		setCurrentTick(0);
	}

	public static AlienGameState parse(JSONObject resp, int playerId) {
		try {
			AlienGameState state = new AlienGameState();
			
			if(resp.has("STATUS"))
			{
				resp = resp.getJSONObject("STATUS");
			}
			
			JSONArray planets = resp.getJSONArray("MAP");
			Planet[] planetArray = new Planet[planets.length()];
			int myTroops, allTroops, myPlanets, allPlanets;
			myTroops = allTroops = myPlanets = allPlanets = 0;
			for(int i = 0; i < planets.length(); i++)
			{
				JSONObject jsonPlanet = planets.getJSONObject(i);
				Planet planet = Planet.fromJSON(jsonPlanet);
				planetArray[i] = planet;
				allPlanets++;
				allTroops += planet.getTroopCount();
				if (planet.getOwnerId() == playerId)
				{
					state.myPlanets.add(planet);
					myPlanets++;
					myTroops += planet.getTroopCount();
				}
				else if(planet.getOwnerId() == -1)
				{
					state.neutralPlanets.add(planet);
				}
				else //planet.getOwnerId() == the other player
				{
					state.theirPlanets.add(planet);
				}
			}
			System.out.print(resp.getInt("TICK") + "\t" + myPlanets + "/" + allPlanets + "\t" + myTroops + "/" + allTroops + "\t");
			JSONArray attacks = new JSONArray();
			if(resp.has("ATTACKS"))						//if there are no attacks, no element 
				attacks = resp.getJSONArray("ATTACKS");	//will be present. this is normal
			for(int i = 0; i < attacks.length(); i++)
			{
				JSONObject jsonAttack = attacks.getJSONObject(i);
				PendingAttack attack = PendingAttack.fromJSON(jsonAttack, planetArray);
				if (attack.getAttackerId() == playerId)
				{
					state.myAttacks.add(attack);
					
				}
				else //planet.getOwnerId() == the other player
				{
					state.theirAttacks.add(attack);
				}
			}
			System.out.println(state.myAttacks.size() + "/" + (state.myAttacks.size() + state.theirAttacks.size()));
			state.currentTick = resp.getInt("TICK");
			if(resp.getInt("WHOWON") == playerId)
				state.whoWon = "YOU";
			else if(resp.getInt("WHOWON") == -1)
				state.whoWon = "";
			else
				state.whoWon = "THEY";
			
			return state;
		} catch (Exception e) {
			return null;
		}
	}
	

}
