package closestPairOfPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

public class Turtle {
	private Grid<Object> grid;
	private Set<PointLocation> foundPoints;
	private boolean workIsDone;
	private double firstPointFoundTickCount;
	private double bestDistance;

	public Turtle(Grid<Object> grid) {
		this.grid = grid;
		foundPoints = new HashSet<PointLocation>();
		workIsDone = false;
		bestDistance = Double.MAX_VALUE;
		firstPointFoundTickCount = Double.MAX_VALUE;
	}
	
	public void addFoundPoint(PointLocation p) {
		foundPoints.add(p);
		workIsDone = false;
	}
	
	public void addFoundPoints(Set<PointLocation> p) {
		foundPoints.addAll(p);
		workIsDone = false;
	}
	
	public double getBestDistance() {
		return bestDistance;
	}
	
	@ScheduledMethod(start=2, interval=2)
	public void stepM() {
		if(!workIsDone) {
			migrate(findMooreSites());
		}
	}
	
	@ScheduledMethod(start=3, interval=2)
	public void stepVN() {
		if(!workIsDone) {
			migrate(findVonNeumannSites());
		}
	}
	
	@ScheduledMethod(start=2, interval=1)
	public void checkClosestPointFound() {
		if(firstPointFoundTickCount * 1.15 <= RunEnvironment.getInstance().getCurrentSchedule().getTickCount()) {
			RunEnvironment.getInstance().endRun();
		}
	}
	
	private void migrate(List<GridPoint> sites) {
		Context<Object> context = ContextUtils.getContext(this);
		
		for(GridPoint point : sites) {
			Object object = grid.getObjectAt(point.getX(), point.getY());
			
			if(object == null) {
				Turtle turtle = new Turtle(grid);
				turtle.addFoundPoints(foundPoints);
				context.add(turtle);
							
				grid.moveTo(turtle, point.getX(), point.getY());
			} else if(object instanceof Turtle) {
				((Turtle) object).addFoundPoints(foundPoints);
			} else if(object instanceof Point) {
				this.addFoundPoint(new PointLocation(point.getX(), point.getY()));
				if(foundPoints.size() > 1) {
					startPointElection();
				}
			}
			
			workIsDone = true;
		}
	}
	
	private void startPointElection() {
		firstPointFoundTickCount = RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		
		PointLocation[] points = new PointLocation[foundPoints.size()];
		foundPoints.toArray(points);
		
		for(int i = 0; i < points.length; i++) {
			for(int j = i+1; j < points.length; j++) {
				double currDistance = points[i].distanceFrom(points[j]);
				if(currDistance < bestDistance) {
					bestDistance = currDistance;
				}
			}
		}
		
		DistanceElectionOfficial.castBestDistance(bestDistance);
	}
	
	/**
	 * Provides a list of adjacent (unoccupied) sites in the cell's Moore 
	 * neighborhood.  The list of sites is shuffled.
	 * 
	 * @return the list of adjacent sites.
	 */
	private List<GridPoint> findVonNeumannSites(){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		Context context = ContextUtils.getContext(this);
		GridPoint pt = grid.getLocation(this);
		
		if(pt != null) {
			emptySites.add(new GridPoint(pt.getX(),pt.getY()+1));
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()));
			emptySites.add(new GridPoint(pt.getX(),pt.getY()-1));
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()));
		}
		
		return emptySites;
	}
	
	/**
	 * Provides a list of adjacent (unoccupied) sites in the cell's Moore 
	 * neighborhood.  The list of sites is shuffled.
	 * 
	 * @return the list of adjacent sites.
	 */
	private List<GridPoint> findMooreSites(){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		Context context = ContextUtils.getContext(this);
		GridPoint pt = grid.getLocation(this);
		
		if(pt != null) {
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()+1));
			emptySites.add(new GridPoint(pt.getX(),pt.getY()+1));
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()+1));
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()));
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()-1));
			emptySites.add(new GridPoint(pt.getX(),pt.getY()-1));
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()-1));
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()));
		}
		
		return emptySites;
	}
		
}
