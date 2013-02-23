package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckersAI {

	public static final String host = "http://localhost:8000";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws JSONException {

		int id, gId, auth;
		Random rand = new Random();
		boolean gameRunning = true;
		int map[][] = new int[8][8];

		JSONObject nullObject = new JSONObject("{}");

		JSONObject playerInfo = doRequest(nullObject, "/connect");
		if (playerInfo == null) {
			System.out.println("/connect request failed");
			return;
		}
		id = playerInfo.getInt("ID");
		gId = playerInfo.getInt("GAMEID");
		auth = playerInfo.getInt("AUTH");
		
		

		JSONObject getStatusObj = new JSONObject();
		getStatusObj.put("ID", id);
		getStatusObj.put("GAMEID", gId);
		getStatusObj.put("AUTH", auth);

		while (gameRunning) {

			JSONObject gStatus = doRequest(getStatusObj, "/game/status");
			gameRunning = gStatus.getBoolean("RUNNING");
			JSONArray board = gStatus.getJSONArray("BOARD");
			for (int i = 0; i < board.length(); i++) {
				for (int j = 0; j < board.getJSONArray(i).length(); j++) {
					map[i][j] = board.getJSONArray(i).getInt(j);
				}
			}
			
			if(gStatus.getInt("WHOWON")!=-1 && gStatus.getInt("WHOWON")!=id){
				System.out.println("We lost :(");
				break;
			}

			if (gStatus.getInt("TURN") == id && gameRunning) {

				ArrayList<int[]> arr = getMoves(map, id);
				System.out.println(arr.size());
				int rand1 = rand.nextInt(arr.size()/2);
				int[] start = arr.get(rand1*2);
				int[] end = arr.get((rand1*2)+1);
				JSONObject moveCmd = new JSONObject();
				JSONArray mCmd = new JSONArray();
				moveCmd.put("ID", id);
				moveCmd.put("GAMEID", gId);
				moveCmd.put("AUTH", auth);
				mCmd.put(0, "MOVE");
				mCmd.put(1, start[0]);
				mCmd.put(2, start[1]);
				mCmd.put(3, end[0]);
				mCmd.put(4, end[1]);
				moveCmd.put("COMMAND", mCmd);
				JSONObject moveRet = doRequest(moveCmd, "/game/move");
				System.out.println(moveRet.getBoolean("WON"));
				if (moveRet.getBoolean("WON") == true) {
					System.out.println("I WON!");
					gameRunning = false;
					break;
				}

			} else {

				// if it isn't our turn give the other guy 1 second to think
				// about it before checking again
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static JSONObject doRequest(JSONObject req, String urlPath) {
		String response = "";
		JSONObject resp = null;
		String data;
		try {
			data = URLEncoder.encode(req.toString(), "UTF-8");
			// Send data
			URL url = new URL(host + urlPath);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response += line;
				// System.out.println(line);
			}
			wr.close();
			rd.close();
			resp = new JSONObject(response);

		} catch (UnsupportedEncodingException e) { // PRO error handling!

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		} catch (JSONException e) {

		}

		return resp;
	}

	public static ArrayList<int[]> getMoves(int[][] board, int id)
	{
		final int MY_PIECE = id;
		int opPiece;
		if(id == 0)
		{
			opPiece = 1;
		}
		else
		{
			opPiece = 0;
		}
		final int OP_PIECE = opPiece;
		final int MY_KING = MY_PIECE + 2;
		final int OP_KING = OP_PIECE + 2;
		final int OPEN = -1;
		ArrayList<int[]> result = new ArrayList<int[]>();
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++)
			{
				System.out.println(board[x][y] + " "+ MY_PIECE);
				if(board[x][y] == MY_PIECE || board[x][y] == MY_KING)
				{
					result.addAll(getMovesForPiece(board, x, y));
					System.out.println("cooleo");
				}
			}
		return result;
	}

	public static ArrayList<int[]> getMovesForPiece(int[][] board, int x, int y) 
	{
		ArrayList<int[]> result = new ArrayList<int[]>();
		int id = board[x][y] % 2;
		int dir = -((id*2)-1);
		final int MY_PIECE = id;
		int opPiece;
		if(id == 0)
		{
			opPiece = 1;
		}
		else
		{
			opPiece = 0;
		}
		final int OP_PIECE = opPiece;
		final int MY_KING = MY_PIECE + 2;
		final int OP_KING = OP_PIECE + 2;
		final int OPEN = -1;
		try{
		System.out.println(board[x][y] + " " + x + " " + y + " " + board[x+dir][y-1] + " " + board[x+dir][y+1]);}
		catch(Exception e)
		{}
		if(((x+dir) > -1) && ((x+dir) < 8) && (y-1 > -1) && (board[x+dir][y-1] == -1))
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x+dir, y-1});
		}
			
		if((x+dir) > -1 && (x+dir) < 8 && y+1 < 8 && board[x+dir][y+1] == -1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x+dir, y+1});
		}
		if((x-dir) > -1 && (x-dir) < 8 && y-1 > -1 && board[x-dir][y-1] == -1 && board[x][y]/2==1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x-dir, y-1});
		}
		if((x-dir) > -1 && (x-dir) < 8 && y+1 < 8 && board[x-dir][y+1] == -1 && board[x][y]/2==1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x-dir, y+1});
		}
		if((x+dir) > 0 && (x+dir) < 7 && y-1 > 0 && (board[x+dir][y-1] == OP_PIECE || board[x+dir][y-1] == OP_KING) && board[x+dir+dir][y-2] == -1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x+dir+dir, y-2});
		}
		if((x+dir) > 0 && (x+dir) < 7 && y+1 < 7 && (board[x+dir][y+1] == OP_PIECE || board[x+dir][y+1] == OP_KING) && board[x+dir+dir][y+2] == -1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x+dir+dir, y+2});
		}
		if((x-dir) > 0 && (x-dir) < 7 && y-1 > 0 && (board[x-dir][y-1] == OP_PIECE || board[x-dir][y-1] == OP_KING) && board[x][y]/2==1 && board[x-dir-dir][y-2] == -1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x-dir-dir, y-2});
		}
		if((x-dir) > 0 && (x-dir) < 7 && y+1 < 7 && (board[x-dir][y+1] == OP_PIECE || board[x-dir][y+1] == OP_KING) && board[x][y]/2==1 && board[x-dir-dir][y+2] == -1)
		{
			result.add(new int[]{x, y});
			result.add(new int[]{x-dir-dir, y+2});
		}
		
		
		
		return result;
		
	}

}


