package sample.source.map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import sample.source.imap.Drawable;
import sample.source.imap.iStreet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Class represents Street
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Street implements iStreet, Drawable{

    private String id; /* street ID */
    private List<Coordinate> coordinates; /* list of coordinates */
    private List<Stop> stops = new ArrayList<>(); /* list of stops */
    @JsonIgnore
    public double delay = 1; /* represent load of street (slowdown vehicle) */

    /**
     * Empty constructor for yaml
     */
    private Street() {
    }

    //                                       iStreet Interface

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


    //                                       Drawable Interface

    @JsonIgnore
    @Override
    public List<Shape> getGUI() {

        //If there is street with more than 2 coordiantes
        List<Shape> shapes = new ArrayList<>();

        //Gui for street with 2 coords
        if(coordinates.size() == 2){
            return Arrays.asList(
                    new Text(Math.abs((coordinates.get(0).getX() + coordinates.get(1).getX()) /2) - 30, Math.abs((coordinates.get(0).getY() + coordinates.get(1).getY()) /2) + 20, this.id),
                    new Line(this.coordinates.get(0).getX(), this.coordinates.get(0).getY(), this.coordinates.get(1).getX(), this.coordinates.get(1).getY()));

        }
        //Gui for street with 3 and more coords
        else {
            int numberOfCoordiantes = coordinates.size();
            for(int i = 0; i<numberOfCoordiantes; i++){
                if(i == numberOfCoordiantes - 1){
                    break;
                }
                shapes.add(new Line(this.coordinates.get(i).getX(), this.coordinates.get(i).getY(), this.coordinates.get(i+1).getX(), this.coordinates.get(i+1).getY()));
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
}
