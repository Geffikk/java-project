package sample;

import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;

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
            if(drawable instanceof  TimerUpdate) {
                updates.add((TimerUpdate) drawable);
            }
        }
    }

    public void startTime() {
        timer = new Timer(false);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                time = time.plusSeconds(1);
                for (TimerUpdate update : updates) {
                    update.update(time);
                }
            }
        }, 0, 50);
    }
}
