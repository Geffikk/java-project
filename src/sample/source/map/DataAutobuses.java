/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
import sample.source.imap.iDataAutobuses;

import java.util.List;

/**
 * Class for store info about all autobuses on map
 */
public class DataAutobuses implements iDataAutobuses {

    private List<Coordinate> coordinates; /* list of coordinates for autobuses */
    private List<Autobus> autobuses; /* list of autobuses */

    /**
     * Empty constructor for yaml
     */
    private DataAutobuses() {
    }


    /**
     * Returns coordinates (getter for yaml)
     * @return coordinates
     */
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * Returns autobuses (getter for yaml)
     * @return autobuses
     */
    public List<Autobus> getAutobuses() {
        return autobuses;
    }

}
