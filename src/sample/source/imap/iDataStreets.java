/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.imap;

/* Imports */
import sample.source.map.Street;

import java.util.List;

public interface iDataStreets {

    /**
     * Return list of streets (getter for yaml)
     * @return list of streets
     */
    List<Street> getStreets();

    /**
     * To string function
     * @return string format
     */
    String toString();
}
