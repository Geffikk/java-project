/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.imap;

/* Imports */
import sample.source.map.Autobus;
import sample.source.map.Coordinate;

import java.util.List;

public interface iDataAutobuses {

    /**
     * Return list of coordiantes (getter for yaml)
     * @return list of coordinates
     */
    List<Coordinate> getCoordinates();

    /**
     * Return list of autobuses (getter for yaml)
     * @return list of vehicles
     */
    List<Autobus> getAutobuses();

    /**
     * To string function
     * @return string format
     */
    String toString();
}
