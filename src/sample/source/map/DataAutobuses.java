package sample.source.map;

import java.util.List;

/** Class for store info about all autobuses on map **/
public class DataAutobuses {
    //List of coordinates for autobuses
    private List<Coordinate> coordinates;
    //List of autobuses
    private List<Autobus> autobuses;

    /** Empty constructor for yaml **/
    private DataAutobuses() {
    }

    /** Normal constructor **/
    public DataAutobuses(List<Coordinate> coordinates, List<Autobus> autobuses) {
        this.coordinates = coordinates;
        this.autobuses = autobuses;
    }

    /** Return list of coordiantes (getter for yaml) **/
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /** Return list of autobuses (getter for yaml) **/
    public List<Autobus> getAutobuses() {
        return autobuses;
    }

    @Override
    public String toString() {
        return "Data{" +
                "coordinates=" + coordinates +
                ", autobuses=" + autobuses +
                '}';
    }

}
