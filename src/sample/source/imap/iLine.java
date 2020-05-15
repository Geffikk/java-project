package sample.source.imap;

import sample.source.map.Stop;
import sample.source.map.Street;

import java.util.ArrayList;
import java.util.List;

public interface iLine {

    /**
     * Return id of line (getter for yaml)
     * @return  ID
     */
    String getId();


    /**
     * Return list of stops in line (getter for yaml)
     * @return list of stops
     */
    List<Stop> getStopList();

    /**
     * Return list of streets in line (getter for yaml)
     * @return list of streets
     */
    List<Street> getStreetList();

    /**
     * Return list of departures (getter for yaml)
     * @return list in list of strings (departures)
     */
    List<ArrayList<String>> getListOfDepartures1();

    /**
     * Return list of departures (getter for yaml)
     * @return list in list of strings (departures)
     */
    List<ArrayList<String>> getListOfDepartures2();


    /**
     * Function for making abs_map of line
     * @param street which will be added
     * @param stop which will be added
     */
    void addStreetAndStopToAbsMap(Street street, Stop stop);

    /**
     * Return list with streets/stops
     * @return abstract map
     */
    java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();



}
