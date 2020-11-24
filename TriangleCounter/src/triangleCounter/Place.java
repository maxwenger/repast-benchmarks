package triangleCounter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import repast.simphony.engine.schedule.ScheduledMethod;

public class Place {
	private int id;
	private HashMap<Integer, Place> neighbors;
	private Queue<Turtle> turtles;
	private boolean isTurtleActive;
	
	public Place(int id) {
		this.id = id;
		neighbors = new HashMap<Integer, Place>();
		turtles = new LinkedList<Turtle>();
		turtles.add(new Turtle(id));
	}
	
	public int getId() {
		return id;
	}
	
	public void addNeighbor(Place n) {
		if(n != null && n.getId() != id) {
			neighbors.put(n.getId(), n);
		}
	}
	
	public Place[] getNeignbors() {
		Place[] places = new Place[neighbors.size()];
		System.arraycopy(neighbors.values().toArray(), 0, places, 0, neighbors.size());
		
		return places;
	}
	
	public int[] getNeignboringIDs() {
		int[] places = new int[neighbors.size()];
		
		int i = 0;
		for(int key : neighbors.keySet()) {
			places[i] = key;
			i++;
		}
		
		return places;
	}
	
	public void addTurtle(Turtle turtle) {
		turtles.add(turtle);
	}
	
	@ScheduledMethod(start=2, interval=1)
	public void migrateTurtles() {
		int numberOfTurtles = turtles.size();
		
		for(int i = 0; i < numberOfTurtles; i++) {
			Turtle turtle = turtles.remove();
			int nextHop = turtle.nextMigration(getNeignboringIDs(), id);
			
			if(nextHop == id) {
				turtles.add(turtle);
			} else {
				if(!neighbors.containsKey(nextHop)) {
					System.out.println();
				}
				neighbors.get(nextHop).addTurtle(turtle);
			}
		}
	}
}
