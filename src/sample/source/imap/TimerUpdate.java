/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.imap;

/* Imports */
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sample.source.map.Street;

import java.sql.Time;

public interface TimerUpdate {

    /*******************************
     * Update gui-s on map
     * @param mapTime system time
     *******************************/
    void update(Time mapTime);

    /*************************************************
     * Action - click on vehicle
     * @param actualPosition actual position of vehicle
     * @param label departures
     * @param label2 stops
     * @param traceOfStops represent trace of stops
     * @param content root pane
     * @param actPositionText constant text
     *************************************************/
    void onClickVehicle(Label actualPosition, Label label, Label label2, Line traceOfStops, Pane content, Label actPositionText);

    /*************************************************
     * Action - click on pane
     * @param actualPosition actual position of vehicle
     * @param label departures
     * @param label2 stops
     * @param traceOfStops represent trace of stops
     * @param pane anchor pane
     * @param content root pane
     * @param actPositionText constant text
     *************************************************/
    void onClickPane(Label actualPosition, Label label, Label label2, Line traceOfStops, Pane pane, Pane content, Label actPositionText);

    /***************************************************************
     * Set load on street
     * @param delayStr name of street which will be slowdown
     * @param switcher flag (process this function just one time)
     * @param howSlow set slowdown
     ***************************************************************/
    void setDelayStreet(String delayStr, Boolean switcher, double howSlow);

    /************************************************************
     * Modify default time
     * @param travelInTime time value of shift vehicles
     ************************************************************/
    void setBaseTime(double travelInTime);

    void testNewRoute(Street closedStreet);
}
