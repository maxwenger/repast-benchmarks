package triangleCounter;

import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {
	
	private int numberOfVertices;
	private int numberOfEdges;
	
	public GraphGenerator(int numberOfVertices, double percentConnected) {
		this.numberOfVertices = numberOfVertices;
		
		int maxEdges = (numberOfVertices * (numberOfVertices - 1)) / 2;
		this.numberOfEdges = (int) (maxEdges * percentConnected);
		
	}
	
	public Place[] GenerateGraph() {
		Place[] places = new Place[numberOfVertices];
		
		for(int i = 0; i < places.length; i++) {
			places[i] = new Place(i);
		}
		
		List<ArrayList<Integer>> possibleEdges = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < places.length; i++) {
			possibleEdges.add(new ArrayList<Integer>());
			for(int j = i + 1; j < places.length; j++) {
				possibleEdges.get(i).add(j);
			}
		}
		
		int edgesCreated = 0;
		while(edgesCreated < numberOfEdges) {
			int vertexId1 = (int) (Math.random() * numberOfVertices);
			if(!possibleEdges.get(vertexId1).isEmpty()) {
				
				int vertexIdIndex2 = (int) (Math.random() * possibleEdges.get(vertexId1).size());
				
				int id2 = possibleEdges.get(vertexId1).get(vertexIdIndex2);
				places[vertexId1].addNeighbor(places[id2]);
				places[id2].addNeighbor(places[vertexId1]);
				
				possibleEdges.get(vertexId1).remove(vertexIdIndex2);
				edgesCreated++;
			}
		}
		
		
		
		return places;
	}

}
