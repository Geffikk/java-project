package sample.source.map;

import org.yaml.snakeyaml.util.ArrayUtils;
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
import java.util.Date;
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
    @JsonIgnore
    private String slowStreetsString = "";

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
    public void onClickVehicle(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content, Label actPositionText) {
        for (Shape shape : gui) {
            shape.setOnMouseClicked(mouseEvent -> setDepartures(actualPosition, label, label2, traceOfStops, content, actPositionText));
        }
    }

    @JsonIgnore
    @Override
    public void onClickPane(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content, Label actPositionText) {
        pane.setOnMouseClicked(mouseEvent -> clearDepartures(actualPosition, label, label2, traceOfStops, pane, content, actPositionText));
    }

    @JsonIgnore
    public void clearDepartures(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content, Label actPositionText) {
        label.setText("");
        label2.setText("");
        actPositionText.setVisible(false);
        actualPosition.setText("");

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
                strongLineRed.setStroke(Color.BLACK);
                content.getChildren().set(i, strongLineRed);
            }
            else if (id.equals("2")) {
                strongLineRed = (Shape) content.getChildren().get(i);
                strongLineRed.setStrokeWidth(1);
                strongLineRed.setStroke(Color.BLACK);
                content.getChildren().set(i, strongLineRed);
            }
            else if (id.equals("3")) {
                strongLineRed = (Shape) content.getChildren().get(i);
                strongLineRed.setStrokeWidth(1);
                strongLineRed.setStroke(Color.BLACK);
                content.getChildren().set(i, strongLineRed);
            }
        }
    }

    @JsonIgnore
    public void setDepartures(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content, Label actPositionText){
        StringBuilder departures = new StringBuilder();
        StringBuilder stops = new StringBuilder();
        double x = 0;
        for (int j = 0; j < 27; j++) {
            for (int i = 0; i < line.getListOfDepartures().size(); i++) {
                try {
                    if (gui.get(0).getId().equals("2")) {
                        departures.append(line.getListOfDepartures().get(i).get(j)).append("\t\t");
                    } else if (gui.get(0).getId().equals("1")) {
                        departures.append(line.getListOfDepartures().get(i).get(j)).append("\t\t");
                    } else if (gui.get(0).getId().equals("3")) {
                        departures.append(line.getListOfDepartures().get(i).get(j)).append("\t\t");
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
            departures.append("\n");
        }

        System.out.println(line.getListOfDepartures().size());
        for (int i = 0; i < line.getListOfDepartures().size(); i++) {
            stops.append(line.getStopList().get(i).getId()).append("\t\t\t").append("     ");
            x = x + 80;
        }


        System.out.println(gui.get(0).getId());

        for (int f = 0; f<content.getChildren().size(); f++){
            /* stronger line (after click on gui) */
            Shape strongLineGreen = (Shape) content.getChildren().get(f);
            if(strongLineGreen.getStrokeWidth() == 4) {
                strongLineGreen.setStrokeWidth(1);
                strongLineGreen.setStroke(Color.BLACK);
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
                    if (strongLineRed.getClass().toString().equals("class javafx.scene.shape.Line")) {
                        strongLineRed.setStrokeWidth(4);
                        strongLineRed.setStroke(Color.RED);
                        content.getChildren().set(i, strongLineRed);
                    }
                }
            }
            else if (gui.get(0).getId().equals("2")) {
                if (id.equals("2")) {
                    //System.out.println(content.getChildren().get(i));
                    strongLineRed = (Shape) content.getChildren().get(i);
                    if (strongLineRed.getClass().toString().equals("class javafx.scene.shape.Line")) {
                        strongLineRed.setStrokeWidth(4);
                        strongLineRed.setStroke(Color.GREEN);
                        content.getChildren().set(i, strongLineRed);
                    }
                }
            }
            else if (gui.get(0).getId().equals("3")) {
                if (id.equals("3")) {
                    strongLineRed = (Shape) content.getChildren().get(i);
                    if (strongLineRed.getClass().toString().equals("class javafx.scene.shape.Line")) {
                        strongLineRed.setStrokeWidth(4);
                        strongLineRed.setStroke(Color.BLUE);
                        content.getChildren().set(i, strongLineRed);
                    }
                }
            }
        }

        actPositionText.setVisible(true);
        actualPosition.setText(autobusIsOnStreet.getId().toUpperCase());

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
                circle.setStroke(Color.BLACK);
                circle.setId("1");
                if(oneColor) {
                    circle = new Circle(position.getX(), position.getY(), 8, Color.RED);
                    circle.setId("4");
                    oneColor = false;
                }
                gui.add(circle);
                break;
            }
            case "2": {
                Circle circle = new Circle(position.getX(), position.getY(), 8, Color.GREEN);
                circle.setStroke(Color.BLACK);
                circle.setId("2");
                gui.add(circle);
                break;
            }
            case "3": {
                Circle circle = new Circle(position.getX(), position.getY(), 8, Color.BLUE);
                circle.setStroke(Color.BLACK);
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

    public void setDelayStreet(String delayStr, Boolean switcher, double howSlow) {
        turnOnDelay = switcher;
        boolean isThere = false;

        for(String street: slowStreets) {
            if (street.equals(delayStr)) {
                isThere = true;
                break;
            }
        }

        if(!isThere) {
            slowStreets.add(delayStr);
        }

        for(int i = 0; i < this.line.getStreetList().size(); i++) {
            if (this.line.getStreetList().get(i).getId().equals(delayStr)) {
                this.line.getStreetList().get(i).delay = howSlow/(3.0/2.0);
            }
        }
    }

    public void setBaseTime(double travelInTime) {
        double distanceAfterTravelInTime = (travelInTime / 60) * 799.9999999999894;

        double i;
        if (distanceAfterTravelInTime > 0) {
            i = distanceAfterTravelInTime;
            while (i > path.getPathSize()) {
                i = i - path.getPathSize();
                Collections.reverse(path.getPath());
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
                Collections.reverse(path.getPath());
            }
            distance = distance - i;
            if (distance < 0) {
                distance = distance + path.getPathSize();
                Collections.reverse(path.getPath());
            }
        }
        if(gui.get(0).getId().equals("4")){
            System.out.printf("MODRE AUTO dlzka celej cesty -> (%s) \n", path.getPathSize()*2);
            System.out.printf("Celkova potrebna dlzka na prejdenie -> (%s) \n", distanceAfterTravelInTime);
            System.out.printf("Aktualna vzdialenost vozidla -> (%s)\n",distance);
            System.out.println("---------------------------------------------------------");
        }
    }

    @Override
    public void update(Time mapTime) {

        distance += speed/(3.0/2.0);

        if (gui.get(0).getId().equals("4")) {
            //System.out.println(distance);
            if(mapTime.toString().equals("07:20:00")) {
                System.out.println(distance);
            }
        }

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
