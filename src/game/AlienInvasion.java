package game;

import java.util.PriorityQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class AlienInvasion implements GenGame {

	public final int tickTime = 500;
	public long startTime;
	public int tick;
	public Planet[] board;
	public PriorityQueue<PendingAttack> attackQueue;
	private int playerCount;
	private final int maxPlayers = 2;
	private boolean isRunning;
	private int winner;

	public AlienInvasion() {
		startTime = Long.MAX_VALUE;
		tick = 0;
		board = Planet.getMap();
		attackQueue = new PriorityQueue<PendingAttack>();
		playerCount = 0;
		isRunning = false;
		winner = -1;
	}

	public boolean doConnect() {
		if (playerCount >= maxPlayers)
			return false;
		else {
			playerCount++;
			if(playerCount == maxPlayers)
			{
				isRunning = true;
				startTime = System.currentTimeMillis();
				tick = 0;
			}
			return true;
		}
	}
	
	public void checkWin()
	{
		for(int i = 0; i < maxPlayers; i++)
		{
			boolean win = true;
			for(Planet p : board)
			{
				if(p.getOwnerId() != i || p.getOwnerId() != -1) win = false;
			}
			for(PendingAttack pa : (PendingAttack[])attackQueue.toArray())
			{
				if(pa.getAttackerId() != i) win = false;
			}
			if(win)
			{
				winner = i;
			}
		}
	}

	@Override
	public JSONObject getStatus() {
		update();
		JSONObject response = new JSONObject();
		try {
			for (Planet planet : board)
				response.append("MAP", planet.toJSON());
			for (PendingAttack attack : attackQueue
					.toArray(new PendingAttack[] {}))
				response.append("ATTACKS", attack.toJSON());
			response.put("TICK", tick);
			response.put("RUNNING", isRunning);
			response.put("WHOWON", winner);
		} catch (JSONException e) {
			// something really bad happened
		}

		return response;
	}

	@Override
	public JSONObject doCommand(JSONObject input) throws JSONException {
		int errorCode = 0; // no error
		JSONObject response = new JSONObject();
		response.put("COMMAND", input);
		update();
		int fromPlanetNumber, toPlanetNumber, player;
		try {
			fromPlanetNumber = input.getInt("FROM");
			toPlanetNumber = input.getInt("TO");
			player = input.getInt("ID");
		} catch (Exception e) {
			errorCode = 3; // Malformed JSON
			response.put("ERROR", errorCode);
			response.put("STATUS", getStatus());
			return response;
		}
		if (isValid(fromPlanetNumber, player)) {
			Planet.startAttack(board[fromPlanetNumber], board[toPlanetNumber],
					tick);
		} else {
			errorCode = 2; // Invalid Move
			response.put("ERROR", errorCode);
			response.put("STATUS", getStatus());
			return response;
		}

		errorCode = 1; // Valid Move
		response.put("ERROR", errorCode);
		response.put("STATUS", getStatus());
		return response;
	}

	private boolean isValid(int fromPlanetNumber, int player) {
		return board[fromPlanetNumber].getOwnerId() == player
				&& board[fromPlanetNumber].getTroopCount() > 1 && isRunning;
	}

	private void update() {
		int currentTick = getTick();
		for (int i = tick + 1; i <= currentTick; i++) {
			for (Planet planet : board) {
				planet.tick();
			}
			while (attackQueue.peek() != null
					&& attackQueue.peek().getArrivalTick() < i) {
				attackQueue.poll().attack();
			}
		}
		tick = currentTick;
		checkWin();
	}

	private int getTick() {
		int elapsedTime = (int) (System.currentTimeMillis() - startTime);
		return elapsedTime / tickTime;
	}

}
