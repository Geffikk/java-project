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
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sample.source.imap.Drawable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.source.imap.TimerUpdate;
import javafx.animation.KeyValue;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class Controller {

    private List<TimerUpdate> updates = new ArrayList<>(); /* update */
    private Time mapTime = new Time(6, 20, 0); /* base time map */
    private AnimationTimer timer; /* timeline for updates */
    private int i = 0;
    private static Integer switcherSong = 0; /* pause on/off song */
    private MediaPlayer mediaPlayer; /* store media */
    private double howSlow;

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

    /** Set image SAD Presov to bottom right corner **/
    @FXML
    public void setImages() {
        Image image = new Image("file:///C:\\Users\\Geffik\\Desktop\\java-project\\src\\sample\\logo_sad_presov.png");
        iv.setImage(image);
        iv.setScaleX(2.05);
        iv.setScaleY(2.05);
        iv.setTranslateY(280);
        iv.setTranslateX(20);
    }

    @FXML
    public void setSpeedLevel() {
        System.out.println(speedLevel.getValue());
        double scale = speedLevel.getValue();
        scale += 3;
        timer.stop();
        startTime(scale);
    }

    @FXML
    private void centerCursorOnDelay() {
        if(setTrafficLevel.getValue() < 0.5) {
            setTrafficLevel.setValue(0);
            howSlow = 1;
        }
        else if(setTrafficLevel.getValue() < 1.5) {
            setTrafficLevel.setValue(1);
            howSlow = 0.75;
        }
        else if(setTrafficLevel.getValue() < 2.5) {
            setTrafficLevel.setValue(2);
            howSlow = 0.5;
        }
        else {
            setTrafficLevel.setValue(3);
            howSlow = 0.25;
        }
    }

    @FXML
    private void setDelay() {
        if(setTrafficLevel.getValue() < 0.5) { howSlow = 1; }
        else if(setTrafficLevel.getValue() < 1.5) { howSlow = 0.75; }
        else if(setTrafficLevel.getValue() < 2.5) { howSlow = 0.5; }
        else { howSlow = 0.25; }

        System.out.println(howSlow);

        Boolean switcher = true;
        String delayStr = delayStreet.getText();

        for(TimerUpdate update : updates) {
            update.setDelayStreet2(delayStr, switcher, slowStreetsLabel, howSlow);
        }
    }

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

    public void setElements(List<Drawable> elements) {

        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
            if(drawable instanceof TimerUpdate) {
                updates.add((TimerUpdate) drawable);
            }
        }
    }

    public void startTime(double scale) {

        showTime.setTranslateX(10);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                i++;
                if (i >= 32767) {
                    i = 0;
                }
                if(i % scale < 1) {
                    showTime.setText(mapTime.toString());
                    mapTime.setSeconds(mapTime.getSeconds() + 1);
                }
                if (i % scale < 1) {
                    for (TimerUpdate update : updates) {
                        update.setKokot(showDepartures, showPathStops, traceOfStops, content);
                        update.setPane(showDepartures, showPathStops, traceOfStops, rightSide, content);
                        update.update(mapTime);
                    }
                }
            }
        };
        timer.start();
    }

    @FXML
    public void setSpecTime() throws ParseException {
        String fajr_prayertime = specificTime.getText();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date d1 = format.parse(fajr_prayertime);
        Date d2 = format.parse(mapTime.toString());
        Date d3 = format.parse("6:20:00");

        mapTime = new Time(d1.getHours(), d1.getMinutes(), d1.getSeconds());
        double timeDiffActual = d2.getTime() - d3.getTime();
        double iHourActual = timeDiffActual / 3600000;
        double allMinutesActual = iHourActual * 60;
        //System.out.println(allMinuesActual);


        double timeDiff = d1.getTime() - d2.getTime();
        double iHour = timeDiff / 3600000;
        double allMinutes = iHour * 60;
        //System.out.println(allMinutes);

        for (TimerUpdate update : updates) {
            update.setBaseTime(allMinutes, allMinutesActual);
        }
    }
}
