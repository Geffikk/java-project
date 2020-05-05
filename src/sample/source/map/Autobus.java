package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(converter = Autobus.AutobusConstruct.class)
public class Autobus implements Drawable, TimerUpdate {
    // Vehicle <position> <speed> <distance> <path>
    private Coordinate position;
    private double speed = 0;
    private Path path;
    private String idOfLine;
    @JsonIgnore
    private double distance = 0;
    @JsonIgnore
    private List<Shape> gui = new ArrayList<>();
    @JsonIgnore
    private Line line;
    @JsonIgnore
    private Shape daco;
    @JsonIgnore
    private Shape daco1;

    @JsonIgnore
    @Override
    public void setKokot(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content) {
        for (Shape shape : gui) {
            shape.setOnMouseClicked(mouseEvent -> setData(label, label2, traceOfStops, content));
        }
    }

    @JsonIgnore
    @Override
    public void setPane(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content) {
        pane.setOnMouseClicked(mouseEvent -> clearData(label, label2, traceOfStops, pane, content));
    }

    public void clearData(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content) {
        label.setText("");
        label2.setText("");
        traceOfStops.setVisible(false);
        for (int i = 0; i<content.getChildren().size(); i++){
            String id;
            id = content.getChildren().get(i).getId();
            if (id == null) {
                id = "";
            }

            if (id.equals("1")) {
                daco = (Shape) content.getChildren().get(i);
                daco.setStrokeWidth(1);
                content.getChildren().set(i, daco);
            }
            if (id.equals("2")) {
                daco = (Shape) content.getChildren().get(i);
                daco.setStrokeWidth(1);
                content.getChildren().set(i, daco);
            }
        }
    }

    public void setData(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content){
        String departures = "";
        String stops = "";
        double x = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < line.getListOfDepartures().size(); i++) {
                if (gui.get(0).getId().equals("2")) {
                    departures = departures + line.getListOfDepartures().get(1).get(j) + "\t\t\t\t" ;
                }
                else if(gui.get(0).getId().equals("1")) {
                    departures = departures + line.getListOfDepartures().get(0).get(j) + "\t\t\t\t";
                }
            }
            departures = departures + "\n";
        }

        for(int i = 0; i < line.getListOfDepartures().size(); i++) {
            stops = stops + line.getStopList().get(i).getId() + "\t\t\t\t\t\t" + "     ";
            x = x + 100;
        }

        System.out.println(gui.get(0).getId());

        for (int f = 0; f<content.getChildren().size(); f++){
            daco1 = (Shape) content.getChildren().get(f);
            if(daco1.getStrokeWidth() == 4) {
                daco1.setStrokeWidth(1);
            }
        }

        for (int i = 0; i<content.getChildren().size(); i++) {
            String id;
            id = content.getChildren().get(i).getId();

            if (id == null) {
                id = "";
            }
            if (gui.get(0).getId().equals("1")) {
                if (id.equals("1")) {
                    daco = (Shape) content.getChildren().get(i);
                    daco.setStrokeWidth(4);
                    content.getChildren().set(i, daco);
                }
            }
            else if (gui.get(0).getId().equals("2")) {
                if (id.equals("2")) {
                    System.out.println(content.getChildren().get(i));
                    daco = (Shape) content.getChildren().get(i);
                    daco.setStrokeWidth(4);
                    content.getChildren().set(i, daco);
                }
            }
        }

        label.setText(departures);
        label2.setText(stops);
        label2.setTranslateX(8);

        traceOfStops.setVisible(true);
        traceOfStops.setStroke(Color.RED);
        traceOfStops.setEndX(x);
    }


    /** Empty constructor for yaml **/
    private Autobus() {
    }

    /** Normal constructor **/
    public Autobus(Coordinate position, double speed, Path path, String idOfLine) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        this.idOfLine = idOfLine;
        this.line = null;
        setGui();
    }

    public String getIdOfLine() {
        return idOfLine;
    }

    public void set_line(Line line){
        this.line = line;
    }

    // Move with pictures in our map
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX((coordinate.getX()));
            shape.setTranslateY((coordinate.getY()));
        }
    }

    // Set first image to map
    private void setGui(){
        if(this.idOfLine.equals("1")) {
            Circle circle = new Circle(position.getX(), position.getY(), 8, Color.RED);
            circle.setId("1");
            gui.add(circle);
        }
        else if (this.idOfLine.equals("2")){
            Circle circle = new Circle(position.getX(), position.getY(), 8, Color.GREEN);
            circle.setId("2");
            gui.add(circle);
        }
    }

    // Return all GUI elements
    @JsonIgnore
    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    // Update images in map
    @Override
    public void update(Time mapTime) {
        distance += speed;
        if (path.getPathSize() <= distance) {
            distance = 0;
        }
        Coordinate coords = path.getCoordinateByDistance(distance);
        moveGui(coords);
    }


    // Get position of vehicle
    public Coordinate getPosition() {
        return position;
    }

    // Get speed of vehicle
    public double getSpeed() {
        return speed;
    }

    // Get path of vehicle
    public Path getPath() {
        return path;
    }

    // Overide functions toString for printing
    @Override
    public String toString() {
        return "Autobus{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }

    // I dont know
    static class AutobusConstruct extends StdConverter<Autobus, Autobus> {
        @Override
        public Autobus convert(Autobus autobus) {
            autobus.setGui();
            return autobus;
        }
    }

}
