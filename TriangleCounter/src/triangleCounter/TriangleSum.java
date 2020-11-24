package triangleCounter;

public class TriangleSum {

	private static int sum = 0;
	
	public static void incrementSum() {
		sum++;
	}
	
	public static int getSum() { 
		return sum;
	}
	
	public static void resetSum() {
		sum = 0;
	}
}
