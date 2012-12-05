package server;

public class Debugger {
	
	
	static public final boolean DEBUG = false;
	
	public static void out(String m){
		if(DEBUG){
			System.out.println(m);
		}
	}
	
	public static void out(String m ,int flag){
		if(DEBUG){
			if(flag==1) System.out.println(m);
			if(flag==0) System.out.print(m);
		}
	}


}
