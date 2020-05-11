package sample.source.map;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import sample.source.imap.iCoordinate;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Coordinate implements iCoordinate {

    private double x; /* coordinate X */
    private double y; /* coordinate Y */

    private Coordinate() {
    }

    /**
     * Constructor of coordines (coordinates have to be positive)
     * @param x -> coordinate X
     * @param y -> coordinate Y */
    public Coordinate(double x, double y) {
        if (x > 0 && y > 0) {
            this.x = x;
            this.y = y;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Coordinate)) {
            return false;
        }
        Coordinate a = (Coordinate) obj;
        return a.getX() == (getX()) && a.getY() == (getY());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
