package sample;

import javafx.animation.Animation;
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

import javax.swing.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private Pane content;
    private List<Drawable> elements = new ArrayList<>();
    private Timer timer;
    private LocalTime time = LocalTime.now();
    private List<TimerUpdate> updates = new ArrayList<>();
    private Time mapTime = new Time(10, 10, 5);
    private float scale;

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


    /*
    @FXML
    public void onReset() {
        odchody.setVisible(false);
    }*/

    @FXML
    private void onTimeScaleChange(){
        try {
            scale = Float.parseFloat(timeScale.getText());
            if (scale <= 0 || scale > 10.0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalind time scale");
                alert.showAndWait();
            }
            scale = scale + 5;
            timer.cancel();
            startTime(scale);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalind time scale");
            alert.showAndWait();
        }
    }

    @FXML
    private void onBaseTime() {
        mapTime = new Time(10, 10, 5);
    }

    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
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

    @FXML
    public void initialize() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            showTime.setText(mapTime.toString());
            showTime.setTranslateX(850);
            //showTime.setTranslateY(5);
        }),
                new KeyFrame(Duration.millis(100))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void startTime(float scale) {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time = time.plusSeconds(1);
                for (TimerUpdate update : updates) {
                    update.setKokot(showDepartures, showPathStops, traceOfStops, content);
                    update.setPane(showDepartures, showPathStops, traceOfStops,rightSide, content);
                    mapTime.setSeconds(mapTime.getSeconds()+1);
                    update.update(mapTime);
                }
            }
        }, 0, (long) (500 / scale));
    }
}
