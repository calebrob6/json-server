package server;

import java.util.concurrent.Executor;

public class GameExecutor implements Executor {
	
	
	static public int count = 0;

	public GameExecutor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Runnable arg0) {
		System.out.println(arg0.getClass().getName().toString());
		arg0.run();

	}

}
