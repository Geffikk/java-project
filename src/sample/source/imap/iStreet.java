package sample.source.imap;

import sample.source.map.Stop;
import sample.source.map.Street;
import sample.source.map.Coordinate;

import java.util.List;

public interface iStreet {

    static Street defaultStreet(String id, Coordinate... coordinate) {
        Street str = new Street(id, coordinate);
        if(str.rightAngle()) {
            return str;
        }
        else {
            return null;
        }
    }

    public boolean rightAngle();

    Coordinate begin();

    Coordinate end();

    List<Coordinate> getCoordinates();

    boolean follows(Street s2);

    boolean addStop(Stop stop1);

    java.util.List<Stop> getStops();

    java.lang.String getId();
}
