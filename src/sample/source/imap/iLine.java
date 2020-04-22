package sample.source.imap;

import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.map.Line;
import sample.source.map.Stop;
import sample.source.map.Street;

import java.util.List;

public interface iLine {
    static Line defaultLine(String id) {
        Line line = new Line(id);
        return line;
    }

    boolean addStop(Stop... stops);
    boolean addStreet(Street... street);

    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();
}
