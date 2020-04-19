package sample.source.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iStop;

import java.util.Arrays;
import java.util.List;


public class Stop implements iStop, Drawable {

    private String id;
    private Coordinate c;
    private Street streetofStop;

    public Stop() {
    }

    public Stop(String id, Coordinate c) {
        this.id = id;
        this.c = c;
    }

    public boolean inStreet(List<Coordinate> coor) {

        boolean switcher = true;
        double x = 0;
        double y = 0;
        double result_x;
        double result_y;

        for (Coordinate coordinate : coor) {
            result_x = coordinate.getX();
            result_y = coordinate.getY();

            if (((x <= c.getX() && c.getX() <= result_x) ||
                    (x >= c.getX() && c.getX() >= result_x)) &&
                    ((y <= c.getY() && c.getY() <= result_y) ||
                            (y >= c.getY() && c.getY() >= result_y)) &&
                    !switcher) {
                return true;
            }

            switcher = false;
            x = coordinate.getX();
            y = coordinate.getY();
        }
        return false;
    }


    public void setStreet(Street s) {
        streetofStop = s;
    }

    public Street getStreet() {
        return this.streetofStop;
    }

    public String getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return c;
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

    public List<Shape> getGUI() {
        return Arrays.asList(
                new Text(this.c.getX() + (this.c.getX()/4), this.c.getY() + (this.c.getY()/4), this.id),
                new Circle(c.getX(),c.getY(), 8, Color.GREEN));
    }
}