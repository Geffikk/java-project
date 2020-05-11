package sample.source.map;

import sample.source.imap.iDataLines;

import java.util.List;

/** Class for store info about all lines on map **/
public class DataLines implements iDataLines {

    private List<Line> lines; /* list of lines */

    /** Empty constructor for yaml **/
    public DataLines() {}

    /** Normal constructor **/
    public DataLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "DataLines{" +
                "lines=" + lines +
                '}';
    }
}
