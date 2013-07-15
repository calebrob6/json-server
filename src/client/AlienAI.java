package client;

import game.Planet;

import java.util.ArrayList;
import java.util.Random;

public class AlienAI {

	
	public static void main(String[] args) {
		AlienInvasionClient client = AlienInvasionClient.connect("localhost",8000);
		AlienGameState state = client.getStatus();
		
		while(state.getWhoWon().equals(""))
		{
			Random rand = new Random();
			ArrayList<Planet> ripePlanets = state.getNeutralPlanets();
			ripePlanets.addAll(state.getTheirPlanets());
			if(ripePlanets.size() > 0 && state.getMyPlanets().size() > 0)
			{
				//long start = System.currentTimeMillis();
				client.attack(ripePlanets.get(rand.nextInt(ripePlanets.size())), state.getMyPlanets().get(rand.nextInt(state.getMyPlanets().size())));
				//long end = System.currentTimeMillis();
				//System.out.println(end - start);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				//ignore
			}
			state = client.getStatus();
			
		}
		
		System.out.println(state.getWhoWon() + " WON!");

	}

}
