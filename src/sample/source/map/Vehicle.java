package sample.source.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements Drawable, TimerUpdate {

    // Position of vehicle <coordinate>
    private Coordinate position;
    // Speed of vehicle
    private double speed = 0;
    // Distance of way for vehicle
    private double distance = 0;
    // Path
    private Path path;
    // Paint to gui
    private List<Shape> gui;

    // Vehicle
    public Vehicle(Coordinate position, double speed, Path path) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        gui = new ArrayList<>();
        gui.add(new Rectangle(position.getX(), position.getY(), 15, 10));
    }

    /** Paint new rectangle to map "simulate move" **/
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX());
            shape.setTranslateY(coordinate.getY());
            System.out.println(String.format("GUI Coors X- %f, Y-%f", shape.getTranslateX(),shape.getTranslateY()));
        }
    }

    /** Return GUI **/
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
}
