package sample.source.map;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        if (x > 0 && y > 0){
            this.x = x;
            this.y = y;
        }
    }

    public static Coordinate create(int x, int y){

        if (x < 0 || y < 0) {
            return null;
        }
        x = x;
        y = y;

        return new Coordinate(x,y);
    }

    public int getX() {
        return x;
    }

    public int getY() { return y; }

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

