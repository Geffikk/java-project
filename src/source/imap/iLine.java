package java.imap;

import java.map.Line;
import java.map.Stop;
import java.map.Street;

public interface iLine {
    static Line defaultLine(String id) {
        Line line = new Line(id);
        return line;
    }

    boolean addStop(Stop stop);

    boolean addStreet(Street street);

    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();
}
