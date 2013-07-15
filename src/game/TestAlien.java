package game;

import org.json.JSONException;
import org.json.JSONObject;

public class TestAlien {
	public static void main(String[] args) throws JSONException {
		long startTime = System.currentTimeMillis();
		Planet[] planets = Planet.getMap();
		for (Planet a : planets) {
			JSONObject json = a.toJSON();
			Planet b = Planet.fromJSON(json);
			assert a.getAttack() == b.getAttack();
			assert a.getDefense() == b.getDefense();
			assert a.getSpeed() == b.getSpeed();
			assert a.getTroopCount() == b.getTroopCount();
			assert a.getTroopsPerTick() == b.getTroopsPerTick();
			assert a.getMaxCapacity() == b.getMaxCapacity();
			assert a.getOwnerId() == b.getOwnerId();
			assert a.getId() == b.getId();
			assert a.getX() == b.getX();
			assert a.getY() == b.getY();
		}

		PendingAttack pa = new PendingAttack(37, planets[3], 42, 100, 100);
		JSONObject json = pa.toJSON();
		PendingAttack pa2 = PendingAttack.fromJSON(json, planets);
		assert pa.getArrivalTick() == pa2.getArrivalTick();
		assert pa.getAttackerId() == pa2.getAttackerId();
		assert pa.getAttackPower() == pa2.getAttackPower();
		assert pa.getAttackTroops() == pa2.getAttackTroops();
		assert pa.getTargetPlanet() == pa2.getTargetPlanet()
				&& pa2.getTargetPlanet() == planets[3];
		
		for(Planet p : planets)
		{
			int before = p.getTroopCount();
			p.tick();
			int after = p.getTroopCount();
			if(p.getOwnerId() == -1)
				assert before == after;
			else
				assert before  + p.getTroopsPerTick() == after;
		}

		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		
		planets = Planet.getMap();
		for(Planet from : planets)
		{
			for(Planet to : planets)
			{
				if(to.getId() != from.getId())
				{
					PendingAttack attack = Planet.startAttack(from, to, 0);
					System.out.println("Attacking from " + from.getId() + " to " + to.getId() + " takes " + attack.getArrivalTick() + " ticks.");
				}
			}
		}
	}

}
