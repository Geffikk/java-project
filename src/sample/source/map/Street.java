/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.map;

/* Imports */
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.scene.control.Alert;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.TimerUpdate2;
import sample.source.imap.iStreet;

import java.sql.Time;
import java.util.*;


/**
 * Class represents Street
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Street implements iStreet, Drawable, TimerUpdate2 {

    private String id; /* street ID */
    private List<Coordinate> coordinates; /* list of coordinates */
    private List<Stop> stops = new ArrayList<>(); /* list of stops */
    @JsonIgnore
    private List<Shape> gui = new ArrayList<>(); /* list of vehicles (gui) */
    @JsonIgnore
    private static List<Street> obchadzka = new ArrayList<>();
    @JsonIgnore
    public double delay = 1; /* represent load of street (slowdown vehicle) */
    private List<Boolean> arrayOfLines =  new ArrayList<>();

    /*********************************************************
     * Return array which represent how mauch lines has street
     * @return - boolean array
     *********************************************************/

    public List<Boolean> getArrayOfLines() {
        return arrayOfLines;
    }

    /********************************
     * Get new trace of closed street
     * @return - list of coordinates
     ********************************/
    @JsonIgnore
    public static List<Street> getObchadzka() {
        return obchadzka;
    }

    public boolean follows(Street s2) {
        return (s2.begin().equals(begin()) || s2.begin().equals(end()) || s2.end().equals(end()) || s2.end().equals(begin()));
    }

    /****************************
     * Empty constructor for yaml
     ****************************/
    private Street() {
    }

    // iStreet Interface
    @Override
    public Coordinate begin() {
        return this.coordinates.get(0);
    }

    @Override
    public Coordinate end() {
        return this.coordinates.get(this.coordinates.size() - 1);
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return Collections.unmodifiableList(this.coordinates);
    }

    @Override
    public double getDelay() { return this.delay; }

    @Override
    public List<Stop> getStops() {
        return stops;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("street(%s)", id);
    }

    @JsonIgnore
    public void setLineToStreet(sample.source.map.Line line) {
        String idOfLine = line.getId();
        if(this.arrayOfLines.isEmpty()){
            this.arrayOfLines.add(false);
            this.arrayOfLines.add(false);
            this.arrayOfLines.add(false);
        }

        switch (idOfLine){
            case "1":
                this.arrayOfLines.set(0,true);
                break;
            case "2":
                this.arrayOfLines.set(1,true);
                break;
            case "3":
                this.arrayOfLines.set(2,true);
                break;
            default:
                break;
        }
    }

    // Drawable Interface
    @JsonIgnore
    @Override
    public List<Shape> getGUI() {

        //If there is street with more than 2 coordiantes
        List<Shape> shapes = new ArrayList<>();
        Line line = new Line(this.coordinates.get(0).getX(), this.coordinates.get(0).getY(), this.coordinates.get(1).getX(), this.coordinates.get(1).getY());
        line.setId(id);

        //Gui for street with 2 coords
        if(coordinates.size() == 2){
            gui.add(line);
            return Arrays.asList(
                    new Text(Math.abs((coordinates.get(0).getX() + coordinates.get(1).getX()) /2) - 30, Math.abs((coordinates.get(0).getY() + coordinates.get(1).getY()) /2) + 20, this.id),
                    line);

        }
        //Gui for street with 3 and more coords
        else {
            int numberOfCoordiantes = coordinates.size();
            for(int i = 0; i<numberOfCoordiantes; i++){
                if(i == numberOfCoordiantes - 1){
                    break;
                }
                line = new Line(this.coordinates.get(i).getX(), this.coordinates.get(i).getY(), this.coordinates.get(i+1).getX(), this.coordinates.get(i+1).getY());
                line.setId(id);
                gui.add(line);
                shapes.add(line);
            }
            //When number of coords is even (TEXT centring)
            int mid =(numberOfCoordiantes-1) / 2;
            if((numberOfCoordiantes%2)==0){
                shapes.add(new Text(Math.abs((coordinates.get(mid).getX() + coordinates.get(mid+1).getX()) /2) - 30, Math.abs((coordinates.get(mid).getY() + coordinates.get(mid+1).getY()) /2) + 20, this.id));
            }
            //When number of coords is odd (TEXT centring)
            else {
                shapes.add(new Text(coordinates.get(mid).getX() + 20, coordinates.get(mid).getY(), this.id));
            }

            return shapes;
        }
    }

    @Override
    public void update2(Time mapTime, Street closeStreet) {

        for(Shape shape: gui) {

            // Do not make stroke width 10 for blocked street
            if (shape.getId().equals(closeStreet.getId())) {
                continue;
            }

            // Also do not make stroke width 10 for streets in line,
            // where is blocked line
            if (closeStreet.arrayOfLines.get(0) && this.arrayOfLines.get(0)) {
                continue;
            }
            if (closeStreet.arrayOfLines.get(1) && this.arrayOfLines.get(1)) {
                continue;
            }
            if (closeStreet.arrayOfLines.get(2) && this.arrayOfLines.get(2)) {
                continue;
            }

            shape.setStrokeWidth(10);
            shape.setOnMouseClicked(mouseEvent -> addToNewTrace(shape));
        }
    }

    @Override
    public void setShapes1() {
        for(Shape shape: gui) {
            shape.setStrokeWidth(1);
        }
    }

    public Street initializeClosedStreet(String closeStreetName) {
        if(this.getId().equals(closeStreetName)) {
            return this;
        }
        return null;
    }

    /***************************************************
     * add streets to the new trace (after block street)
     * @param shape1
     ***************************************************/
    public void addToNewTrace(Shape shape1) {

        boolean error = false;
        boolean isThere = false;

        for (Shape shape2 : gui) {
            if(shape2.getId().equals(shape1.getId())) {
                if(!obchadzka.isEmpty()) {
                    if(!obchadzka.get(obchadzka.size()-1).follows(this)) {
                        error = true;
                        break;
                    }
                }
                shape2.setStrokeWidth(1);
            }

            for(Street ulica: obchadzka) {
                if (shape1.getId().equals(ulica.getId())) {
                    isThere = true;
                    break;
                }
            }

            if(!isThere) {
                obchadzka.add(this);
            }
        }

        if(error) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "\n" +
                    "the new path must follow");
            alert.showAndWait();
        }
    }

    public void clearArray(){
        obchadzka.clear();
    }

    @Override
    public double getDistanceBetweenCoordinates(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public double getPathSize() {
        double size = 0;
        for(int i=0; i< obchadzka.size(); i++) {
            for(int j=0; j< obchadzka.get(i).getCoordinates().size()-1; j++) {
                Coordinate a = obchadzka.get(i).getCoordinates().get(j);
                Coordinate b = obchadzka.get(i).getCoordinates().get(j+1);
                size += getDistanceBetweenCoordinates(a, b);
            }
        }
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Street)) {
            return false;
        }
        Street a = (Street) obj;
        return a.getId().equals(getId());
    }
}
