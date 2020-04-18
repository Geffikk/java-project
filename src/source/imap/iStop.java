package java.imap;

import java.map.Stop;
import java.map.Coordinate;
import java.map.Street;
import java.util.List;

public interface iStop {

    static Stop defaultStop(String stop1, Coordinate c1) {
        Stop stop = new Stop(stop1, c1);
        return stop;
    }

    public boolean inStreet(List<Coordinate> street);
    public Street getStreet();
    public void setStreet(Street s);
    public String getId();
    public Coordinate getCoordinate();
}

