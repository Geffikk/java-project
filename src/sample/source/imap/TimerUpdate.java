package sample.source.imap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.Timeline;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.sql.Time;
import java.util.List;


public interface TimerUpdate {
    void update(Time mapTime);
    void setKokot(Label label, Label label2, Line traceOfStops, Pane content);
    void setPane(Label label, Label label2, Line traceOfStops, Pane pane, Pane content);
    void restartPosition();
    void movePlusHour();
    void moveMinusHour();
}
