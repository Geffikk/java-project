package sample.source.map;

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
