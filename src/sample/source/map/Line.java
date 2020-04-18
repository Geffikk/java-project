package sample.source.map;

import sample.source.imap.iLine;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Line implements iLine {

    private String id;
    boolean first = true;
    Street str_before;
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street, Stop>> abs_map = new ArrayList<>();

    public Line(String id) {
        this.id = id;
    }

    @Override
    public boolean addStop(Stop stop) {

        if(first) {
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

    @Override
    public boolean addStreet(Street street) {
        if(first) {
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


    @Override
    public List<AbstractMap.SimpleImmutableEntry<Street, Stop>> getRoute() {
        return new ArrayList<>(abs_map);
    }
}

