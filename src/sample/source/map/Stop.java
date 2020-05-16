/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
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

/**
 * Class represents Stop
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Stop implements iStop, Drawable {

    private String id; /* stop ID */
    private Coordinate coordinate; /* coordinate of stop */
    private Street streetOfStop; /* under which street is this stop */

    /**
     * Empty constructor for yaml
     */
    private Stop() {

    }

    //                                   iStop Interface
    @Override
    public String getId() {
        return id;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public Street getStreet() {
        return this.streetOfStop;
    }

    @Override
    public void setStreet(Street s) {
        streetOfStop = s;
    }

    @Override
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

    //                                      Drawable Interface

    @JsonIgnore
    public List<Shape> getGUI() {

        Rectangle rect = new Rectangle(coordinate.getX() - 6, coordinate.getY() - 6, 12, 12);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.GREY);

        return Arrays.asList(new Text(coordinate.getX() - 22, coordinate.getY() + 20, this.id), rect);
    }
}