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

public class TicTacToeAI {

	public static final String host = "http://localhost:8000";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws JSONException {

		
		int id,gId,auth;
		Random rand = new Random();
		boolean gameRunning = true;
		int map[][] = new int[3][3];
		
		
		JSONObject nullObject = new JSONObject("{}");
		
		JSONObject playerInfo = doRequest(nullObject,"/connect");
		id = playerInfo.getInt("ID");
		gId = playerInfo.getInt("GAMEID");
		auth = playerInfo.getInt("AUTH");
		
		JSONObject getStatusObj = new JSONObject();
		getStatusObj.put("ID", id);
		getStatusObj.put("GAMEID",gId);
		getStatusObj.put("AUTH",auth);
		
		
		while(gameRunning){
			
			JSONObject gStatus = doRequest(getStatusObj, "/game/status");
			
			if(gStatus.getInt("TURN")==id){
				
				int moves = 0;
				JSONArray board = gStatus.getJSONArray("BOARD");
				
				for(int i=0;i<board.length();i++){
					for(int j=0;j<board.getJSONArray(i).length();j++){
						map[i][j] = board.getJSONArray(i).getInt(j);
						if(map[i][j]==-1) moves++;
					}
				}
				
				if(moves==0){
					System.out.println("No avaliable moves");
					gameRunning = false;
					break;
				}else{
					boolean haventFoundMove = true;
					int q = -1,p=-1;
					while(haventFoundMove){
						
						q = rand.nextInt(3);
						p = rand.nextInt(3);
						if(map[q][p]==-1){
							haventFoundMove = false;
						}
					}
					JSONObject moveCmd = new JSONObject();
					JSONArray mCmd = new JSONArray();
					moveCmd.put("ID", id);
					moveCmd.put("GAMEID",gId);
					moveCmd.put("AUTH",auth);
					mCmd.put(0, "MOVE");
					mCmd.put(1, q);
					mCmd.put(2, p);
					moveCmd.put("COMMAND", mCmd);
					JSONObject moveRet = doRequest(moveCmd, "/game/move");
					System.out.println(moveRet);
					if(moveRet.get("WON")=="true"){
						System.out.println("AI WON!");
						gameRunning = false;
						break;
					}
					/*
					if(moveRet.get("ERROR")!=0){
						System.out.println("ERROR CODE: "+moveRet.getInt("ERROR"));
					}
					*/
				
				}
				
				
				
			}else{
				
				//if it isn't our turn give the other guy 1 second to think about it before checking again
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) { 
					e.printStackTrace(); //insomnia yo
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
				response+=line;
				//System.out.println(line);
			}
			wr.close();
			rd.close();
			resp = new JSONObject(response);

		} catch (UnsupportedEncodingException e) {  //PRO error handling!

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		} catch (JSONException e) {
			
		}
		
		return resp;

	}

}
