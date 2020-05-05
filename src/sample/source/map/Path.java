package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Path {

    private List<Coordinate> path;


    public Path() {
    }

    public Path(List<Coordinate> path) {
        this.path = path;
    }

    public List<Coordinate> getPath() {
        return path;
    }

    /** Distance between two coordinates **/
    private double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        // Everything ok
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));

    }


    /** Calculate new coordinates after move **/
    public Coordinate getCoordinateByDistance(double distance, Path pathOfAutobus, Coordinate startOfAutobus) {

        // Length of all ways in path
        double length = 0;

        int j = pathOfAutobus.getPath().indexOf(startOfAutobus);


        // Initialize coordinates two null (get coordinates with function getDistBetweenCoor)
        Coordinate a = null;
        Coordinate b = null;

        // Iterate over all coordinates
        for(int i=j; i< path.size() - 1; i++) {
            a = path.get(i);
            b = path.get(i + 1);

            if(length + (getDistanceBetweenCoordinates(a, b)) >= distance) {
                break;
            }
            // Add path between 2 coords to final way
            length += getDistanceBetweenCoordinates(a, b);
        }

        if (a == null || b == null) {
            return null;
        }

        // Este pozriet nato
        double driven = (distance - length) / (getDistanceBetweenCoordinates(a, b));
        return new Coordinate(a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);
    }

    // Return size of all the way
    @JsonIgnore
    public double getPathSize() {
        double size = 0;
        for(int i=0; i< path.size() - 1; i++) {
            size += getDistanceBetweenCoordinates(path.get(i), path.get(i+1));
        }
        return size;
    }
}
