package sample.source.map;

import sample.source.imap.iStop;

import java.util.List;

public class Stop implements iStop {

    private String id;
    private Coordinate c;
    private Street streetofStop;

    public Stop(String id, Coordinate c) {
        this.id = id;
        this.c = c;
    }

    public boolean inStreet(List<Coordinate> coor) {

        boolean switcher = true;
        int x = 0;
        int y = 0;
        int result_x;
        int result_y;

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
}