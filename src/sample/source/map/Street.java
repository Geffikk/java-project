package sample.source.map;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iStreet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Street implements iStreet, Drawable {

    private String id;
    private List<Coordinate> coordinates;
    private List<Stop> stops  = new ArrayList<>();

    public Street(String id, Coordinate... coordinates) {
        this.id = id;
        this.coordinates = new ArrayList<>(Arrays.asList(coordinates));
    }

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

    @Override
    public Coordinate begin() {
        return this.coordinates.get(0);
    }

    @Override
    public Coordinate end() {
        return this.coordinates.get(this.coordinates.size() - 1);
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return Collections.unmodifiableList(this.coordinates);
    }

    @Override
    public boolean follows(Street s2) {
        return (s2.begin().equals(begin()) || s2.begin().equals(end()) || s2.end().equals(end()) || s2.end().equals(begin()));
    }

    @Override
    public boolean addStop(Stop stop1) {
        if(stop1.inStreet(this.coordinates)) {
            this.stops.add(stop1);
            stop1.setStreet(this);
            return true;
        }
        return false;
    }

    @Override
    public List<Stop> getStops() {
        return Collections.unmodifiableList(stops);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("stop(%s)", id);
    }

    @Override
    public List<Shape> getGUI() {
        return Arrays.asList(
                new Text(this.coordinates.get(0).getX() + (this.coordinates.get(1).getX()/4), this.coordinates.get(0).getY() + (this.coordinates.get(1).getY()/4), this.id),
                new Line(this.coordinates.get(0).getX(), this.coordinates.get(0).getY(), this.coordinates.get(1).getX(), this.coordinates.get(1).getY()));
    }
}
