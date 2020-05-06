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


    /*
    @FXML
    public void onReset() {
        odchody.setVisible(false);
    }*/

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
        delayStr = delayStreet.getText();
        for(TimerUpdate update : updates) {
            update.setDelayStreet2(delayStr);
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
}
