/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate2;
import sample.source.imap.iLine;

import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.*;


/**
 * Class represents Line
 */
public class Line implements iLine, Drawable{

    private String id; /* id line */
    @JsonIgnore
    Street str_before; /* load street before */
    @JsonIgnore /* immutable list with lines */
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street, Stop>> abs_map = new ArrayList<>();
    @JsonIgnore
    private List<Shape> gui = new ArrayList<>(); /* list of vehicles (gui) */
    @JsonIgnore
    static int counter = 0;
    private List<Street> streetList = new ArrayList<>(); /* list of streets in line */
    private List<Stop> stopList = new ArrayList<>(); /* list of stops in line */
    private List<ArrayList<String>> listOfDepartures1 = new ArrayList<>(); /* list of lists of departures in line */
    private List<ArrayList<String>> listOfDepartures2 = new ArrayList<>(); /* list of lists of departures in line */

    /**
     * Empty constructor for yaml
     * @param shape
     */
    private Line(Shape shape) {
    }

    private Line() {
    }

    //   iLine Interface
    @Override
    public void addStreetAndStopToAbsMap(Street street, Stop stop){
        this.abs_map.add(new AbstractMap.SimpleImmutableEntry<>(street, stop));
        if(stop != null){
            this.str_before = stop.getStreet();
        }
    }

    @Override
    public List<AbstractMap.SimpleImmutableEntry<Street, Stop>> getRoute() {
        return new ArrayList<>(abs_map);
    }

    //                                         Getters

    @Override
    public String getId() {
        return id;
    }


    @Override
    public List<Stop> getStopList() {
        return stopList;
    }

    @Override
    public List<Street> getStreetList() {
        return streetList;
    }

    @Override
    public List<ArrayList<String>> getListOfDepartures1() {
        return listOfDepartures1;
    }

    @Override
    public List<ArrayList<String>> getListOfDepartures2() {
        return listOfDepartures2;
    }

    //                                      Drawable Interface
    @Override
    @JsonIgnore
    public List<Shape> getGUI() {

        //If there is street with more than 2 coordiantes
        List<Shape> shapes = new ArrayList<>();
        javafx.scene.shape.Line line = null;
        int numberOfCoordiantes = this.abs_map.get(counter).getKey().getCoordinates().size();

        //If street contains 2 coordinates
        if (numberOfCoordiantes == 2){
             line = new javafx.scene.shape.Line(this.abs_map.get(counter).getKey().getCoordinates().get(0).getX(),
                    this.abs_map.get(counter).getKey().getCoordinates().get(0).getY(),
                    this.abs_map.get(counter).getKey().getCoordinates().get(1).getX(),
                    this.abs_map.get(counter).getKey().getCoordinates().get(1).getY());
        }
        //If street contains more than 2 coordinates
        else{
            for(int i = 0; i<numberOfCoordiantes; i++){
                if(i == (numberOfCoordiantes - 1)){
                    break;
                }

                line = new javafx.scene.shape.Line(this.abs_map.get(counter).getKey().getCoordinates().get(i).getX(),
                        this.abs_map.get(counter).getKey().getCoordinates().get(i).getY(),
                        this.abs_map.get(counter).getKey().getCoordinates().get(i+1).getX(),
                        this.abs_map.get(counter).getKey().getCoordinates().get(i+1).getY());
                shapes.add(line);
                gui.add(line);
            }
        }
        counter = counter + 1;

        if(shapes.isEmpty()){
            switch (this.id) {
                case "1":
                    Objects.requireNonNull(line).setId("1");
                    break;
                case "2":
                    Objects.requireNonNull(line).setId("2");
                    break;
                case "3":
                    Objects.requireNonNull(line).setId("3");
                    break;
            }
        }
        else{
            for (Shape lineOfStreet: shapes) {
                switch (this.id) {
                    case "1":
                        lineOfStreet.setId("1");
                        break;
                    case "2":
                        lineOfStreet.setId("2");
                        break;
                    case "3":
                        lineOfStreet.setId("3");
                        break;
                }
            }
        }

        if (str_before.getId().equals(this.abs_map.get(counter-1).getKey().getId())){
            counter = 0;
        }

        if(shapes.isEmpty()) {
            gui.add(line);
            return Collections.singletonList(line);
        }
        else {
            return shapes;
        }
    }

    /*
    @Override
    @JsonIgnore
    public void update2(Time mapTime, String closeStreetName) {

        /*
        for(Street street: this.streetList) {
            if(street.getId().equals(closeStreetName)){
                System.out.println("Je sucastou linky");
            }
        }

        for(Shape shape: gui) {
            System.out.println(shape);
            shape.setStrokeWidth(5);
            shape.setOnMouseClicked(mouseEvent -> shape.setStrokeWidth(1));
        }*
    }*/
}

