package sample.source.map;

import sample.source.imap.iDataLines;

import java.util.List;

/**
 * Class for store info about all lines on map
 */
public class DataLines implements iDataLines {

    private List<Line> lines; /* list of lines */

    /**
     * Empty constructor for yaml
     */
    private DataLines() {}

    /**
     * Returns lines (getter for yaml)
     * @return lines
     */
    public List<Line> getLines() {
        return lines;
    }


}
