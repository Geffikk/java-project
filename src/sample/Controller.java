/*
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
    private Label slowStreetsLabel; /* display streets with delay */
    @FXML
    private Slider setTrafficLevel; /* set load to traffic */
    @FXML
    private Slider speedLevel;

    /** set image SAD Presov to bottom right corner **/
    @FXML
    public void setImages() {

        Image image = new Image("file:///C:\\Users\\Geffik\\Desktop\\java-project\\src\\sample\\logo_sad_presov.png");
        iv.setImage(image);
        iv.setScaleX(2.05);
        iv.setScaleY(2.05);
        iv.setTranslateY(280);
        iv.setTranslateX(20);
    }

    /** set speed of system **/
    @FXML
    public void setSpeedLevel() {

        System.out.println(speedLevel.getValue());
        double scale = speedLevel.getValue();
        scale += 3;
        timer.stop();
        startTime(scale);
    }

    /* center slider cursor (only integer value) */
    @FXML
    private void centerCursorOnDelay() {

        if(setTrafficLevel.getValue() < 0.5) { setTrafficLevel.setValue(0); }
        else if(setTrafficLevel.getValue() < 1.5) { setTrafficLevel.setValue(1); }
        else if(setTrafficLevel.getValue() < 2.5) { setTrafficLevel.setValue(2); }
        else { setTrafficLevel.setValue(3); }
    }

    /** interactive activity (set load on street) **/
    @FXML
    private void setDelay() {

        double howSlow;
        if(setTrafficLevel.getValue() < 0.5) { howSlow = 1; }
        else if(setTrafficLevel.getValue() < 1.5) { howSlow = 0.75; }
        else if(setTrafficLevel.getValue() < 2.5) { howSlow = 0.5; }
        else { howSlow = 0.25; }

        for(TimerUpdate update : updates) {
            update.setDelayStreet(delayStreet.getText(), true, slowStreetsLabel, howSlow);
        }
    }

    /** modify zoom of all system **/
    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
        System.out.println(event.getDeltaY());
        System.out.println(content.getScaleX());
        System.out.println(content.getScaleY());

        if (content.getScaleX() < 0.8058044288328456) {
            zoom = 1.01;
        }

        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    /** set elements to gui **/
    public void setElements(List<Drawable> elements) {

        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
            if(drawable instanceof TimerUpdate) {
                updates.add((TimerUpdate) drawable);
            }
        }
    }

    /** timer of aplication (system is updated every n seconds) **/
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

                modifyTimer++;
                if (modifyTimer >= 32767) {
                    modifyTimer = 0;
                }
                if(modifyTimer % scale < 1) {
                    showTime.setText(mapTime.toString());
                    mapTime.setSeconds(mapTime.getSeconds() + 1);
                }
                if (modifyTimer % scale < 1) {
                    for (TimerUpdate update : updates) {
                        update.onClickVehicle(showDepartures, showPathStops, traceOfStops, content);
                        update.onClickPane(showDepartures, showPathStops, traceOfStops, rightSide, content);
                        update.update(mapTime);
                    }
                }
            }
        };
        timer.start();
    }

    /** set default time to aplication **/
    @FXML
    public void setSpecTime() throws ParseException {

        String shiftInTime = specificTime.getText();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d1 = format.parse(shiftInTime);
        Date d2 = format.parse(mapTime.toString());
        Date d3 = format.parse("6:20:00");

        mapTime = new Time(d1.getHours(), d1.getMinutes(), d1.getSeconds());
        double timeDiffActual = d2.getTime() - d3.getTime();
        double allMinutesActual = (timeDiffActual / 3600000) * 60;

        double timeDiff = d1.getTime() - d2.getTime();
        double allMinutes = (timeDiff / 3600000) * 60;

        for (TimerUpdate update : updates) {
            update.setBaseTime(allMinutes, allMinutesActual);
        }
    }
}
