package sample.source.imap;

import sample.source.map.Autobus;
import sample.source.map.Coordinate;

import java.util.List;

public interface iDataAutobuses {

    /**
     * Return list of coordiantes (getter for yaml)
     * @return -> list of coordinates */
    List<Coordinate> getCoordinates();

    /**
     * Return list of autobuses (getter for yaml)
     * @return -> list of vehicles */
    List<Autobus> getAutobuses();

    /**
     * To string function
     * @return -> string format */
    String toString();
}
