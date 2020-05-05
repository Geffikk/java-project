package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iLine;

import java.util.*;

public class Line implements iLine, Drawable {

    // ID linky
    private String id;
    // Add first street/stop
    @JsonIgnore
    boolean first = true;
    // Load street before
    @JsonIgnore
    Street str_before;
    // Return Simmutable list with lines
    @JsonIgnore
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street, Stop>> abs_map = new ArrayList<>();
    @JsonIgnore
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<String, List<Stop>>> informationQueue = new ArrayList<>();
    @JsonIgnore
    static int counter = 0;
    @JsonIgnore
    Stop first_stop;
    @JsonIgnore
    Stop last_stop;
    List<Stop> lineStops = new ArrayList<>();
    //List of streets in line
    private List<Street> streetList = new ArrayList<>();
    //List of stops in line
    private List<Stop> stopList = new ArrayList<>();
    //List of lists of departures in line
    private List<ArrayList<String>> listOfDepartures = new ArrayList<ArrayList<String>>();

    /** Empty constructor for yaml**/
    private Line() {
    }

    /** Normal constructor **/
    public Line(String id) {
        this.id = id;
    }


    /** Add stop to LINE **/
    /** Not used **/
    public boolean addStop(Stop stop) {
        if(first) {
            first_stop = stop;
            // Add first stop to line
            abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
            this.stopList.add(stop);
            if(!streetList.contains(stop.getStreet())){
                this.streetList.add(stop.getStreet());
            }
            str_before = stop.getStreet();
            first = false;
            return true;
        }
        else if(!str_before.follows(stop.getStreet())) {
            return false;
        }
        last_stop = stop;
        abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
        this.stopList.add(stop);
        if(!streetList.contains(stop.getStreet())){
            this.streetList.add(stop.getStreet());
        }
        str_before = stop.getStreet();
        return true;
    }

    /** Add street to line **/
    public boolean addStreet(Street... street) {
        for(Street str : street) {
            if (first) {
                //Add first stop to line
                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(str, null));
                this.streetList.add(str);
                str_before = str;
                first = false;
            } else if (!str_before.follows(str)) {
                System.out.println("Ulice na seba nenavazuju !");
                return false;
            }
            else {
                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(str, null));
                this.streetList.add(str);
            }
            str_before = str;
        }
        return true;
    }


    /** Not used **/
/*
    public boolean addStop(Stop... stops) {
        for (Stop stop: stops) {
            if (first) {
                first_stop = stop;
                // Add first stop to line
                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
                str_before = stop.getStreet();
                first = false;
            } else if (!str_before.follows(stop.getStreet())) {
                return false;
            }
            else {
                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
                str_before = stop.getStreet();
            }
            last_stop = stop;
            lineStops.add(stop);
        }
*/
//    public boolean addStop(Stop stop) {
//        if(first) {
//            first_stop = stop;
//            // Add first stop to line
//            abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
//            this.stopList.add(stop);
//            if(!streetList.contains(stop.getStreet())){
//                this.streetList.add(stop.getStreet());
//            }
//            str_before = stop.getStreet();
//            first = false;
//            return true;
//        }
//        else if(!str_before.follows(stop.getStreet())) {
//            return false;
//        }
//        last_stop = stop;
//        abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
//        this.stopList.add(stop);
//        if(!streetList.contains(stop.getStreet())){
//            this.streetList.add(stop.getStreet());
//        }
//        str_before = stop.getStreet();
//        return true;
//    }
//
//
//
//    public boolean addStreet(Street... street) {
//        for(Street str : street) {
//            if (first) {
//                //Add first stop to line
//                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(str, null));
//                this.streetList.add(str);
//                str_before = str;
//                first = false;
//            } else if (!str_before.follows(str)) {
//                System.out.println("Ulice na seba nenavazuju !");
//                return false;
//            }
//            else {
//                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(str, null));
//                this.streetList.add(str);
//            }
//            str_before = str;
//        }
//        return true;
//    }


    /** Return id of line (getter for yaml) **/
    public String getId() {
        return id;
    }

    /** Return list of stops in line (getter for yaml) **/
    public List<Stop> getStopList() {
        return stopList;
    }

    /** Return list of streets (getter for yaml) **/
    public List<Street> getStreetList() {
        return streetList;
    }

    /** Return list of departures (getter for yaml) **/
    public List<ArrayList<String>> getListOfDepartures() {
        return listOfDepartures;
    }

    /** Function for making abs_map of line **/
    @JsonIgnore
    public void addStreetAndStopToAbsMap(Street street, Stop stop){
        this.abs_map.add(new AbstractMap.SimpleImmutableEntry<>(street, stop));
        if(stop != null){
            this.str_before = stop.getStreet();
        }
    }

    /** Return list with streets/stops **/
    @Override
    @JsonIgnore
    public List<AbstractMap.SimpleImmutableEntry<Street, Stop>> getRoute() {
        return new ArrayList<>(abs_map);
    }


    /** Paint streets to GUI **/
    @JsonIgnore
    public List<Shape> getGUI() {

        //If there is street with more than 2 coordiantes
        List<Shape> shapes = new ArrayList<>();
        javafx.scene.shape.Line line = null;
        int numberOfCoordiantes = this.abs_map.get(counter).getKey().getCoordinates().size();

        if (numberOfCoordiantes == 2){
             line = new javafx.scene.shape.Line(this.abs_map.get(counter).getKey().getCoordinates().get(0).getX(),
                    this.abs_map.get(counter).getKey().getCoordinates().get(0).getY(),
                    this.abs_map.get(counter).getKey().getCoordinates().get(1).getX(),
                    this.abs_map.get(counter).getKey().getCoordinates().get(1).getY());
        }
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
            }
        }
        counter = counter + 1;

        if(shapes.isEmpty()){
            if (this.id.equals("1")) {
                line.setStroke(Color.RED);
                line.setId("1");
            }
            else if(this.id.equals("2")) {
                line.setStroke(Color.GREEN);
                line.setId("2");
            }
        }
        else{
            for (Shape lineOfStreet: shapes) {
                if (this.id.equals("1")) {
                    lineOfStreet.setStroke(Color.RED);
                    lineOfStreet.setId("1");
                }
                else if(this.id.equals("2")) {
                    lineOfStreet.setStroke(Color.GREEN);
                    lineOfStreet.setId("2");
                }
            }
        }

        if (str_before.getId().equals(this.abs_map.get(counter-1).getKey().getId())){
            counter = 0;
        }

        if(shapes.isEmpty()){
            return Collections.singletonList(line);
        }
        else{
            return shapes;
        }
    }

    /** Show line informations **/
    public Text showInformation() {
        informationQueue.add(new AbstractMap.SimpleImmutableEntry<>(this.id, stopList));
        String output = null;

        for (Stop stop : stopList) {
            output = output + "\n" + stop.getId();
        }
        return new Text(400, 400, output);
    }
}

