package sample.source.imap;

import sample.source.map.Line;
import sample.source.map.Path;
import sample.source.map.Street;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public interface iAutobus {

    /******************************************************
     * Set starting distance of vehicle
     * @param startDistance -> starting position of vehicle
     ******************************************************/
    void setDistance(double startDistance);

    /***************************************************************
     * Clear label -> (departures), line -> (line), label -> (stops)
     * @param actualPosition -> actual position of vehicle
     * @param label -> departures
     * @param label2 -> stops
     * @param traceOfStops -> represent trace of stops
     * @param pane -> anchor pane
     * @param content -> root pane
     * @param actPositionText -> constant text
     ***************************************************************/
    void clearDepartures(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content, Label actPositionText);

    /***************************************************************
     * Set label -> (departures), line -> (line), label -> (stops)
     * @param actualPosition -> actual position of vehicle
     * @param label -> departures
     * @param label2 -> stops
     * @param traceOfStops -> represent trace of stops
     * @param content -> root pane
     * @param actPositionText -> constant text
     ***************************************************************/
    void setDepartures(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content, Label actPositionText);

    /***************************************
     * Get path of autobus (getter for yaml)
     * @return -> path
     ***************************************/
    Path getPath();

    /*********************
     * Get line of autobus
     * @return -> line
     *********************/
    Line getLine();

    /*********************************************
     * Set line of autobus (setting lines in main)
     * @param line -> identify line of vehicle
     *********************************************/
    void set_line(Line line);

    /***************************************************
     * Set street on which autobus is
     * @param street -> identify street where is vehicle
     ***************************************************/
    void setAutobusIsOnStreet(Street street);

    /**************************
     * To string function
     * @return -> string format
     **************************/
    String toString();


}
