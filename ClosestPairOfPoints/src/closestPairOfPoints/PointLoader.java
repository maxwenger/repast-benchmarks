package closestPairOfPoints;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class PointLoader {
    public static int[][] loadPoints(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        Scanner scanny = new Scanner(file);
        ArrayList<int[]> points = new ArrayList<int[]>();

        while(scanny.hasNextLine()) {
            String pointString = scanny.nextLine(); 
            int[] point = getPointsFromString(pointString);
            points.add(point);
        }

        scanny.close();

        int[][] pointArray = arrayListToArray(points);
        return pointArray;
    }

    private static int[][] arrayListToArray(ArrayList<int[]> list) {
        int[][] array = new int[list.size()][];
        array = list.toArray(array);
        return array;
    }

    private static int[] getPointsFromString(String pointString) {
        String pointArray[] = pointString.split(" ");
        int[] point = new int[2];

        point[0] = Integer.parseInt(pointArray[0]);
        point[1] = Integer.parseInt(pointArray[1]);

        return point;
    }
}
