package sample.source.map;

import sample.source.imap.iDataAutobuses;

import java.util.List;

/** Class for store info about all autobuses on map **/
public class DataAutobuses implements iDataAutobuses {

    private List<Coordinate> coordinates; /* list of coordinates for autobuses */
    private List<Autobus> autobuses; /* list of autobuses */

    /** Empty constructor for yaml **/
    private DataAutobuses() {
    }

    /** Normal constructor **/
    public DataAutobuses(List<Coordinate> coordinates, List<Autobus> autobuses) {
        this.coordinates = coordinates;
        this.autobuses = autobuses;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

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
