/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate2;
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
import java.util.*;

import static java.lang.Math.abs;

/**
 * Class represents Autobus
 */
@JsonDeserialize(converter = Autobus.AutobusConstruct.class)
public class Autobus implements Drawable, TimerUpdate, iAutobus {

    // Vehicle <position> <speed> <distance> <path>
    private Coordinate position; /* position of vehicle */
    private double speed = 0; /* speed of vehicle */
    private Path path; /* path of vehicle */
    private String idOfLine; /* identification of line, (which vehicle, which line) */

    public double getDistance() {
        return distance;
    }

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
    @JsonIgnore
    private static int oneColor = 0;
    @JsonIgnore
    static public Boolean turnOnDelay;
    @JsonIgnore
    private double startDistance;
    @JsonIgnore
    public boolean direction;
    @JsonIgnore
    private Coordinate lastCoord;
    @JsonIgnore
    static int removeFromObchadzka = 0;

    // TimerUpdate Interface
    @Override
    public void update(Time mapTime) {

        /*
        if(oneColor) {
            if(gui.get(0).getId().equals("2")) {
                System.out.println(distance);
            }
            oneColor = false;
        }*/

        if(gui.get(0).getId().equals("4")) {
            //System.out.println(distance);
        }

        Time pauseOnSleep = new Time(23,59,59);
        if(mapTime.before(pauseOnSleep) && mapTime.after(new Time(6,19,59))) {

            distance += speed/(3.0/2.0);
            gui.get(0).setVisible(true);
            if (this.autobusIsOnStreet != null) {
                for (String street : slowStreets) {
                    if (this.autobusIsOnStreet.getId().equals(street)) {
                        speed = autobusIsOnStreet.delay;
                    } else {
                        speed = 1;
                    }
                }
            }

            // reverse path of line
            if (path.getPathSize() <= distance) {
                Collections.reverse(path.getPath());
                if(direction) {
                    setDirection(false);
                }
                else {
                    setDirection(true);
                }
                distance = 0;
            }

            //calculate new coordinates
            Coordinate coords = path.getCoordinateByDistance(distance, this);

            //move vehicle
            moveGui(coords);
            //set new position of vehicle
            position = coords;
        }
        else {
            distance = startDistance;
            gui.get(0).setVisible(false);
        }
    }


    @Override
    public void onClickVehicle(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content, Label actPositionText) {
        for (Shape shape : gui) {
            shape.setOnMouseClicked(mouseEvent -> setDepartures(actualPosition, label, label2, traceOfStops, content, actPositionText));
        }
    }

    @Override
    public void onClickPane(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane pane, Pane content, Label actPositionText) {
        pane.setOnMouseClicked(mouseEvent -> clearDepartures(actualPosition, label, label2, traceOfStops, pane, content, actPositionText));
    }

    @Override
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

    @Override
    public void setBaseTime(double travelInTime) {
        double distanceAfterTravelInTime = (travelInTime / 60) * 799.9999999999894;

        double i;
        if (distanceAfterTravelInTime > 0) {
            i = distanceAfterTravelInTime;
            while (i > path.getPathSize()) {
                i = i - path.getPathSize();
                Collections.reverse(path.getPath());
                if(direction) {
                    setDirection(false);
                }
                else {
                    setDirection(true);
                }
            }

            distance = distance + i;
            if (distance > path.getPathSize()) {
                distance = distance - path.getPathSize();
                Collections.reverse(path.getPath());
                if(direction) {
                    setDirection(false);
                }
                else {
                    setDirection(true);
                }
            }
        }
        else if (distanceAfterTravelInTime < 0) {
            i = abs(distanceAfterTravelInTime);
            while (i > path.getPathSize()) {
                i = i - path.getPathSize();
                Collections.reverse(path.getPath());
                if(direction) {
                    setDirection(false);
                }
                else {
                    setDirection(true);
                }
            }
            distance = distance - i;
            if (distance < 0) {
                distance = distance + path.getPathSize();
                Collections.reverse(path.getPath());
                if(direction) {
                    setDirection(false);
                }
                else {
                    setDirection(true);
                }
            }
        }
    }

    @Override
    public void testNewRoute(Street closedStreet) {
        int i = 0;
        int j = 0;

        for(Street street: line.getStreetList()) {
            //System.out.println(street);
            if(street.getArrayOfLines().get(0)) {

                //System.out.println(path.getPath());
                /*
                if (direction && distance > path.getPartPathSize(closedStreet)) {
                    distance = distance + closedStreet.getObchadzka().get(0).getPathSize();
                    System.out.println("---------------------------------");
                    System.out.println("Get path to closed street");
                    System.out.println(path.getPartPathSize(closedStreet));
                    System.out.println("---------------------------------");
                    System.out.println("Cela Path");
                    System.out.println(path.getPathSize());
                    System.out.println(path.getPath());
                } else if (!direction && distance > path.getPartPathSize(closedStreet)) {
                    //distance = distance + closedStreet.getObchadzka().get(0).getPathSize();
                    System.out.println("---------------------------------");
                    System.out.println("Get path to closed street");
                    System.out.println(path.getPartPathSize(closedStreet));
                    System.out.println("---------------------------------");
                    System.out.println("Cela Path");
                    System.out.println(path.getPathSize());
                    System.out.println(path.getPath());
                }*/
            }

            /*
            if(oneColor) {
                System.out.println(street.getObchadzka());
                System.out.println(street.getObchadzka().get(0));
                System.out.println(closedStreet.getCoordinates().get(0));
                oneColor = false;
            }*/

            i++;
            if(street.equals(closedStreet)) {
                // Street11 right coordinate
                if(equalCoordinate(closedStreet.getCoordinates().get(0), street.getObchadzka().get(0))) {
                    Coordinate leftSide = closedStreet.getCoordinates().get(1);
                    System.out.println("1 podmienka");
                    int iterator2 = 1;
                    while(j < street.getObchadzka().size()){
                        //System.out.println(street.getObchadzka());
                        try {
                            equalCoordinate(lastCoord, street.getObchadzka().get(iterator2));
                        }
                        catch (IndexOutOfBoundsException ignored){}
                        iterator2++;
                        j++;
                        System.out.println(this.path.getPath());
                    }
                    if(!direction) {
                        ///System.out.println(this.path.getPath());
                    }
                    removeFromObchadzka = 0;
                }
                // Street11 left coordinate
                else if(equalCoordinate(closedStreet.getCoordinates().get(1), street.getObchadzka().get(0))) {
                    Coordinate leftSide = closedStreet.getCoordinates().get(1);
                    //System.out.println("2 podmienka");
                    int iterator2 = 1;
                    while(j < street.getObchadzka().size()){
                        //System.out.println(street.getObchadzka());
                        try {
                            equalCoordinate(lastCoord, street.getObchadzka().get(iterator2));
                        }
                        catch (IndexOutOfBoundsException ignored){}
                        iterator2++;
                        j++;
                    }
                    if(direction) {
                       //System.out.println(this.path.getPath());
                    }
                    removeFromObchadzka = 0;
                }
            }
        }
    }

    public boolean equalCoordinate(Coordinate mainCoor, Street street2){
        int j = 0;
        int iter = 0;

        /*
        System.out.println(street2.getObchadzka());
        System.out.println(street2);
        System.out.println(street2.getCoordinates());
        System.out.println(mainCoor);
        System.out.println("----------------------------");
        */

        for(Coordinate coor : path.getPath()) {
            j++;
            if(mainCoor.equals(coor)) {
                break;
            }
        }

        if(mainCoor.equals(street2.getCoordinates().get(0))) {

            if(!direction) {
                while (iter < street2.getCoordinates().size()-1) {
                    iter++;
                    this.path.getPath().add(j, street2.getCoordinates().get(street2.getCoordinates().size()-iter));
                    //System.out.println(this.path.getPath());
                }
                //System.out.println(this.path.getPath());
            }
            else {
                int iter2 = street2.getCoordinates().size();
                while (iter < street2.getCoordinates().size()-1) {
                    //System.out.println(this.path.getPath());
                    iter++;
                    iter2--;
                    this.path.getPath().add(j-1, street2.getCoordinates().get(street2.getCoordinates().size()-iter2));
                }
            }
            //System.out.println(this.path.getPath());
            lastCoord = street2.getCoordinates().get(0);
            return true;
        }
        else if(mainCoor.equals(street2.getCoordinates().get(street2.getCoordinates().size()-1))) {

            if(!direction) {
                while (iter < street2.getCoordinates().size()-1) {
                    iter++;
                    this.path.getPath().add(j, street2.getCoordinates().get(iter-1));
                    //System.out.println(this.path.getPath());
                }
                //System.out.println(this.path.getPath());
                //System.out.println(this.path.getPath());
            }
            else {
                int iter2 = street2.getCoordinates().size();
                while (iter < street2.getCoordinates().size()-1) {
                    //System.out.println(this.path.getPath());
                    iter++;
                    iter2--;
                    this.path.getPath().add(j-1, street2.getCoordinates().get(iter2-1));
                }
            }
            //System.out.println(this.path.getPath());
            lastCoord = street2.getCoordinates().get(street2.getCoordinates().size()-iter-1);
            return true;
        }
        return false;
    }

    //iAutobus Interface
    @Override
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

    @Override
    public void setDepartures(Label actualPosition, Label label, Label label2, javafx.scene.shape.Line traceOfStops, Pane content, Label actPositionText){
        StringBuilder departures = new StringBuilder();
        StringBuilder stops = new StringBuilder();
        double x = 0;
        boolean jumpAway = false;
        int numberOfDepartures = 0;
        for (int j = 0; j < 27; j++) {
            numberOfDepartures++;
            for (int i = 0; i < line.getListOfDepartures1().size(); i++) {
                try {
                    if(direction) {
                        if (gui.get(0).getId().equals("2")) {
                            departures.append(line.getListOfDepartures1().get(i).get(j)).append("\t\t");
                        } else if (gui.get(0).getId().equals("1")) {
                            departures.append(line.getListOfDepartures1().get(i).get(j)).append("\t\t");
                        } else if (gui.get(0).getId().equals("3")) {
                            departures.append(line.getListOfDepartures1().get(i).get(j)).append("\t\t");
                        }
                    }
                    else {
                        if (gui.get(0).getId().equals("2")) {
                            departures.append(line.getListOfDepartures2().get(i).get(j)).append("\t\t");
                        } else if (gui.get(0).getId().equals("1")) {
                            departures.append(line.getListOfDepartures2().get(i).get(j)).append("\t\t");
                        } else if (gui.get(0).getId().equals("3")) {
                            departures.append(line.getListOfDepartures2().get(i).get(j)).append("\t\t");
                        }
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    jumpAway = true;
                    break;
                }
            }
            if (jumpAway) {
                numberOfDepartures--;
                break;
            }
            departures.append("\n");
        }
        try {
            if(direction) {
                for (int i = 0; i < line.getStopList().size(); i++) {
                    stops.append(line.getStopList().get(i).getId()).append("\t\t\t").append("     ");
                    x = x + 80;
                }
            }
            else {
                for (int i = line.getStopList().size()-1; i >= 0; i--) {
                    stops.append(line.getStopList().get(i).getId()).append("\t\t\t").append("     ");
                    x = x + 80;
                }
            }
        }
        catch (IndexOutOfBoundsException e) {
        }

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

        if(numberOfDepartures == 19) {
            label.setPrefHeight(580);
        }
        else {
            label.setPrefHeight(700);
        }

        traceOfStops.setVisible(true);
        traceOfStops.setStroke(Color.RED);
        traceOfStops.setEndX(x);
    }

    // Drawable Interface
    @Override
    @JsonIgnore
    public List<Shape> getGUI() {
        return gui;
    }

    // Private Functions
    /**
     * Empty constructor for yaml
     */
    private Autobus() {
    }

    /**
     * Move with pictures in map
     * @param coordinate New coordinates of vehicle
     */
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - position.getY() + shape.getTranslateY());
        }
    }

    /**
     * Set gui (vehicles) to map
     */
    private void setGui(){

        switch (this.idOfLine) {
            case "1": {
                Circle circle = new Circle(position.getX(), position.getY(), 8, Color.RED);
                circle.setStroke(Color.BLACK);
                circle.setId("1");
                oneColor++;
                if(oneColor > 8) {
                    circle = new Circle(position.getX(), position.getY(), 10, Color.PURPLE);
                    circle.setId("4");
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
     * Get position of autobus (getter for yaml)
     * @return -> position */
    @Override
    public Coordinate getPosition() {
        return position;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public String getIdOfLine() {
        return idOfLine;
    }

    @Override
    public Line getLine() {
        return line;
    }

    // Setters

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setStartDistance(double startDistance) {
        this.startDistance = startDistance;
    }

    public void set_line(Line line){
        this.line = line;
    }

    public void setAutobusIsOnStreet(Street street) {
        this.autobusIsOnStreet = street;
    }

    public void setDistance(double startDistance) {
        distance = startDistance;
    }


    // Others
    @Override
    public String toString() {
        return "Autobus{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }

    /**
     * Converter for setting gui after read from yaml
     * */
    static class AutobusConstruct extends StdConverter<Autobus, Autobus> {
        @Override
        public Autobus convert(Autobus autobus) {
            autobus.setGui();
            return autobus;
        }
    }
}
