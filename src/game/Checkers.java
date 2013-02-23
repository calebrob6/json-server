package game;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Debugger;
import server.Scoreboard;

public class Checkers implements GenGame {

	private int board[][] = {
			{ 0, -1, 0, -1, 0, -1, 0, -1 }, 
			{ -1, 0, -1, 0, -1, 0, -1, 0 }, 
			{ 0, -1, 0, -1, 0, -1, 0, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1 }, 
			{ -1, -1, -1, -1, -1, -1, -1, -1 }, 
			{ -1, 1, -1, 1, -1, 1, -1, 1 },
			{ 1, -1, 1, -1, 1, -1, 1, -1 },
			{ -1, 1, -1, 1, -1, 1, -1, 1 }
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
	
			
			if (whoseTurn-1 == id) {
				
				x1 = command.getInt(1);
				y1 = command.getInt(2);

				ArrayList<Integer> coordList = new ArrayList<Integer>();
				
				for(int i=3;i<command.length()-3;i++){
					coordList.add(command.getInt(i)); //this should be atleast 2
				}
				
				if(validMove(x1,y1,coordList)){
					doMove(x1,y1,coordList);
					System.out.println("Move successful");
					
					
				}else{
					//invalid move
				}
				
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

	private boolean validMove(int x1, int y1, ArrayList<Integer> coordList) {
		
		if(board[x1][y1] == whoseTurn){ //we have a normal piece (non-king)
			if( ((y2==y1+((whoseTurn==1)?1:-1)) && (x2==x1+1 || x2==x1-1)) && inBounds(x2,y2)){
				if(board[x2][y2]==0){
					return true;
				}
			}	
		}
		if(board[x1][y1] == whoseTurn+2){ //we have a king piece
			if( ((y2==y1+1 || y2==y1-1) && (x2==x1+1 || x2==x1-1)) && inBounds(x2,y2)){ //we have a legal move
				if(board[x2][y2]==0){
					return true;
				}
			}
		}
		return false;
	}

	private boolean doMove(int x1, int y1, ArrayList<Integer> coordList) {

		
		return false
	}

	

	private boolean validJump(int x1, int y1, int x2, int y2) {
		
		if(board[x1][y1] == whoseTurn){ //we have a normal piece (non-king)
			if( ((y2==y1+((whoseTurn==1)?2:-2)) && (x2==x1+2 || x2==x1-2)) && inBounds(x2,y2)){
				if(board[Integer.valueOf((x2+x1)/2)][Integer.valueOf((y2+y1)/2)]==1){
					
					
				}
			}	
		}
		if(board[x1][y1] == whoseTurn+2){ //we have a king piece
			if( ((y2==y1+2 || y2==y1-2) && (x2==x1+2 || x2==x1-2)) && inBounds(x2,y2)){
				
				return true;
			}
		}
		
		return false;
	}
	
	private boolean validMove(int x1, int y1, int x2, int y2) {
		
		
		
		return false;
	}

	private boolean inBounds(int x, int y) {
		return (x>=0 && x<8) && (y>=0 && y<8);
	}

}
