package sample.source.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Autobus implements Drawable, TimerUpdate {
    private Coordinate position;
    private double speed = 0;
    private double distance = 0;
    private Path path;
    private List<Shape> gui;

    public Autobus(Coordinate position, double speed, Path path) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 8, Color.BLUE));
    }

    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX((coordinate.getX() - position.getX()) + shape.getTranslateX());
            shape.setTranslateX((coordinate.getY() - position.getY()) + shape.getTranslateY());
        }
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    @Override
    public void update(LocalTime time) {
        distance += speed;
        Coordinate coords = path.getCoordinateByDistance(distance);
        moveGui(coords);

    }
}
