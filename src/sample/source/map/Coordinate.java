package sample.source.map;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Coordinate {
    private double x;
    private double y;

    private Coordinate() {
    }

    public Coordinate(double x, double y) {
        if (x > 0 && y > 0){
            this.x = x;
            this.y = y;
        }
    }

    public static Coordinate create(int x, int y){

        if (x < 0 || y < 0) {
            return null;
        }

        return new Coordinate(x,y);
    }

    public double getX() {
        return x;
    }

    public double getY() { return y; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Coordinate)) {
            return false;
        }
        Coordinate a = (Coordinate) obj;
        return a.getX()==(getX()) && a.getY()==(getY());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


}

