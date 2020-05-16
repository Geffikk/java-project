package sample.source.imap;

import sample.source.map.Autobus;
import sample.source.map.Coordinate;

import java.util.List;

public interface iPath {

    /**
     * Calculate new coordinates after move
     * @param distance actual distance of vehicle
     * @param autobus vehicle
     * @return new coordinates displacement vehicle
     */
    Coordinate getCoordinateByDistance(double distance, Autobus autobus);

    /**
     * Return size of all the way
     * @return size of all path
     */
    double getPathSize();

    /**
     * Return path of vehicle
     * @return path
     */
    List<Coordinate> getPath();

}
