package sample.source.imap;

import sample.source.map.Line;
import sample.source.map.Stop;
import sample.source.map.Street;


public interface iLine {
    static Line defaultLine(String id) {
        Line line = new Line(id);
        return line;
    }

<<<<<<< HEAD
    boolean addStop(Stop stops);
    //boolean addStop(Stop... stops);
    boolean addStreet(Street... street);
=======
//    boolean addStop(Stop stop);
//    //boolean addStop(Stop... stops);
//    boolean addStreet(Street... street);
>>>>>>> master

    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();
}
