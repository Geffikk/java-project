package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import sample.source.imap.Drawable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.source.imap.TimerUpdate;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.source.map.Coordinate;

import java.sql.Time;
import javax.swing.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private Pane content;
    private List<Drawable> elements = new ArrayList<>();
    private LocalTime time = LocalTime.now();
    private List<TimerUpdate> updates = new ArrayList<>();
    private Time mapTime = new Time(06, 20, 0);
    private float scale;
    private String delayStr;

    //main timeline
    private Timeline timeline = new Timeline();
    private AnimationTimer timer;
    private Integer i=0;

    @FXML
    private TextField timeScale;
    @FXML
    private Label showTime;
    @FXML
    private Label showDepartures;
    @FXML
    private Label showPathStops;
    @FXML
    private Pane rightSide;
    @FXML
    private Line traceOfStops;
    @FXML
    private TextField delayStreet;

    @FXML
    private ImageView iv;
    @FXML
    private TextField specificTime;

    private static Integer switcherSong = 0;
    private MediaPlayer mediaPlayer;
    /*
    @FXML
    public void onReset() {
        odchody.setVisible(false);
    }*/

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
    private void plusOneHour() {
        mapTime.setHours(mapTime.getHours()+1);
        for (TimerUpdate update: updates) {
            update.movePlusHour();
        }

    }

    @FXML
    private void minusOneHour() {
        mapTime.setHours(mapTime.getHours()-1);
        for (TimerUpdate update: updates) {
            update.moveMinusHour();
        }

    }

    @FXML
    private void onTimeScaleChange(){
        try {
            scale = Float.parseFloat(timeScale.getText());
            if (scale <= 0 || scale > 10.0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalind time scale");
                alert.showAndWait();
            }
            scale = (scale + 2);
            timer.stop();
            startTime(scale);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalind time scale");
            alert.showAndWait();
        }
    }

    @FXML
    private void setDelay() {
        Boolean switcher = true;
        delayStr = delayStreet.getText();
        for(TimerUpdate update : updates) {
            update.setDelayStreet2(delayStr, switcher);
        }
    }

    @FXML
    private void onBaseTime() {
        mapTime = new Time(10, 10, 5);
        for (TimerUpdate update: updates) {
            update.restartPosition();
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
        this.elements = elements;

        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
            if(drawable instanceof TimerUpdate) {
                updates.add((TimerUpdate) drawable);
            }
        }
    }

    public void startTime(float scale) {

        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1000),
                new KeyValue (showTime.translateXProperty(), 5)));
        timeline.play();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                i++;
                if(i%scale == 0) {
                    showTime.setText(mapTime.toString());
                    mapTime.setSeconds(mapTime.getSeconds() + 1);
                }
                if (i % (scale + 3) == 0) {
                    for (TimerUpdate update : updates) {
                        update.setKokot(showDepartures, showPathStops, traceOfStops, content);
                        update.setPane(showDepartures, showPathStops, traceOfStops, rightSide, content);
                        update.update(mapTime);
                    }
                }
            }
        };
        timeline.play();
        timer.start();

    }

    @FXML
    public void setSpecTime() throws ParseException {
        String fajr_prayertime = specificTime.getText();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        java.sql.Time timeValue = new java.sql.Time(formatter.parse(fajr_prayertime).getTime());
        System.out.println(timeValue);
    }

    @FXML
    public void playSong() {

        if (switcherSong == 0) {
            Media musicfile = new Media("file:///C:/Users/Geffik/Desktop/java-project/src/sample/song.mp3");
            mediaPlayer = new MediaPlayer(musicfile);
            mediaPlayer.setAutoPlay(true);
            switcherSong = 2;
        } else if (switcherSong == 1) {
            mediaPlayer.play();
            switcherSong = 2;
        } else if (switcherSong == 2) {
            mediaPlayer.pause();
            switcherSong = 1;
        }

        if (mediaPlayer.getCurrentTime().equals(mediaPlayer.getTotalDuration()))
            switcherSong = 0;
    }
}
