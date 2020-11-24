package triangleCounter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang3.ArrayUtils;

public class Turtle {
	private Set<Integer> homeNeighbors;
	private Set<Integer> penultimateHopsVisited;
	private int homeId;
	private boolean active;
	
	public Turtle(int homeId) {
		active = true;
		this.homeId = homeId;
		homeNeighbors = new HashSet<Integer>();
		penultimateHopsVisited = new HashSet<Integer>();
		TurtleMonitor.regesterTurtle(homeId);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public int nextMigration(int[] neighbors, int currentPlaceId) {
		if(!active) {
			TurtleMonitor.announceWorkComplete(homeId);
			return homeId;
		} else if(homeId == currentPlaceId) {
			homeNeighbors = new HashSet<Integer>();
			for (int i : neighbors) {
				homeNeighbors.add(i);
			}
			
			Set<Integer> differnce = new HashSet<Integer>(homeNeighbors);
			differnce.removeAll(penultimateHopsVisited);
			active = false;
			if(!differnce.isEmpty()) {
				for(int i : differnce) {
					if(i < homeId) {
						active = true;
						penultimateHopsVisited.add(i);
						return i;
					}
				}
			}
		} else {
			for(int i : neighbors) {
				if(i < homeId && homeNeighbors.contains(i)) {
					TriangleSum.incrementSum();
				}
			}
		}
		
		return homeId;
	}
}
