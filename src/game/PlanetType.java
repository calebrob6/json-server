package game;

public class PlanetType {
	
	private int initialOwner;
	private int initialForce;
	private int maxCapacity;
	private int troopsPerTick;
	private int attack;
	private int defense;
	private int speed;
	
	public PlanetType(int initialOwner, int initialForce, int maxCapacity, int troopsPerTick, int attack, int defense, int speed)
	{
		this.initialOwner = initialOwner;
		this.initialForce = initialForce;
		this.maxCapacity = maxCapacity;
		this.troopsPerTick = troopsPerTick;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
	}

	public int getInitialOwner() {
		return initialOwner;
	}

	public int getInitialForce() {
		return initialForce;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public int getTroopsPerTick() {
		return troopsPerTick;
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

}
