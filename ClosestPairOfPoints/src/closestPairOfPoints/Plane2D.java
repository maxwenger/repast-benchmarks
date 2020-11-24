package closestPairOfPoints;

import java.io.FileNotFoundException;

import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.InfiniteBorders;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.StrictBorders;
import repast.simphony.space.grid.WrapAroundBorders;

public class Plane2D implements ContextBuilder<Object> {
	
	public Context build(Context<Object> context) {
		Parameters params = RunEnvironment.getInstance().getParameters();
		String pointsFilePath = params.getString("points_filepath");
		
		int[][] points = new int[2][1];
		
		try {
			points = PointLoader.loadPoints(pointsFilePath);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final int rangeX = getMaxX(points);
		final int rangeY = getMaxY(points);
		
		context.setId("ClosestPairOfPoints");

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);

		Grid<Object> grid = gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new InfiniteBorders<Object>(),
						new SimpleGridAdder<Object>(), false, rangeX, rangeY));
		
		for (int[] pointCoords : points) {
			Point point = new Point(grid);
			context.add(point);
			int x = pointCoords[0];
			int y = pointCoords[1];
			grid.moveTo(point, x, y);
		}
		
		return context;
	}
	
	private int getMaxX(int[][] points) {
		int max = Integer.MIN_VALUE;
		
		for(int[] point : points) {
			max = Math.max(max, point[0]);
		}
		
		return max;
	}
	
	private int getMaxY(int[][] points) {
		int max = Integer.MIN_VALUE;
		
		for(int[] point : points) {
			max = Math.max(max, point[1]);
		}
		
		return max;
	}

}
