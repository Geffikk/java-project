package sample.source.imap;

import sample.source.map.Coordinate;
import sample.source.map.Street;

public interface iStop {

    /**************************************
     * Get street of stop (getter for yaml)
     * @return -> street where is stop
     **************************************/
    Street getStreet();

    /********************
     * Set street to stop
     * @param s -> street
     ********************/
    void setStreet(Street s);

    /*************************************
     * Return id of stop (getter for yaml)
     * @return -> id
     *************************************/
    String getId();

    /*******************************************
     * Get coordinates of stop (getter for yaml)
     * @return -> coordinate
     *******************************************/
    Coordinate getCoordinate();

    /*******************************************
     * Override equal function
     * @param obj -> stop to compare
     * @return -> return true if stops are equal
     *******************************************/
    boolean equals(Object obj);

    /**************************
     * Return ID of stop
     * @return -> string format
     **************************/
    String toString();

}

