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

import java.sql.Time;


public interface TimerUpdate2 {

    /*******************************
     * Update gui-s on map
     * @param mapTime system time
     *******************************/
    void update2(Time mapTime, Street closeStreet);

    /************************
     * Set class from text
     * @param closeStreetName
     * @return street
     ************************/
    Street initializeClosedStreet(String closeStreetName);

    void clearArray();

}
