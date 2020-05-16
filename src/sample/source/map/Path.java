/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
import sample.source.imap.iPath;

import java.util.List;

/**
 * Class represents Path
 */
public class Path implements iPath {

    private List<Coordinate> path; /* list of coordinates (make path of vehicle) */


    //                                     Private Functions

    /**
     * Empty constructor for yaml
     */
    private Path() {

    }

    /**
     * Distance between two coordinates
     * @param a first coor
     * @param b second coor
     * @return distance between coordinates
     */
    private double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    //                                iPath Interface

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

        if (a == null) {
            return null;
        }

        //calculate move
        double driven = (distance - length) / (getDistanceBetweenCoordinates(a, b));
        return new Coordinate(a.getX() + (b.getX() - a.getX()) * driven, a.getY() + (b.getY() - a.getY()) * driven);
    }

    public double getPathSize() {
        double size = 0;
        for(int i=0; i< path.size() - 1; i++) {
            size += getDistanceBetweenCoordinates(path.get(i), path.get(i+1));
        }
        return size;
    }


    public List<Coordinate> getPath() {
        return path;
    }
}
