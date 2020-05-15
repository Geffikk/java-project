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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/sample.fxml"));
        BorderPane root = loader.load();
        primaryStage.setTitle("Map");

        primaryStage.setScene(new Scene(root, 1835, 1030));

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

            List<Street> streetsOfLine = line.getStreetList();

            for (Street str: streetsOfLine) {
                List<Stop> stopsOfStreet = str.getStops();
                if (stopsOfStreet.size() == 0){
                    line.addStreetAndStopToAbsMap(str, null);
                }
                else{
                    for (Stop stop: stopsOfStreet) {
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
            List<Stop> stops = street.getStops();
            for (Stop stop: stops) {
                elements.add(stop);
            }
        }

        // Load vehicles and their path
        DataAutobuses dataOfVehicles = mapper.readValue(new File("data.yml"), DataAutobuses.class);

        //assing line 1 to first 10 autobuses and starting position(distance)
        for(int i = 0; i < 10; i++){
            //assing line to autobus
            dataOfVehicles.getAutobuses().get(i).set_line(dataOfLines.getLines().get(0));
            //lenght of path
            double lenghtOfPath = dataOfVehicles.getAutobuses().get(i).getPath().getPathSize();
            //10 autobuses on line so lenghtOfPath/10
            double distanceToAdd = lenghtOfPath/5;
            //add path to autobus based on direction of path
            if(i<6){
                if(i%2 == 0){
                    dataOfVehicles.getAutobuses().get(i).setDistance(distanceToAdd*i);
                }
                else{
                    dataOfVehicles.getAutobuses().get(i).setDistance(distanceToAdd*(5 - i));
                }
            }
            else{
                if(i%2 == 0){
                    dataOfVehicles.getAutobuses().get(i).setDistance(distanceToAdd*(i - 5));
                }
                else{
                    dataOfVehicles.getAutobuses().get(i).setDistance(distanceToAdd*(5 - (i - 5)));
                }
            }
        }

        //assing line 2 to next 10 autobuses and starting position(distance)
        for(int j = 10; j < 20; j++){
            //assing line to autobus
            dataOfVehicles.getAutobuses().get(j).set_line(dataOfLines.getLines().get(1));
            //lenght of path
            double lenghtOfPath = dataOfVehicles.getAutobuses().get(j).getPath().getPathSize();
            //10 autobuses on line so lenghtOfPath/10
            double distanceToAdd = lenghtOfPath/5;
            //add path to autobus based on direction of path
            if(j<16){
                if(j%2 == 0){
                    dataOfVehicles.getAutobuses().get(j).setDistance(distanceToAdd*(j - 10));
                }
                else{
                    dataOfVehicles.getAutobuses().get(j).setDistance(distanceToAdd*(5 - (j - 10)));
                }
            }
            else{
                if(j%2 == 0){
                    dataOfVehicles.getAutobuses().get(j).setDistance(distanceToAdd*(j - 15));
                }
                else{
                    dataOfVehicles.getAutobuses().get(j).setDistance(distanceToAdd*(5 - (j - 15)));
                }
            }
        }

        //assing line 3 to next 10 autobuses and starting position(distance)
        for(int z = 20; z < 30; z++){
            //assing line to autobus
            dataOfVehicles.getAutobuses().get(z).set_line(dataOfLines.getLines().get(2));
            //lenght of path
            double lenghtOfPath = dataOfVehicles.getAutobuses().get(z).getPath().getPathSize();
            //10 autobuses on line so lenghtOfPath/10
            double distanceToAdd = lenghtOfPath/5;
            //add path to autobus based on direction of path
            if(z<26){
                if(z%2 == 0){
                    dataOfVehicles.getAutobuses().get(z).setDistance(distanceToAdd*(z - 20));
                }
                else{
                    dataOfVehicles.getAutobuses().get(z).setDistance(distanceToAdd*(5 - (z - 20)));
                }
            }
            else{
                if(z%2 == 0){
                    dataOfVehicles.getAutobuses().get(z).setDistance(distanceToAdd*(z - 25));
                }
                else{
                    dataOfVehicles.getAutobuses().get(z).setDistance(distanceToAdd*(5 - (z - 25)));
                }
            }
        }

        // Add vehicles to map
        elements.addAll(dataOfVehicles.getAutobuses());

        controller.setImages();
        controller.setElements(elements);
        controller.startTime(5);
    }
}
