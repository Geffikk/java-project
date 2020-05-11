package sample.source.map;

import sample.source.imap.iDataStreets;

import java.util.List;

/** Class for store info about all streets and stops on map **/
public class DataStreets implements iDataStreets {

    private List<Street> streets; /* list of streets */

    /** Empty constructor for yaml **/
    public DataStreets() {
    }

    /** Normal constructor **/
    public DataStreets(List<Street> streets) {
        this.streets = streets;
    }

    public List<Street> getStreets() {
        return streets;
    }

    @Override
    public String toString() {
        return "DataStreets{" +
                "streets=" + streets +
                '}';
    }
}
