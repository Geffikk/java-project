/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
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
