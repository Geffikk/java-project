package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
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
    @JsonIgnore
    private double distance = 0;
    private Path path;
    @JsonIgnore
    private List<Shape> gui;
    private Color color;

    private Autobus() {
    }

    public Autobus(Coordinate position, double speed, Path path) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        setGui();
    }

    // Move with pictures in our map
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX((coordinate.getX()));
            shape.setTranslateY((coordinate.getY()));
        }
    }

    private void setKokot() {
        for (Shape shape : gui) {
            if(shape.getFill() == Color.GREEN) {
                shape.setOnMouseClicked(mouseEvent -> System.out.println("KOKOT"));
            }
        }
    }

    // Set first image to map
    private void setGui(){
        gui = new ArrayList<>();
        if(this.speed == 0.5) {
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
        int i = 1;
        if(i == 1){
            setKokot();
        }
        i = i+1;
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
