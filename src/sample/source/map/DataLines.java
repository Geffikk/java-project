package sample.source.map;

import java.util.List;

public class DataLines {
    private List<Line> lines;

    public DataLines() {
    }

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
