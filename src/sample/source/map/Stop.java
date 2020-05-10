package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iStop;

import java.util.Arrays;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Stop implements iStop, Drawable {

    //Stop ID
    private String id;
    // Coordinate of stop
    private Coordinate coordinate;
    // Under which street is this stop
    private Street streetOfStop;

    /** Empty constructor for yaml **/
    public Stop() {
    }

    /** Normal constructor **/
    public Stop(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }


    /** Not used **/
    public boolean inStreet(List<Coordinate> coor) {

        boolean switcher = true;
        double x = 0;
        double y = 0;
        double result_x;
        double result_y;

        for (Coordinate coordinate : coor) {
            result_x = coordinate.getX();
            result_y = coordinate.getY();

            if (((x <= this.coordinate.getX() && this.coordinate.getX() <= result_x) ||
                    (x >= this.coordinate.getX() && this.coordinate.getX() >= result_x)) &&
                    ((y <= this.coordinate.getY() && this.coordinate.getY() <= result_y) ||
                            (y >= this.coordinate.getY() && this.coordinate.getY() >= result_y)) &&
                    !switcher) {
                return true;
            }

            switcher = false;
            x = coordinate.getX();
            y = coordinate.getY();
        }
        return false;
    }

    /** Return id of stop (getter for yaml) **/
    public String getId() {
        return id;
    }

    /** Get coordinates of stop (getter for yaml) **/
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /** Get street of stop (getter for yaml) **/
    public Street getStreet() {
        return this.streetOfStop;
    }

    /** Set street by stop **/
    public void setStreet(Street s) {
        streetOfStop = s;
    }


    // Override function equal
    public boolean equals(Object obj) {
        if (!(obj instanceof Stop)) {
            return false;
        }
        Stop a = (Stop) obj;
        return a.getId().equals(getId());
    }

    /** Return ID of stop **/
    @Override
    public String toString() {
        return String.format("stop(%s)", id);
    }

    /** Paint stop to GUI **/
    @JsonIgnore
    public List<Shape> getGUI() {

        Rectangle rect = new Rectangle(coordinate.getX() - 6, coordinate.getY() - 6, 12, 12);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.GREY);

        return Arrays.asList(
                new Text(coordinate.getX() - 22, coordinate.getY() + 20, this.id),
                rect);
    }

}