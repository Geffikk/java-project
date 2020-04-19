package sample.source.map;

import sample.source.imap.iLine;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Line implements iLine {

    // ID linky
    private String id;
    // Add first street/stop
    boolean first = true;
    // Load street before
    Street str_before;
    // Return Simmutable list with lines
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street, Stop>> abs_map = new ArrayList<>();

    // Line ID
    public Line(String id) {
        this.id = id;
    }

    /** Add stop to LINE **/
    @Override
    public boolean addStop(Stop stop) {
        if(first) {
            // Add first stop to line
            abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
            str_before = stop.getStreet();
            first = false;
            return true;
        }
        else if(!str_before.follows(stop.getStreet())) {
            return false;
        }
        abs_map.add(new AbstractMap.SimpleImmutableEntry<>(stop.getStreet(), stop));
        str_before = stop.getStreet();
        return true;
    }

    /** Add street to line **/
    @Override
    public boolean addStreet(Street street) {
        if(first) {
            // Add first stop to line
            abs_map.add(new AbstractMap.SimpleImmutableEntry<>(street, null));
            str_before = street;
            first = false;
            return true;
        }
        else if(!str_before.follows(street)) {
            return false;
        }

        abs_map.add(new AbstractMap.SimpleImmutableEntry<>(street, null));
        str_before = street;
        return true;
    }

    /** Return list with streets/stops **/
    @Override
    public List<AbstractMap.SimpleImmutableEntry<Street, Stop>> getRoute() {
        return new ArrayList<>(abs_map);
    }
}

