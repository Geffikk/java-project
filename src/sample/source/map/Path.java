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
    public Coordinate getCoordinateByDistance(double distance, Autobus autobus) {

        // Length of all ways in path
        double length = 0;

        // Initialize coordinates two null (get coordinates with function getDistBetweenCoor)
        Coordinate a = null;
        Coordinate b = null;

        // Iterate over all coordinates
        for(int i=0; i< path.size() - 1; i++) {
            a = path.get(i);
            b = path.get(i + 1);

            //when autobus get on next street assing street to autobus
            if(length + (getDistanceBetweenCoordinates(a, b)) >= distance) {
                for (Street str: autobus.getLine().getStreetList()) {
                    if(str.getCoordinates().size() == 3 && i < path.size() - 2){
                        if((str.begin().equals(path.get(i)) && str.end().equals(path.get(i + 2))) || (str.end().equals(path.get(i)) && str.begin().equals(path.get(i + 2)))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                    }
                    else{
                        if((str.begin().equals(path.get(i)) && str.end().equals(path.get(i + 1))) || (str.end().equals(path.get(i)) && str.begin().equals(path.get(i + 1)))){
                            autobus.setAutobusIsOnStreet(str);
                            break;
                        }
                    }
                }
                break;
            }

            // Add path between 2 coords to final way
            length += getDistanceBetweenCoordinates(a, b);
        }

        if (a == null || b == null) {
            return null;
        }

        //calculate move
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
