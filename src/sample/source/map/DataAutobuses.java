package sample.source.map;

import java.util.List;

public class DataAutobuses {
    private List<Coordinate> coordinates;
    private List<Autobus> autobuses;

    private DataAutobuses() {
    }

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
