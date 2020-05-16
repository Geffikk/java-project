/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.imap;

/* Imports */
import sample.source.map.Line;

import java.util.List;

public interface iDataLines {

    /**
     * Return list of lines (getter for yaml)
     * @return list of lines
     */
    List<Line> getLines();

    /**
     * To string function
     * @return string format
     */
    String toString();
}
