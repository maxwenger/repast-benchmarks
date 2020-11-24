package closestPairOfPoints;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.query.space.grid.MooreQuery;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

public class Point {

	private Grid<Object> grid;
	private int state;

	public Point(Grid<Object> grid) {
		this.grid = grid;
	}
	
	@ScheduledMethod(start = 1)
	public void step() {
		Context<Object> context = ContextUtils.getContext(this);

		List<GridPoint> emptySites = findEmptySites();

		for(GridPoint point : emptySites) {
			Turtle turtle = new Turtle(grid);
			context.add(turtle);
						
			grid.moveTo(turtle, point.getX(), point.getY());
		}
	}
	
	
	/**
	 * Provides a list of adjacent (unoccupied) sites in the cell's Moore 
	 * neighborhood.  The list of sites is shuffled.
	 * 
	 * @return the list of adjacent sites.
	 */
	private List<GridPoint> findEmptySites(){
		List<GridPoint> emptySites = new ArrayList<GridPoint>();
		Context context = ContextUtils.getContext(this);
		GridPoint pt = grid.getLocation(this);
		
		// Find Empty Moore neighbors
		// TODO automate via Repast API
		if (!grid.getObjectsAt(pt.getX()-1,pt.getY()+1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()+1));
		if (!grid.getObjectsAt(pt.getX(),pt.getY()+1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX(),pt.getY()+1));
		if (!grid.getObjectsAt(pt.getX()+1,pt.getY()+1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()+1));
		if (!grid.getObjectsAt(pt.getX()+1,pt.getY()).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()));
		if (!grid.getObjectsAt(pt.getX()+1,pt.getY()-1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()+1,pt.getY()-1));
		if (!grid.getObjectsAt(pt.getX(),pt.getY()-1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX(),pt.getY()-1));
		if (!grid.getObjectsAt(pt.getX()-1,pt.getY()-1).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()-1));
		if (!grid.getObjectsAt(pt.getX()-1,pt.getY()).iterator().hasNext())
			emptySites.add(new GridPoint(pt.getX()-1,pt.getY()));
		
				
		return emptySites;
	}

}
