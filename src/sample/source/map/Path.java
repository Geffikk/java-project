package sample.source.map;

import java.util.Collections;
import java.util.List;

public class Path {

    private List<Coordinate> path;

    public Path(List<Coordinate> path) {
        this.path = path;
    }

    private double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public Coordinate getCoordinateByDistance(double distance) {
        double length = 0;

        Coordinate a = null;
        Coordinate b = null;
        for(int i=0; i< path.size() - 1; i++) {
            a = path.get(i);
            b = path.get(i+1);

            if(length + getDistanceBetweenCoordinates(a, b) >= distance) {
                break;
            }
            length += getDistanceBetweenCoordinates(a, b);
        }

        if (a == null || b == null) {
            return null;
        }
        double driven = (distance - length) / getDistanceBetweenCoordinates(a, b);
        return new Coordinate(a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);

    }
    
}
