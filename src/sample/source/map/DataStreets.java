package sample.source.map;

import java.util.List;

public class DataStreets {
    private List<Street> streets;

    public DataStreets() {
    }

    public DataStreets(List<Street> streets) {
        this.streets = streets;
    }

    public List<Street> getStreets() {
        return streets;
    }

    @Override
    public String toString() {
        return "DataStreets{" +
                "streets=" + streets +
                '}';
    }
}
