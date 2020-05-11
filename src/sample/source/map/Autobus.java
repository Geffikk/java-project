package sample.source.map;

import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;
import sample.source.imap.iAutobus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static java.lang.Math.abs;


@JsonDeserialize(converter = Autobus.AutobusConstruct.class)
public class Autobus implements Drawable, TimerUpdate, iAutobus {

    // Vehicle <position> <speed> <distance> <path>
    private Coordinate position; /* position of vehicle */
    private double speed = 0; /* speed of vehicle */
    private Path path; /* path of vehicle */
    private String idOfLine; /* identification of line, (which vehicle, which line) */
    @JsonIgnore
    private double distance = 0; /* driven distance */
    @JsonIgnore
    private List<Shape> gui = new ArrayList<>(); /* list of vehicles (gui) */
    @JsonIgnore
    private Shape strongLineRed; /* stronger line (after click on gui) */
    @JsonIgnore
    private Line line; /* define line of vehicle */
    @JsonIgnore
    private Street autobusIsOnStreet = null; /* on which street is current vehicle */
    @JsonIgnore
    private List<String> slowStreets = new ArrayList<>(); /* list of load streets */

    static boolean oneColor = true;
    static public Boolean turnOnDelay;

    /** Empty constructor for yaml **/
    private Autobus() {
    }

    /** Normal constructor **/
    public Autobus(Coordinate position, double speed, Path path, String idOfLine) {
        this.position = position;
        this.path = path;
        this.speed = speed;
        this.idOfLine = idOfLine;
        setGui();
    }

    @JsonIgnore
    public void setDistance(double startDistance) {
        distance = startDistance;
    }

    @JsonIgnore
    @Override
    public void onClickVehicle(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content) {
        for (Shape shape : gui) {
            shape.setOnMouseClicked(mouseEvent -> setDepartures(label, label2, traceOfStops, content));
        }
    }

    @JsonIgnore
    @Override
    public void onClickPane(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content) {
        pane.setOnMouseClicked(mouseEvent -> clearDepartures(label, label2, traceOfStops, pane, content));
    }

    @JsonIgnore
    public void clearDepartures(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content) {
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
                strongLineRed = (Shape) content.getChildren().get(i);
                strongLineRed.setStrokeWidth(1);
                content.getChildren().set(i, strongLineRed);
            }
            if (id.equals("2")) {
                strongLineRed = (Shape) content.getChildren().get(i);
                strongLineRed.setStrokeWidth(1);
                content.getChildren().set(i, strongLineRed);
            }
        }
    }

    @JsonIgnore
    public void setDepartures(Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content){
        StringBuilder departures = new StringBuilder();
        StringBuilder stops = new StringBuilder();
        double x = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < line.getListOfDepartures().size(); i++) {
                if (gui.get(0).getId().equals("2")) {
                    departures.append(line.getListOfDepartures().get(1).get(j)).append("\t\t");
                }
                else if(gui.get(0).getId().equals("1")) {
                    departures.append(line.getListOfDepartures().get(0).get(j)).append("\t\t");
                }
            }
            departures.append("\n");
        }

        for(int i = 0; i < line.getListOfDepartures().size(); i++) {
            stops.append(line.getStopList().get(i).getId()).append("\t\t\t").append("     ");
            x = x + 80;
        }

        System.out.println(gui.get(0).getId());

        for (int f = 0; f<content.getChildren().size(); f++){
            /* stronger line (after click on gui) */
            Shape strongLineGreen = (Shape) content.getChildren().get(f);
            if(strongLineGreen.getStrokeWidth() == 4) {
                strongLineGreen.setStrokeWidth(1);
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
                    strongLineRed = (Shape) content.getChildren().get(i);
                    strongLineRed.setStrokeWidth(4);
                    content.getChildren().set(i, strongLineRed);
                }
            }
            else if (gui.get(0).getId().equals("2")) {
                if (id.equals("2")) {
                    //System.out.println(content.getChildren().get(i));
                    strongLineRed = (Shape) content.getChildren().get(i);
                    strongLineRed.setStrokeWidth(4);
                    content.getChildren().set(i, strongLineRed);
                }
            }
        }

        label.setText(departures.toString());
        label2.setText(stops.toString());
        label2.setTranslateX(8);

        traceOfStops.setVisible(true);
        traceOfStops.setStroke(Color.RED);
        traceOfStops.setEndX(x);
    }

    /**
     * Move with pictures in map
     * @param coordinate -> new coordinates of vehicle */
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - position.getY() + shape.getTranslateY());
        }
    }

    /**
     * Set gui (vehicles) to map */
    private void setGui(){

        switch (this.idOfLine) {
            case "1": {
                Circle circle = new Circle(position.getX(), position.getY(), 8, Color.RED);
                circle.setId("1");
                if (oneColor) {
                    circle = new Circle(position.getX(), position.getY(), 10, Color.PINK);
                    circle.setId("3");
                    oneColor = false;
                }
                gui.add(circle);
                break;
            }
            case "2": {
                Circle circle = new Circle(position.getX(), position.getY(), 8, Color.GREEN);
                circle.setId("2");
                gui.add(circle);
                break;
            }
            case "3": {
                Circle circle = new Circle(position.getX(), position.getY(), 8, Color.BLUE);
                circle.setId("3");
                gui.add(circle);
                break;
            }
        }

    }

    /**
     * Return all GUI elements
     * @return -> vehicle
     */
    @JsonIgnore
    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public void setDelayStreet(String delayStr, Boolean switcher, Label slowStreetText, double howSlow) {
        turnOnDelay = switcher;
        slowStreets.add(delayStr);

        for(int i = 0; i < this.line.getStreetList().size(); i++) {
            if (this.line.getStreetList().get(i).getId().equals(delayStr)) {
                this.line.getStreetList().get(i).delay = howSlow/(3.0/2.0);
            }
        }
        /*
        for(String street: slowStreets) {
            //slowStreetText.setText(street);
            //slowStreetText.setText("\n");
        }*/
    }

    public void setBaseTime(double travelInTime, double travelInTimeActual) {
        double distanceAfterTravelInTime = (travelInTime / travelInTimeActual) * distance;

        if(gui.get(0).getId().equals("3")) {
            System.out.println("GUI RUZOVE");
            System.out.println(distanceAfterTravelInTime);
            System.out.println(distance);
        }

        if(gui.get(0).getId().equals("1")){
            System.out.println("ZVYSOK");
            System.out.println(distanceAfterTravelInTime);
            System.out.println(distance);
        }

        double i;
        if (distanceAfterTravelInTime > 0) {
            i = distanceAfterTravelInTime;
            while (i > path.getPathSize() * 2) {
                i = i - path.getPathSize() * 2;
            }

            distance = distance + i;
            if (distance > path.getPathSize()) {
                distance = distance - path.getPathSize();

                Collections.reverse(path.getPath());
            }
        }
        else if (distanceAfterTravelInTime < 0) {
            i = abs(distanceAfterTravelInTime);
            while (i > path.getPathSize()) {
                i = i - path.getPathSize();
            }
            distance = distance + distanceAfterTravelInTime;
            if (distance < 0) {
                distance = path.getPathSize() + distance;
            }
        }
    }

    @Override
    public void update(Time mapTime) {

        distance += speed/(3.0/2.0);

        if(this.autobusIsOnStreet != null) {
            for(String street : slowStreets) {
                if (this.autobusIsOnStreet.getId().equals(street)) {
                    speed = autobusIsOnStreet.delay;
                }
                else {
                    speed = 1;
                }
            }
        }

        // reverse path of line
        if (path.getPathSize() <= distance) {
            Collections.reverse(path.getPath());
            distance = 0;
        }

        //calculate new coordinates
        Coordinate coords = path.getCoordinateByDistance(distance,this);

        //move vehicle
        moveGui(coords);
        //set new position of vehicle
        position = coords;
    }

    /**
     * Get position of autobus (getter for yaml)
     * @return -> position */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Get speed of autobus (getter for yaml)
     * @return -> speed */
    public double getSpeed() {
        return speed;
    }

    public Path getPath() {
        return path;
    }

    /**
     * Get id of line of autobus (getter for yaml)
     * @return -> id of line */
    public String getIdOfLine() {
        return idOfLine;
    }

    public Line getLine() {
        return line;
    }

    public void set_line(Line line){
        this.line = line;
    }

    public void setAutobusIsOnStreet(Street street) {
        this.autobusIsOnStreet = street;
    }

    @Override
    public String toString() {
        return "Autobus{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }

    /** Converter for setting gui after read from yaml**/
    static class AutobusConstruct extends StdConverter<Autobus, Autobus> {
        @Override
        public Autobus convert(Autobus autobus) {
            autobus.setGui();
            return autobus;
        }
    }
}
