package sample.source.map;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iStreet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Street implements iStreet, Drawable {

    // Street ID, List<Coordinates>, List<Stops>
    private String id;
    private List<Coordinate> coordinates;
    private List<Stop> stops = new ArrayList<>();


    public Street() {
    }

    public Street(String id, Coordinate... coordinates) {
        this.id = id;
        this.coordinates = new ArrayList<>(Arrays.asList(coordinates));
    }


    // If street is in right angle, in this project we do not need
    @Override
    public boolean rightAngle() {
        boolean switcher = true;
        double x = 0;
        double y = 0;
        double result_x;
        double result_y;

        for(Coordinate coordinate:this.coordinates)
        {
            result_x = coordinate.getX() - x;
            result_y = coordinate.getY() - y;

            if (result_x != 0 && result_y != 0 && !switcher) {
                return false;
            }

            switcher = false;
            x = coordinate.getX();
            y = coordinate.getY();
        }
        return true;
    }

    /** Return begin coordinates of street **/
    @Override
    public Coordinate begin() {
        return this.coordinates.get(0);
    }

    /** Return last coordinates of street **/
    @Override
    public Coordinate end() {
        return this.coordinates.get(this.coordinates.size() - 1);
    }

    /** Get all coordinates of street **/
    @Override
    public List<Coordinate> getCoordinates() {
        return Collections.unmodifiableList(this.coordinates);
    }

    /** Return true if one street follow next one **/
    @Override
    public boolean follows(Street s2) {
        return (s2.begin().equals(begin()) || s2.begin().equals(end()) || s2.end().equals(end()) || s2.end().equals(begin()));
    }

    /** add stop to street **/
    @Override
    public boolean addStop(Stop stop1) {
        if(stop1.inStreet(this.coordinates)) {
            this.stops.add(stop1);
            stop1.setStreet(this);
            return true;
        }
        return false;
    }

    /** Get list of stops **/
    @Override
    public List<Stop> getStops() {
        return Collections.unmodifiableList(stops);
    }

    /** Get id of street **/
    @Override
    public String getId() {
        return id;
    }

    /** Get id of street **/
    @Override
    public String toString() {
        return String.format("street(%s)", id);
    }

    @JsonIgnore
    /** Paint streets to GUI **/
    @Override
    public List<Shape> getGUI() {
        return Collections.singletonList(
                new Line(this.coordinates.get(0).getX(), this.coordinates.get(0).getY(), this.coordinates.get(1).getX(), this.coordinates.get(1).getY()));
    }
}
