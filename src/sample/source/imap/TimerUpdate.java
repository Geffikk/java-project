package sample.source.imap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.sql.Time;

public interface TimerUpdate {

    /**
     * Update gui-s on map
     * @param mapTime -> system time */
    void update(Time mapTime);

    /**
     * Action -> click on vehicle
     * @param label -> departures
     * @param label2 -> stops
     * @param traceOfStops -> represent trace of stops
     * @param content -> root pane */
    void onClickVehicle(Label label, Label label2, Line traceOfStops, Pane content);

    /**
     * Action -> click on pane
     * @param label -> departures
     * @param label2 -> stops
     * @param traceOfStops -> represent trace of stops
     * @param pane -> anchor pane
     * @param content -> root pane */
    void onClickPane(Label label, Label label2, Line traceOfStops, Pane pane, Pane content);

    /**
     * Set load on street
     * @param delayStr -> name of street which will be slowdown
     * @param switcher -> flag (process this function just one time)
     * @param slowStreetText -> label, show slowdown streets
     * @param howSlow -> set slowdown */
    void setDelayStreet(String delayStr, Boolean switcher, Label slowStreetText, double howSlow);

    /**
     * Modify default time
     * @param travelInTime -> time value of shift vehicles
     * @param travelInTimeActual -> actual time value of vehicles */
    void setBaseTime(double travelInTime, double travelInTimeActual);
}
