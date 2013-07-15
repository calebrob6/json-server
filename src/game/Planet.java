package game;

import org.json.JSONException;
import org.json.JSONObject;

public class Planet {
	private int maxCapacity;
	private int ownerId;
	private int troopCount;
	private int troopsPerTick;
	private double x;
	private double y;
	private int attack;
	private int defense;
	private int speed;
	private int id;
	public static PlanetType p0base = new PlanetType(0, 20, 200, 3, 100, 100, 100);
	public static PlanetType p1base = new PlanetType(1, 20, 200, 3, 100, 100, 100);
	public static PlanetType city = new PlanetType(-1, 50, 150, 2, 90, 90, 100);
	public static PlanetType village = new PlanetType(-1, 20, 100, 2, 75, 75, 75);
	public static PlanetType fortress = new PlanetType(-1, 50, 50, 0, 200, 200, 25);

	public Planet(PlanetType type, double x, double y, int id) {
		this.maxCapacity = type.getMaxCapacity();
		this.ownerId = type.getInitialOwner();
		this.troopCount = type.getInitialForce();
		this.troopsPerTick = type.getTroopsPerTick();
		this.x = x;
		this.y = y;
		this.attack = type.getAttack();
		this.defense = type.getDefense();
		this.speed = type.getSpeed();
		this.id = id;
	}

	public static Planet[] getMap() {
		return new Planet[] { new Planet(p0base, 0, 250, 0),
				new Planet(p1base, 1000, 250, 1), new Planet(city, 500, 0, 2),
				new Planet(city, 500, 500, 3), new Planet(fortress, 500, 250, 4),
				new Planet(village, 250, 125, 5), new Planet(village, 250, 250, 6),
				new Planet(village, 250, 375, 7), new Planet(village, 500, 125, 8),
				new Planet(village, 500, 375, 9), new Planet(village, 750, 125, 10),
				new Planet(village, 750, 250, 11), new Planet(village, 750, 375, 12), };
	}

	public void attack(PendingAttack attack) {
		if(ownerId == attack.getAttackerId()) //if attack is a reinforcement
		{
			troopCount += attack.getAttackTroops();
			if (troopCount > maxCapacity)
				troopCount = maxCapacity; // enforce capacity limits
			return;
		}
		int attackForce = attack.getAttackTroops() * attack.getAttackPower();
		int defenseForce = defense * troopCount;
		if (attackForce > defenseForce) // attacker wins
		{
			int survivors = (attackForce - defenseForce)
					/ attack.getAttackPower();
			if (survivors > 0) // the attacker must have at least one survivor
								// to occupy
			{
				troopCount = survivors;
				ownerId = attack.getAttackerId();
			}
		} else // defender wins
		{

			troopCount = (defenseForce - attackForce) / attack.getAttackPower();
		}
		if (troopCount > 1) {
			troopCount = 1; // the defense always get at least one troop if
							// there is no take over
		}
	}

	public void tick() {
		if (ownerId != -1) // neutral planets do not expand
			troopCount += troopsPerTick;
		if (troopCount > maxCapacity)
			troopCount = maxCapacity; // enforce capacity limits
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public int getTroopCount() {
		return troopCount;
	}

	public int getTroopsPerTick() {
		return troopsPerTick;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpeed() {
		return speed;
	}

	public int getId() {
		return id;
	}

	public static PendingAttack startAttack(Planet from, Planet to, int currentTick) {
		double dist = Math.sqrt(Math.pow(from.x - to.x, 2) + Math.pow(from.y - to.y, 2));
		int timeTaken = (int) Math.ceil(dist / from.speed);
		int arrivalTick = timeTaken + currentTick;
		int attackTroops = from.troopCount / 2;
		from.troopCount -= attackTroops;
		return new PendingAttack(arrivalTick, to, from.ownerId, from.attack, attackTroops);
	}
	public JSONObject toJSON()
	{
		JSONObject json = new JSONObject();;
		try {
			json.put("ID", ownerId);
			json.put("ATTACK", attack);
			json.put("DEFENSE", defense);
			json.put("SPEED", speed);
			json.put("X", x);
			json.put("Y", y);
			json.put("TROOPS", troopCount);
			json.put("GROWTH", troopsPerTick);
			json.put("CAPACITY", maxCapacity);
			json.put("PLANETID", id);
		} catch (JSONException e) {
			// this should never happen
			System.out.println("The World Ended");
		}
		
		return json;
	}
	
	public static Planet fromJSON(JSONObject json)
	{
		Planet p = null;
		PlanetType pt = null;
		
		try {
			pt = new PlanetType(json.getInt("ID"), json.getInt("TROOPS"), 
					json.getInt("CAPACITY"), json.getInt("GROWTH"), 
					json.getInt("ATTACK"), json.getInt("DEFENSE"), 
					json.getInt("SPEED"));
			p = new Planet(pt, json.getDouble("X"), json.getDouble("Y"), json.getInt("PLANETID"));
		} catch (JSONException e) {
			// this should never happen
			System.out.println("The World Ended");
		}
		
		return p;
		
	}
	

}
