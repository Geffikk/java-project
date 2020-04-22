package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import sample.source.imap.Drawable;
import sample.source.imap.iLine;

import java.lang.reflect.Array;
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
    static int counter = 0;
    @JsonIgnore
    Stop first_stop;
    @JsonIgnore
    Stop last_stop;
    @JsonIgnore
    private List<Line> lineInformation = new ArrayList<>();
    private List<Street> streetList = new ArrayList<>();
    private List<Stop> stopList = new ArrayList<>();
    private List<ArrayList<String>> listOfDepartures = new ArrayList<ArrayList<String>>();


    private Line() {
    }

    public Line(String id) {
        this.id = id;
    }


    /** Add stop to LINE **/
    @Override
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

    public String getId() {
        return id;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public List<Street> getStreetList() {
        return streetList;
    }

    public List<ArrayList<String>> getListOfDepartures() {
        return listOfDepartures;
    }

    public void addDeparture(ArrayList<String> departures){
        this.listOfDepartures.add(departures);
    }

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

    @JsonIgnore
    /** Paint streets to GUI **/
    public List<Shape> getGUI() {
        javafx.scene.shape.Line line = new javafx.scene.shape.Line(this.abs_map.get(counter).getKey().getCoordinates().get(0).getX(),
                this.abs_map.get(counter).getKey().getCoordinates().get(0).getY(),
                this.abs_map.get(counter).getKey().getCoordinates().get(1).getX(),
                this.abs_map.get(counter).getKey().getCoordinates().get(1).getY());
        counter = counter + 1;

        if (this.id.equals("1")) {
            line.setStroke(Color.RED);
        }
        else if(this.id.equals("2")) {
            line.setStroke(Color.GREEN);
        }

        if (str_before.getId().equals(this.abs_map.get(counter-1).getKey().getId())){
            counter = 0;
        }
        return Collections.singletonList(line);

    }




}

