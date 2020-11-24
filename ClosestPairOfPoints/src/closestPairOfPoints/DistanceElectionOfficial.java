package closestPairOfPoints;

public class DistanceElectionOfficial {
	private static double bestDistance = Double.MAX_VALUE;
	
	public static void castBestDistance(double d) {
		if(d < bestDistance) {
			bestDistance = d;
			System.out.println("Best distance to date found: " + bestDistance);
		}
	}
	
}
