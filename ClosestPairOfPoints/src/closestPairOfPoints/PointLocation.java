package closestPairOfPoints;

import java.util.Objects;

public class PointLocation {

	private int x;
	private int y;
	
	public PointLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double distanceFrom(PointLocation p) {
        double distance = Math.pow((p.getX() - x), 2) + Math.pow((p.getY() - y), 2);
        distance = Math.sqrt(distance);
        distance = Math.abs(distance);
        
        return distance;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof PointLocation) {
			return this.hashCode() == ((PointLocation)o).hashCode();
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(x,y);
	}
    
}
