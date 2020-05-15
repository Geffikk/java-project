package sample.source.imap;

import sample.source.map.Street;

import java.util.List;

public interface iDataStreets {

    /**
     * Return list of streets (getter for yaml)
     * @return list of streets
     */
    List<Street> getStreets();

    /**
     * To string function
     * @return string format
     */
    String toString();
}
