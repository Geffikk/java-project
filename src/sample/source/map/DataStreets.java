package sample.source.map;

import sample.source.imap.iDataStreets;

import java.util.List;

/**
 * Class for store info about all streets and stops on map
 */
public class DataStreets implements iDataStreets {

    private List<Street> streets; /* list of streets */

    /**
     * Empty constructor for yaml
     */
    private DataStreets() {
    }

    /**
     * Returns streets (getter for yaml)
     * @return streets
     */
    public List<Street> getStreets() {
        return streets;
    }


}
