package sample.source.map;

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
    boolean first = true;
    // Load street before
    Street str_before;
    // Return Simmutable list with lines
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street, Stop>> abs_map = new ArrayList<>();
    static int counter = 0;
    Stop first_stop;
    Stop last_stop;
    private List<Line> lineInformation = new ArrayList<>();

    // Line ID
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
            str_before = stop.getStreet();
            first = false;
            return true;
        }
        else if(!str_before.follows(stop.getStreet())) {
            return false;
        }
        last_stop = stop;
        abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
        str_before = stop.getStreet();
        return true;
    }

    /** Add street to line **/
    public boolean addStreet(Street... street) {
        for(Street str : street) {
            if (first) {
                //Add first stop to line
                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(str, null));
                str_before = str;
                first = false;
            } else if (!str_before.follows(str)) {
                System.out.println("Ulice na seba nenavazuju !");
                return false;
            }
            else {
                abs_map.add(new AbstractMap.SimpleImmutableEntry<>(str, null));
            }
            str_before = str;
        }
        return true;
    }

    /** Return list with streets/stops **/
    @Override
    public List<AbstractMap.SimpleImmutableEntry<Street, Stop>> getRoute() {
        return new ArrayList<>(abs_map);
    }

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

