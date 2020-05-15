package sample.source.imap;

public interface iCoordinate {

    /**
     * Return X
     * @return coordinate X
     */
    double getX();

    /**
     * Return Y
     * @return coordinate Y
     */
    double getY();

    /**
     * Override function equal
     * @param obj coordinate to compare
     * @return true if coordinates are equal
     */
    boolean equals(Object obj);

    /**
     * Override function toString for printing
     * @return string format
     */
    String toString();
}
