package game;

import org.json.JSONObject;

public class TicTacToe implements GenGame {
	
	private int board[][] = new int[3][3];
	private int whoseTurn = 0;
	
	
	public TicTacToe(){
		System.out.println("TicTacToe Game Running");
	}
	

	
	
	@Override
	public JSONObject getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject runCommand(JSONObject input) {
		// TODO Auto-generated method stub
		return null;
	}

}
