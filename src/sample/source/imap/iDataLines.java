package sample.source.imap;

import sample.source.map.Line;

import java.util.List;

public interface iDataLines {

    /**
     * Return list of lines (getter for yaml)
     * @return -> list of lines */
    List<Line> getLines();

    /**
     * To string function
     * @return -> string format */
    String toString();
}
