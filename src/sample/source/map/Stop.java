package sample.source.map;

import sample.source.imap.iStop;

import java.util.List;

public class Stop implements iStop {
    //Stop ID
    private String id;
    // Coordinate of stop
    private Coordinate c;
    // Under which street is this stop
    private Street streetOfStop;

    // Stop ID and COOR
    public Stop(String id, Coordinate c) {
        this.id = id;
        this.c = c;
    }

    // If stop is placed on existing street
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

    /** Set street by stop **/
    public void setStreet(Street s) {
        streetOfStop = s;
    }

    /** Get street of stop **/
    public Street getStreet() {
        return this.streetOfStop;
    }

    /** Return id of stop **/
    public String getId() {
        return id;
    }

    /** Get coordinates of stop **/
    public Coordinate getCoordinate() {
        return c;
    }

    // Override function equal
    public boolean equals(Object obj) {
        if (!(obj instanceof Stop)) {
            return false;
        }
        Stop a = (Stop) obj;
        return a.getId().equals(getId());
    }

    // Return ID of stop
    @Override
    public String toString() {
        return String.format("stop(%s)", id);
    }
}