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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.source.imap.TimerUpdate;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class Controller {

    private List<TimerUpdate> updates = new ArrayList<>(); /* update */
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
    private ImageView iv; /* display image (SAD Presov) */
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

    /**
     * Set image SAD Presov to bottom right corner
     */
    @FXML
    public void setImages() {

        Image image = new Image("file:///C:\\Users\\Geffik\\Desktop\\java-project\\src\\sample\\logo_sad_presov.png");
        iv.setImage(image);
        iv.setScaleX(2.05);
        iv.setScaleY(2.05);
        iv.setTranslateY(280);
        iv.setTranslateX(20);
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
        }
    }

    /**
     * Timer of aplication (system is updated every n seconds)
     * @param scale timer scale
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
      *	@exception ParseException error
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
