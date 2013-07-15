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

import game.Planet;

import org.json.JSONException;
import org.json.JSONObject;

public class AlienInvasionClient {
	private static final String connect = "http://localhost:8000/connect";
	private static final String attack = "http://localhost:8000/game/move";
	private static final String status = "http://localhost:8000/game/status";
	private int playerId, playerAuth, gameId;
	
	
	
	public static AlienInvasionClient connect()
	{
		AlienInvasionClient temp = new AlienInvasionClient();
		JSONObject req = new JSONObject();
		String response = "";
		JSONObject resp = null;
		String data;
		try {
			data = URLEncoder.encode(req.toString(), "UTF-8");
			// Send data
			URL url = new URL(connect);
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
			temp.gameId = resp.getInt("GAMEID");
			temp.playerAuth = resp.getInt("AUTH");
			temp.playerId = resp.getInt("ID");
			

		} catch (Exception e) { // PRO error handling!
			//oops
		} 
		
		return temp;
	}
	
	public AlienGameState attack(Planet target, Planet base)
	{
		JSONObject req = new JSONObject();
		try {
			req.put("FROM", base.getId());
			req.put("TO", target.getId());
			req.put("ID", playerId);
			req.put("AUTH", playerAuth);
			req.put("GAMEID", gameId);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String response = "";
		JSONObject resp = null;
		String data;
		try {
			data = URLEncoder.encode(req.toString(), "UTF-8");
			// Send data
			URL url = new URL(attack);
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

		} catch (Exception e) { // PRO error handling!

		} 
		

		return AlienGameState.parse(resp, playerId);
	}
	
	public AlienGameState getStatus()
	{
		JSONObject req = new JSONObject();
		try {
			req.put("ID", playerId);
			req.put("AUTH", playerAuth);
			req.put("GAMEID", gameId);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		String response = "";
		JSONObject resp = null;
		String data;
		try {
			data = URLEncoder.encode(req.toString(), "UTF-8");
			// Send data
			URL url = new URL(status);
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

		} catch (Exception e) { // PRO error handling!

		} 
		
		return AlienGameState.parse(resp, playerId);
	}
	

}
