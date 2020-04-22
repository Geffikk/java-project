package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import sample.source.imap.Drawable;
import sample.source.map.*;

import java.net.URL;
import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.File;
import java.sql.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/sample.fxml"));
        BorderPane root = loader.load();
        primaryStage.setTitle("Map");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

        Controller controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();

        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);

        // Load streets and stops
        DataStreets dataOfStreets = mapper.readValue(new File("data2.yml"), DataStreets.class);
        // Add STREETS to map
        elements.addAll(dataOfStreets.getStreets());

        // Load lines
        DataLines dataOfLines = mapper.readValue(new File("data3.yml"), DataLines.class);

        // Make route of each line
        for (Line line: dataOfLines.getLines()) {

            List<Street> streets1 = line.getStreetList();

            for (Street str: streets1) {
                List<Stop> stops = str.getStops();
                if (stops.isEmpty()){
                    line.addStreetAndStopToAbsMap(str, null);
                }
                else{
                    for (Stop stop: stops) {
                        line.addStreetAndStopToAbsMap(stop.getStreet(), stop);

                    }
                }
            }
            // Add LINE to map
            for(int i = 0; i<line.getRoute().size(); i++){
                elements.add(line);
            }

        }

        // Add all STOPS on each street to map
        for (Street street: dataOfStreets.getStreets()) {
            //streets.add(street);

            List<Stop> stops = street.getStops();
            for (Stop stop: stops) {
                elements.add(stop);
            }
        }

        // Load vehicles and their path
        DataAutobuses dataOfVehicles = mapper.readValue(new File("data.yml"), DataAutobuses.class);

        // Add vehicles to map
        elements.addAll(dataOfVehicles.getAutobuses());
        
        controller.setElements(elements);
        controller.startTime();
        //mapper.writeValue(new File("data3.yml"), data);

    }
}
