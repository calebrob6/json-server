package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Logger {

	public static final String logName = "log.txt";
	public FileWriter fStream;
	public BufferedWriter fOut;
	public static HashMap<String, Integer> scoreboard = new HashMap<String, Integer>();

	public Logger() {

		try {
			fStream = new FileWriter(logName); //overwrites log file every time
			fOut = new BufferedWriter(fStream);
		} catch (Exception e){
			System.out.println("Error creating log file");
		}

	}

	/*
	 * @param k The name of the person to update
	 * 
	 * @param dv The change in score of the person
	 */
	public static void updateScores(String k, Integer dv) {
		Integer a = scoreboard.get(k);
		if (a == null) {
			scoreboard.put(k, dv);
		} else {
			scoreboard.put(k, scoreboard.get(k) + dv);
		}

	}
	
	/*
	 * Creates a copy of the scoreboard that is sorted then prints it out
	 */
	public static void printScores() {

		Map<String, Integer> sorted = sortByValue(scoreboard);

		Iterator<?> it = sorted.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			it.remove();
		}

	}
	

	/*
	 * Yuck, java
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static Map<String, Integer> sortByValue(Map<String, Integer> map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return -((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it
					.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
}
