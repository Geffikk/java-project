package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import org.omg.CORBA.Any;
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
<<<<<<< HEAD
    private List<Shape> gui = new ArrayList<>();
    private Line line;
    private String idOfLine;

    private Shape daco;


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
        }
    }

    public void setData(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content){
        String departures = "";
        String stops = "";
        String idcko;
        double x = 0;
        for (int j = 0; j < 4; j++) {

            for (int i = 0; i < line.getListOfDepartures().size(); i++) {
                departures = departures + "\t\t\t\t   " + line.getListOfDepartures().get(i).get(j);
            }
            departures = departures + "\n";
        }

        for(int i = 0; i < line.getListOfDepartures().size(); i++) {
            stops = stops + "\t\t\t" + line.getStopList().get(i).getId();
            x = x + 130;
        }

        for (int i = 0; i<content.getChildren().size(); i++){
            String id;
            id = content.getChildren().get(i).getId();
            if (id == null) {
                id = "";
            }
            //System.out.println(elements.get(i).getGUI().get(0));
            if ( id.equals("1"))
            {
                daco = (Shape) content.getChildren().get(i);
                daco.setStrokeWidth(10);
                content.getChildren().set(i, daco);
            }
        }

        label.setText(departures);
        label2.setText(stops);
        label2.setTranslateX(8);

        traceOfStops.setVisible(true);
        traceOfStops.setStroke(Color.RED);
        traceOfStops.setEndX(x);
    }
=======
    private List<Shape> gui;
    @JsonIgnore
    private Color color;
>>>>>>> master

    /** Empty constructor for yaml **/
    private Autobus() {
    }

    /** Normal constructor **/
    public Autobus(Coordinate position, double speed, Path path) {
        this.position = position;
        this.path = path;
        this.speed = speed;
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
        if(this.speed == 1) {
            gui.add(new Circle(position.getX(), position.getY(), 8, Color.RED));
        }
        else  {
            gui.add(new Circle(position.getX(), position.getY(), 8, Color.GREEN));
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

    public String getIdOfLine() {
        return idOfLine;
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
