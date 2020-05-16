/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
import sample.source.imap.iDataLines;

import java.util.List;

/**
 * Class for store info about all lines on map
 */
public class DataLines implements iDataLines {

    private List<Line> lines; /* list of lines */

    /**
     * Empty constructor for yaml
     */
    private DataLines() {}

    /**
     * Returns lines (getter for yaml)
     * @return lines
     */
    public List<Line> getLines() {
        return lines;
    }


}
