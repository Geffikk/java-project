/**
-  PROJECT: Simulacia liniek MHD
-  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
-  Date: 10.5.2020
-  School: VUT Brno
*/

/* Package */
package sample;

/* Imports */
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate2;
import sample.source.imap.TimerUpdate;
import sample.source.map.Street;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class Controller {

    private List<TimerUpdate> updates = new ArrayList<>(); /* update */
    private List<TimerUpdate2> updates2 = new ArrayList<>();
    private Time mapTime = new Time(6, 20, 0); /* base time map */
    private AnimationTimer timer; /* timeline for updates */
    private int modifyTimer = 0; /* change time value */

    @FXML
    private Pane content; /* main pane */
    @FXML
    private Label showTime; /* display clock in gui */
    @FXML
    private Label showDepartures; /* display departures (after click on Vehicel gui) */
    @FXML
    private Label showPathStops; /* display stops (after click on Vehicel gui) */
    @FXML
    private Pane rightSide; /* right side of main pane */
    @FXML
    private Line traceOfStops; /* display line (representing trace of stops) */
    @FXML
    private TextField delayStreet; /* modify speed on street */
    @FXML
    private TextField specificTime; /* modify default time */
    @FXML
    private Slider setTrafficLevel; /* set load to traffic */
    @FXML
    private Slider speedLevel; /* speed of system */
    @FXML
    private Label actualPositionLabel; /* label for display actual position */
    @FXML
    private Label actPositionText;
    @FXML
    private TextField nameOfClosedStreet;
    static String closeStreetText = null;
    static Street closedStreet = null;

    @FXML
    public void clearTrace(){
        for(TimerUpdate2 update2:updates2) {
            update2.clearArray();
        }
    }

    @FXML
    public void setCloseStreet(){
        closeStreetText = nameOfClosedStreet.getText();
        closedStreet = null;

        for (TimerUpdate2 update2: updates2) {
            closedStreet = update2.initializeClosedStreet(closeStreetText);
            if (closedStreet != null) {
                break;
            }
        }

        if(closedStreet != null) {
            for (TimerUpdate2 update2 : updates2) {
                update2.update2(mapTime, closedStreet);
            }
        }
    }

    @FXML
    public void setNewRoute(){
        if (closeStreetText == null) {
            System.out.println("ERROR text is null");
        }

        for(TimerUpdate2 update2: updates2) {
            update2.setShapes1();
        }

        for(TimerUpdate update: updates) {
            update.testNewRoute(closedStreet);
        }
    }

    /**
     *  speed of system
     */
    @FXML
    public void setSpeedLevel() {

        double scale = speedLevel.getValue();
        scale += 3;
        timer.stop();
        startTime(scale);
    }

    /**
     * Center slider cursor (only integer value)
     */
    @FXML
    private void centerCursorOnDelay() {

        if(setTrafficLevel.getValue() < 0.5) { setTrafficLevel.setValue(0); }
        else if(setTrafficLevel.getValue() < 1.5) { setTrafficLevel.setValue(1); }
        else if(setTrafficLevel.getValue() < 2.5) { setTrafficLevel.setValue(2); }
        else { setTrafficLevel.setValue(3); }
    }

    /**
     * Interactive activity (set load on street)
     */
    @FXML
    private void setDelay() {

        double howSlow;
        if(setTrafficLevel.getValue() < 0.5) { howSlow = 1; }
        else if(setTrafficLevel.getValue() < 1.5) { howSlow = 0.75; }
        else if(setTrafficLevel.getValue() < 2.5) { howSlow = 0.5; }
        else { howSlow = 0.25; }

        for(TimerUpdate update : updates) {
            update.setDelayStreet(delayStreet.getText(), true, howSlow);
        }
    }

    /**
     * Modify zoom of all system
     */
    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;

        if (content.getScaleX() < 0.9) {
            zoom = 1.01;
        }

        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    /**
     * Set elements to gui
     * @param elements elements to set
     */
    public void setElements(List<Drawable> elements) {

        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
            if(drawable instanceof TimerUpdate) {
                updates.add((TimerUpdate) drawable);
            }
            if(drawable instanceof TimerUpdate2) {
                updates2.add((TimerUpdate2) drawable);
            }
        }
    }

    /**
     * Timer of aplication (system is updated every n seconds)
     * @param scale scale of time
     */
    public void startTime(double scale) {

        showTime.setTranslateX(10);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                specificTime.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        try {
                            setSpecTime();
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                setTrafficLevel.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        setDelay();
                    }
                });

                delayStreet.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        setDelay();
                    }
                });

                modifyTimer++;
                if (modifyTimer >= 32767) {
                    modifyTimer = 0;
                }
                if(modifyTimer % scale < 1) {
                    showTime.setText(mapTime.toString());
                    mapTime.setSeconds(mapTime.getSeconds() + 3);
                }
                if (modifyTimer % scale < 1) {
                    for (TimerUpdate update : updates) {
                        update.onClickVehicle(actualPositionLabel,showDepartures, showPathStops, traceOfStops, content, actPositionText);
                        update.onClickPane(actualPositionLabel, showDepartures, showPathStops, traceOfStops, rightSide, content, actPositionText);
                        update.update(mapTime);
                    }
                }
            }
        };
        timer.start();
    }

    /** 
      * Set default time to aplication 
      *	@exception ParseException when time is in wrong format
      */
    @FXML
    public void setSpecTime() throws ParseException {

        String shiftInTime = specificTime.getText();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date d1 = format.parse(shiftInTime);
            Date d2 = format.parse(mapTime.toString());

            mapTime = new Time(d1.getHours(), d1.getMinutes(), d1.getSeconds());

            double timeDiff = d1.getTime() - d2.getTime();
            double allMinutes = (timeDiff / 3600000) * 60;

            System.out.println(allMinutes);

            for (TimerUpdate update : updates) {
                update.setBaseTime(allMinutes);
            }
        }
        catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid time format (Time format HH:mm:ss)");
            alert.showAndWait();
        }
    }
}
