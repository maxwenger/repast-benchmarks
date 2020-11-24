package triangleCounter;

import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.InfiniteBorders;
import repast.simphony.space.grid.SimpleGridAdder;

public class ContextPlane implements ContextBuilder<Object>{
	private final int rangeX = 100;
	private final int rangeY = 100;

	@Override
	public Context build(Context<Object> context) {
		context.setId("TriangleCounter");

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);

		Grid<Object> grid = gridFactory.createGrid("grid", context,
				new GridBuilderParameters<Object>(new InfiniteBorders<Object>(),
						new SimpleGridAdder<Object>(), false, rangeX, rangeY));
		
		NetworkBuilder<Object> net = new NetworkBuilder<Object>("edges", context, true);
		net.buildNetwork();
		
		GraphGenerator gg = new GraphGenerator(100, .05);
		placeGraphOnGrid(gg.GenerateGraph(), context, grid);
		
		
		return context;
	}
	
	private void placeGraphOnGrid(Place[] places, Context<Object> context, Grid<Object> grid) {
		for(Place p : places) {
			context.add(p);
			int x = RandomHelper.nextIntFromTo(0, rangeX - 1);
			int y = RandomHelper.nextIntFromTo(0, rangeY - 1);
			while(!grid.moveTo(p, x, y)) {
				x = RandomHelper.nextIntFromTo(0, rangeX - 1);
				y = RandomHelper.nextIntFromTo(0, rangeY - 1);
			}
		}
		
		Network<Object> net  = (Network<Object>)context.getProjection("edges");
		
		for(Place p : places) {
			for(Place p2 : p.getNeignbors()) {
				net.addEdge(p, p2);
			}
		}
		
	}
}
