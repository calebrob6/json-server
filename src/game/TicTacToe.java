package game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Debugger;
import server.Scoreboard;

public class TicTacToe implements GenGame {

	private int board[][] = new int[3][3];
	private int whoseTurn = 0;
	private boolean gameRunning = false;

	public TicTacToe() {
		Debugger.out("TicTacToe Game Running");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = -1;
			}
		}
		gameRunning = true;
	}

	@Override
	public JSONObject getStatus() {

		JSONArray rBoard = new JSONArray();
		for (int i = 0; i < 3; i++) {
			JSONArray collum = new JSONArray();
			for (int j = 0; j < 3; j++) {
				Debugger.out(board[i][j] + " ",0);
				collum.put(board[i][j]);
			}
			Debugger.out("");
			rBoard.put(collum);
		}
		JSONObject rObj = new JSONObject();
		try {
			rObj.put("BOARD", rBoard);
			rObj.put("TURN", whoseTurn);
			rObj.put("RUNNING", gameRunning);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rObj;
	}

	@Override
	public JSONObject doCommand(JSONObject input) throws JSONException {
		int id = -1;
		int x = -1;
		int y = -1;
		int error = -1;
		boolean won = false;
		JSONArray command = null;

		try {
			id = input.getInt("ID");
			command = input.getJSONArray("COMMAND");
		} catch (JSONException e) {
			Debugger.out("Malformed input JSON");
			error = 3;
		}

		String commandName = null;
		commandName = (String) command.getString(0);

		if (commandName.equalsIgnoreCase("move")) {
			
			x = command.getInt(1);
			y = command.getInt(2);
			
			if (whoseTurn == id) {
				if (checkMove(id, x, y)) {
					doMove(id, x, y);
					if (checkWin(id)) {
						Debugger.out("player " + id + " won!");
						Scoreboard.incrementScore(Integer.toString(input.getInt("GAMEID")));
						won = true;
						gameRunning = false;
					}
				}
			}
		}

		JSONObject rWhat = new JSONObject();
		try {
			rWhat.put("ERROR", error);
			rWhat.put("WON", won);
		} catch (JSONException e) {
			e.printStackTrace();
			Debugger.out("Error creating return object");
		}
		return rWhat;
	}

	private boolean checkWin(int id) {
		if (board[0][0] == id && board[1][0] == id && board[2][0] == id)
			return true;
		if (board[0][1] == id && board[1][1] == id && board[2][1] == id)
			return true;
		if (board[0][2] == id && board[1][2] == id && board[2][2] == id)
			return true;

		if (board[0][0] == id && board[0][1] == id && board[0][2] == id)
			return true;
		if (board[1][0] == id && board[1][1] == id && board[1][2] == id)
			return true;
		if (board[2][0] == id && board[2][1] == id && board[2][2] == id)
			return true;

		if (board[0][0] == id && board[1][1] == id && board[2][2] == id)
			return true;
		if (board[0][2] == id && board[1][1] == id && board[2][0] == id)
			return true;

		return false;
	}

	private void doMove(int id, int x, int y) {
		board[x][y] = id;
		if (whoseTurn == 1) {
			whoseTurn = 0;
		} else {
			if (whoseTurn == 0)
				whoseTurn = 1;
		}
	}

	private boolean checkMove(int id, int x, int y) {
		return (board[x][y] == -1);
	}

}
