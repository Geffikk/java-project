package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD:src/sample/source/map/Autobus.java
@JsonDeserialize(converter = Autobus.AutobusConstruct.class)
public class Autobus implements Drawable, TimerUpdate {
=======
public class Vehicle implements Drawable, TimerUpdate {

    // Position of vehicle <coordinate>
>>>>>>> master:src/sample/source/map/Vehicle.java
    private Coordinate position;
    // Speed of vehicle
    private double speed = 0;
<<<<<<< HEAD:src/sample/source/map/Autobus.java
    @JsonIgnore
=======
    // Distance of way for vehicle
>>>>>>> master:src/sample/source/map/Vehicle.java
    private double distance = 0;
    // Path
    private Path path;
<<<<<<< HEAD:src/sample/source/map/Autobus.java
    @JsonIgnore
    private List<Shape> gui;

    private Autobus() {
    }

    public Autobus(Coordinate position, double speed, Path path) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        setGui();
=======
    // Paint to gui
    private List<Shape> gui;

    // Vehicle
    public Vehicle(Coordinate position, double speed, Path path) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        gui = new ArrayList<>();
        gui.add(new Rectangle(position.getX(), position.getY(), 15, 10));
>>>>>>> master:src/sample/source/map/Vehicle.java
    }

    /** Paint new rectangle to map "simulate move" **/
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX());
            shape.setTranslateY(coordinate.getY());
            System.out.println(String.format("GUI Coors X- %f, Y-%f", shape.getTranslateX(),shape.getTranslateY()));
        }
    }

<<<<<<< HEAD:src/sample/source/map/Autobus.java
    private void setGui(){
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 8, Color.BLUE));
    }

    @JsonIgnore
=======
    /** Return GUI **/
>>>>>>> master:src/sample/source/map/Vehicle.java
    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    /** Refresh gui and move vehcile to direction **/
    @Override
    public void update(LocalTime time) {
        distance += speed;
        System.out.println(String.format("Path size: %f, Distance: %f", path.getPathSize(), distance));
        if (distance > path.getPathSize())
        {
            return;
        }
        Coordinate coords = path.getCoordinateByDistance(distance);
        System.out.println(String.format("New coordinates -> X = %f, Y = %f ", coords.getX(), coords.getY()));
        moveGui(coords);
    }

    public Coordinate getPosition() {
        return position;
    }

    public double getSpeed() {
        return speed;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Autobus{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }

    static class AutobusConstruct extends StdConverter<Autobus, Autobus> {
        @Override
        public Autobus convert(Autobus autobus) {
            autobus.setGui();
            return autobus;
        }
    }

}
