package sample.source.imap;

import sample.source.map.Coordinate;
import sample.source.map.Stop;

import java.util.List;

public interface iStreet {

    /***************************************
     * Return begin coordinates of street
     * @return first coordinate of street
     ***************************************/
    Coordinate begin();

    /**************************************
     * Return last coordinates of street
     * @return last coordinate of street
     **************************************/
    Coordinate end();

    /**********************************
     * Get all coordinates of street
     * @return coordinates of street
     **********************************/
    List<Coordinate> getCoordinates();

    /**************************
     * Get list of stops
     * @return list of stops
     **************************/
    java.util.List<Stop> getStops();

    /*************************
     * Get id of street
     * @return id of street
     *************************/
    java.lang.String getId();

    /******************
     * slowdown vehicle
     * @return delay
     ******************/
    double getDelay();

    /*****************************
     * Get id of street
     * @return string format ID
     *****************************/
    String toString();
}
