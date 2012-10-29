package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Logger {

	public static HashMap<String, Integer> scoreboard = new HashMap<String, Integer>();
	
	public Logger(){
		
		
		
	}
	
	
	
	public static void updateScores(String k, Integer dv){
		scoreboard.put(k, scoreboard.get(k)+dv);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void printScores(){
		
		ArrayList as = new ArrayList( scoreboard.entrySet() );  
        
        Collections.sort( as , new Comparator() {  
            public int compare( Object o1 , Object o2 )  
            {  
                Map.Entry e1 = (Map.Entry)o1 ;  
                Map.Entry e2 = (Map.Entry)o2 ;  
                Integer first = (Integer)e1.getValue();  
                Integer second = (Integer)e2.getValue();  
                return first.compareTo( second );  
            }  
        }); 
		
		for (Map.Entry<String, Integer> entry : scoreboard.entrySet()) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    System.out.println(key+" "+value);
		}
		
		
	}
	
	
	
}
