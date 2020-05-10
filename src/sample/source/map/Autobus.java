package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Math.abs;


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
    private Shape daco;
    @JsonIgnore
    private Shape daco1;
    @JsonIgnore
    private Line line;
    @JsonIgnore
    private Street autobusIsOnStreet = null;
<<<<<<< HEAD
    @JsonIgnore
    private List<String> slowStreets = new ArrayList<>();
    @JsonIgnore
    private double distanceAfterTravelInTime = 0;

=======
>>>>>>> master
    @JsonIgnore
    Boolean first_position = true;
    @JsonIgnore
    Boolean click_position = false;
    @JsonIgnore
    Coordinate iPosition;


    private double percentage;
    static boolean onecolor = true;
    private boolean over50 = false;

    static public Boolean turnOnDelay;
    private String str;

    @JsonIgnore
    public void setDistance(double startDistance) {
        distance = startDistance;
    }

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
                    departures = departures + line.getListOfDepartures().get(1).get(j) + "\t\t" ;
                }
                else if(gui.get(0).getId().equals("1")) {
                    departures = departures + line.getListOfDepartures().get(0).get(j) + "\t\t";
                }
            }
            departures = departures + "\n";
        }

        for(int i = 0; i < line.getListOfDepartures().size(); i++) {
            stops = stops + line.getStopList().get(i).getId() + "\t\t\t" + "     ";
            x = x + 80;
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
                    //System.out.println(content.getChildren().get(i));
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
        setGui();
    }

    // Move with pictures in our map
    private void moveGui(Coordinate coordinate) {
        for (Shape shape : gui) {
            shape.setTranslateX(coordinate.getX() - position.getX() + shape.getTranslateX());
            shape.setTranslateY(coordinate.getY() - position.getY() + shape.getTranslateY());
        }
    }

    // Set first image to map
    private void setGui(){

        if(this.idOfLine.equals("1")) {
            Circle circle = new Circle(position.getX(), position.getY(), 8, Color.RED);
            circle.setId("1");
            if (onecolor) {
                circle = new Circle(position.getX(), position.getY(), 10, Color.PINK);
                circle.setId("3");
                onecolor = false;
            }
            gui.add(circle);
        }
        else if (this.idOfLine.equals("2")){
            Circle circle = new Circle(position.getX(), position.getY(), 8, Color.GREEN);
            circle.setId("2");
            gui.add(circle);
        }
        else if (this.idOfLine.equals("3")){
            Circle circle = new Circle(position.getX(), position.getY(), 8, Color.BLUE);
            circle.setId("3");
            gui.add(circle);
        }

<<<<<<< HEAD
        //Circle circle = new Circle(position.getX(), position.getY(), 8, Color.RED);
        //gui.add(circle);
        this.start = true;
        this.startOfAutobus = position;
=======
>>>>>>> master
    }

    // Return all GUI elements
    @JsonIgnore
    @Override
    public List<Shape> getGUI() {
        return gui;
    }

    public void restartPosition() {
        click_position = true;
    }

    public void movePlusHour() {
        distance += 150;
    }

    public void moveMinusHour() {
        if(distance >= 150) {
            distance -= 150;
        }
        else {
            distance = 0;
        }
    }

    public void setDelayStreet2(String delayStr, Boolean switcher, Label slowStreetText, double howSlow) {
        turnOnDelay = switcher;
        str = delayStr;
        slowStreets.add(delayStr);

        for(int i = 0; i < this.line.getStreetList().size(); i++) {
            if (this.line.getStreetList().get(i).getId().equals(str)) {
                this.line.getStreetList().get(i).delay = howSlow/(3.0/2.0);
            }
        }
        //System.out.println(slowStreets.size());
        //System.out.println(slowStreets);
        for(String street: slowStreets) {
            //slowStreetText.setText(street);
            //slowStreetText.setText("\n");
        }
    }

    public void setBaseTime(double travelInTime, double travelInTimeActual) {
        distanceAfterTravelInTime = (travelInTime / travelInTimeActual) * distance;
        int switcherReverse = 0;
        if(gui.get(0).getId().equals("3")) {
            System.out.println("GUI RUZOVE");
            System.out.println(distanceAfterTravelInTime);
            System.out.println(distance);
        }

<<<<<<< HEAD
        if(gui.get(0).getId().equals("1")){
            System.out.println("ZVYSOK");
            System.out.println(distanceAfterTravelInTime);
            System.out.println(distance);
        }

        if (gui.get(0).getId().equals("3")) {
            //System.out.println(distance);
            //System.out.println(path.getPath());
        }

        double i = 0;
        if (distanceAfterTravelInTime > 0) {
            i = distanceAfterTravelInTime;
            while (i > path.getPathSize()*2) {
                i = i - path.getPathSize()*2;
                //System.out.println("-----------------");
                //System.out.println(i);
                switcherReverse++;
            }

            distance = distance + i;
            if (distance > path.getPathSize()) {
                distance = distance - path.getPathSize();

                Collections.reverse(path.getPath());
                if (gui.get(0).getId().equals("3")) {
                    //System.out.println(distance);
                    //System.out.println(path.getPath());
                }
                over50 = true;
=======

        distance += speed;
        /*
        if ("Street1".equals(line.getStreetList().get(0).getId())) {
            System.out.println(line.getStreetList().get(0).getId());
            System.out.println(line.getStreetList().get(0).delay);
            distance = distance - speed + line.getStreetList().get(0).delay;
        }*/
        percentage = distance / path.getPathSize();

        //if(autobusIsOnStreet != null)
            //System.out.println(autobusIsOnStreet.getId());
        /*
        for (int i = 0; i < line.getStreetList().size(); i++) {
            if (line.getStreetList().get(i).delay != 0.0 && turnOnDelay) {
                try {
                    if (autobusIsOnStreet.getId().equals(str)) {
                        speed = line.getStreetList().get(i).delay;
                        //System.out.println(line.getStreetList().get(i).getId());
                        //System.out.println(line.getStreetList().get(i).delay);
                        turnOnDelay = false;
                    }
                    else {
                        speed = 1;
                    }
                }-
                catch (NullPointerException e){}
>>>>>>> master
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
<<<<<<< HEAD
    }

    // Update images in map
    @Override
    public void update(Time mapTime) {

        distance += speed/(3.0/2.0);
        //System.out.println(distance);
        percentage = distance / path.getPathSize();

        if (gui.get(0).getId().equals("3")) {
            //System.out.println(distance);
            //System.out.println(path.getPath());
        }
=======
        */
>>>>>>> master

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
<<<<<<< HEAD
            /*
            List<Coordinate> reverseList1 = new ArrayList<>();
            for (int i = path.getPath().size() - 1; i > 0; i--) {
=======
            List<Coordinate> reverseList = new ArrayList<>();
            for (int i = path.getPath().size() - 1; i >= 0 ; i--){
>>>>>>> master
                Coordinate c1 = path.getPath().get(i);
                reverseList.add(c1);
            }
<<<<<<< HEAD
            Path reversePath1 = new Path (reverseList1);
            this.path = reversePath1;*/
            Collections.reverse(path.getPath());
=======
            this.path = new Path (reverseList);
>>>>>>> master
            distance = 0;
        }

<<<<<<< HEAD
                }
                if (this.temporaryPath != null && !over50) {
                    //reverse path
                    if (this.temporaryPath.getPathSize() <= distance) {
                        /*
                        List<Coordinate> reverseList2 = new ArrayList<>();
                        for (int i = path.getPath().size() - 1; i >= 0; i--) {
                            Coordinate c1 = path.getPath().get(i);
                            reverseList2.add(c1);
                        }
                        Path reversePath2 = new Path(reverseList2);
                        this.path = reversePath2;*/
                        Collections.reverse(path.getPath());
                        distance = 0;
                        startOfAutobus = path.getPath().get(0);
                    }
                }
                else {
                    startOfAutobus = path.getPath().get(0);
                }
            }
        }

        Coordinate coords = path.getCoordinateByDistance(distance, path, startOfAutobus, this);
=======
        //calculate new coordinates
        Coordinate coords = path.getCoordinateByDistance(distance,this);
>>>>>>> master

        if (gui.get(0).getId().equals("3")) {
            //System.out.println(coords);
        }


        if (first_position) {
            iPosition = coords;
            first_position = false;
        }
        if(click_position) {
            coords = iPosition;
            distance = 0;
            click_position = false;
        }

        //move autobus
        moveGui(coords);
        //set new position of autobus
        position = coords;
    }



    /** Get position of autobus (getter for yaml) **/
    public Coordinate getPosition() {
        return position;
    }

    /** Get speed of autobus (getter for yaml) **/
    public double getSpeed() {
        return speed;
    }

    /** Get path of autobus (getter for yaml) **/
    public Path getPath() {
        return path;
    }

    /** Get id of line of autobus (getter for yaml) **/
    public String getIdOfLine() {
        return idOfLine;
    }

    /** Get line of autobus **/
    public Line getLine() {
        return line;
    }


    /** Set line of autobus (setting lines in main)**/
    public void set_line(Line line){
        this.line = line;
    }

    /** Set street on which autobus is**/
    public void setAutobusIsOnStreet(Street street) {
        this.autobusIsOnStreet = street;
    }

    /** Set distance to autobus (setting starting distance in main)**/
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /** To string function **/
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
