package sample.source.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Autobus implements Drawable {
    private Coordinate position;
    private double speed;
    private List<Shape> gui;

    public Autobus(Coordinate position, double speed) {
        this.position = position;
        this.speed = speed;
        gui = new ArrayList<>();
        gui.add(new Circle(position.getX(), position.getY(), 8, Color.BLUE));
    }

    @Override
    public List<Shape> getGUI() {
        return gui;
    }

}
