package game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Debugger;
import server.Scoreboard;

public class Checkers implements GenGame {

	private int board[][] = {
			{ 0, 2, 0, 2, 0, 2, 0, 2 },
			{ 2, 0, 2, 0, 2, 0, 2, 0 }, 
			{ 0, 2, 0, 2, 0, 2, 0, 2 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 1, 0, 1, 0, 1, 0 }, 
			{ 0, 1, 0, 1, 0, 1, 0, 1 }, 
			{ 1, 0, 1, 0, 1, 0, 1, 0 }
			};
	private int whoseTurn = 0;
	private boolean gameRunning = false;
	private int whoWon = -1;

	public Checkers() {
		if(Debugger.DEBUG){
			System.out.println("Checkers Game Running");
		}
		whoseTurn = 1;
		gameRunning = true;
	}

	@Override
	public JSONObject getStatus() {

		JSONArray rBoard = new JSONArray();
		for (int i = 0; i < board.length; i++) {
			JSONArray collum = new JSONArray();
			for (int j = 0; j < board[i].length; j++) {
				collum.put(board[i][j]);
			}
			rBoard.put(collum);
		}
		JSONObject rObj = new JSONObject();
		try {
			rObj.put("BOARD", rBoard);
			rObj.put("TURN", whoseTurn);
			rObj.put("RUNNING", gameRunning);
			rObj.put("WHOWON", whoWon);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rObj;
	}

	@Override
	public JSONObject doCommand(JSONObject input) throws JSONException {
		
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		
		
		int id = -1;
		int error = -1;
		boolean won = false;
		JSONArray command = null;

		try {
			id = input.getInt("ID");
			command = input.getJSONArray("COMMAND");
		} catch (JSONException e) {
			System.err.println("Malformed JSON");
			error = 3;
		}

		String commandName = null;
		commandName = (String) command.getString(0);

		if (commandName.equalsIgnoreCase("move")) {
			
			x1 = command.getInt(1);
			y1 = command.getInt(2);
			x2 = command.getInt(3);
			y2 = command.getInt(4);
			
			if(doMove(x1,y1,x2,y2)){
				
			}else{
				//invalid move
			}
			
			
		}

		JSONObject rWhat = new JSONObject();
		try {
			rWhat.put("ERROR", error);
			rWhat.put("WON", won);
		} catch (JSONException e) {
			e.printStackTrace();
			System.err.println("Error creating return object");
		}
		return rWhat;
		
		
	}

	private boolean doMove(int x1, int y1, int x2, int y2) {
		
		
		if(board[x1][y1] == whoseTurn || board[x1][y1] == whoseTurn + 2){
			
			if(board[x1][y1] == whoseTurn){ //we have a normal piece
				if(((y2==y1+1) && (x2==x1+1 || x2==x1-1)) && inBounds(x2,y2)){ //we have a legal move
					
					if(board[x2][y2]==0){ //the place we want to move is empty
						
						board[x2][y2] = whoseTurn;
						board[x1][y1] = 0;
						return true;
						
					}
					
				}
			}
			
			
		}else{
			return false; //we don't have a piece at (x1,y1)
		}
		
		
		return false;
	}

	private boolean inBounds(int x, int y) {
		return (x>=0 && x<8) && (y>=0 && y<8);
	}

}
