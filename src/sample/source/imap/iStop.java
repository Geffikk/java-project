/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.imap;

/* Imports */
import sample.source.map.Coordinate;
import sample.source.map.Street;

public interface iStop {

    /**************************************
     * Get street of stop (getter for yaml)
     * @return street where is stop
     **************************************/
    Street getStreet();

    /********************
     * Set street to stop
     * @param s street
     ********************/
    void setStreet(Street s);

    /*************************************
     * Return id of stop (getter for yaml)
     * @return id
     *************************************/
    String getId();

    /*******************************************
     * Get coordinates of stop (getter for yaml)
     * @return coordinate
     *******************************************/
    Coordinate getCoordinate();

    /*******************************************
     * Override equal function
     * @param obj stop to compare
     * @return return true if stops are equal
     *******************************************/
    boolean equals(Object obj);

    /**************************
     * Return ID of stop
     * @return string format
     **************************/
    String toString();

}

