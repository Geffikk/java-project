package sample.source.map;

public class Coordinate {
    private double x;
    private double y;

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

}

