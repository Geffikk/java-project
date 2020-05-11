package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iStop;

import java.util.Arrays;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Stop implements iStop, Drawable {

    private String id; /* stop ID */
    private Coordinate coordinate; /* coordinate of stop */
    private Street streetOfStop; /* under which street is this stop */

    /** Empty constructor for yaml **/
    public Stop() {}

    /** Normal constructor **/
    public Stop(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Street getStreet() {
        return this.streetOfStop;
    }

    public void setStreet(Street s) {
        streetOfStop = s;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Stop)) {
            return false;
        }
        Stop a = (Stop) obj;
        return a.getId().equals(getId());
    }

    @Override
    public String toString() {
        return String.format("stop(%s)", id);
    }

    @JsonIgnore
    public List<Shape> getGUI() {

        Rectangle rect = new Rectangle(coordinate.getX() - 6, coordinate.getY() - 6, 12, 12);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.GREY);

        return Arrays.asList(new Text(coordinate.getX() - 22, coordinate.getY() + 20, this.id), rect);
    }
}