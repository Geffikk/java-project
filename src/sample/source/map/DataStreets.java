package sample.source.map;

import java.util.List;

/** Class for store info about all streets and stops on map **/
public class DataStreets {
    //List of streets
    private List<Street> streets;

    /** Empty constructor for yaml **/
    public DataStreets() {
    }

    /** Normal constructor **/
    public DataStreets(List<Street> streets) {
        this.streets = streets;
    }

    /** Return list of streets (getter for yaml) **/
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
