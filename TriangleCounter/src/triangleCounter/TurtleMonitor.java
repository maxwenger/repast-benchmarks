package triangleCounter;

import java.util.Hashtable;

import repast.simphony.engine.environment.RunEnvironment;

public class TurtleMonitor {
	private static Hashtable<Integer, Boolean> regesterdTurtles = new Hashtable<Integer, Boolean>();

	public static void announceWorkComplete(int turtleHomeID) {
		if(regesterdTurtles.containsKey(turtleHomeID)) {
			regesterdTurtles.replace(turtleHomeID, false);
		}
		
		for(boolean b : regesterdTurtles.values()) {
			if(b) {
				return;
			}
		}

		haultSimulation();
	}
	
	public static void regesterTurtle(int turtleHomeID) {
		regesterdTurtles.put(turtleHomeID, true);
	}
	
	private static void haultSimulation() {
		if(TriangleSum.getSum() != 0) {
			System.out.println(TriangleSum.getSum() + " unique triangles found.");
		}
		TriangleSum.resetSum();
		RunEnvironment.getInstance().endRun();
	}
}
