package sample.source.map;

import java.util.List;

/** Class for store info about all lines on map **/
public class DataLines {
    //List of lines
    private List<Line> lines;

    /** Empty constructor for yaml **/
    public DataLines() {
    }

    /** Normal constructor **/
    public DataLines(List<Line> lines) {
        this.lines = lines;
    }

    /** Return list of lines (getter for yaml) **/
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
