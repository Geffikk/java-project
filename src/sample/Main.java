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

import java.io.IOException;
import java.net.CookiePolicy;
import java.net.URL;
//import com.google.transit.realtime.GtfsRealtime.FeedEntity;
//import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.io.File;
<<<<<<< HEAD
import java.io.File;
import java.sql.Array;
import java.util.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.File;
import java.sql.Array;
import java.util.AbstractMap;
=======
>>>>>>> master
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/sample.fxml"));
        BorderPane root = loader.load();
        primaryStage.setTitle("Map");

        primaryStage.setScene(new Scene(root, 1500, 1000));

        primaryStage.show();

        Controller controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();

        // Show Line information ///
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton button1 = new JButton();

        frame.add(panel);
        panel.add(button1);
        frame.setVisible(true);

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(frame.getComponent(0), "Hello World");

            }
        });

<<<<<<< HEAD
       // DataStreets data = new DataStreets(stops);
=======
        ////////////////////////////
>>>>>>> master

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

<<<<<<< HEAD
        YAMLFactory factory2 = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper2 = new ObjectMapper(factory2);

        List<Street> streets1 = line.getStreetList();

        for (Street str: streets1) {
            List<Stop> stops = str.getStops();
            if (stops.isEmpty()){
                line.addStreetAndStopToAbsMap(str, null);
            }
            else{
                for (Stop stop: stops) {
=======
        List<Street> streetsOfLine = line.getStreetList();

        for (Street str: streetsOfLine) {
            List<Stop> stopsOfStreet = str.getStops();
            if (stopsOfStreet == null){
                line.addStreetAndStopToAbsMap(str, null);
            }
            else{
                for (Stop stop: stopsOfStreet) {
>>>>>>> master
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
<<<<<<< HEAD
        dataOfVehicles.getAutobuses().get(0).set_line(dataOfLines.getLines().get(0));
        dataOfVehicles.getAutobuses().get(1).set_line(dataOfLines.getLines().get(1));
        dataOfVehicles.getAutobuses().get(2).set_line(dataOfLines.getLines().get(0));
=======
>>>>>>> master
        // Add vehicles to map
        elements.addAll(dataOfVehicles.getAutobuses());
        //controller.setKokot(dataOfVehicles.getAutobuses().get(0).getGUI().get(0), dataOfLines.getLines().get(0));
        controller.setElements(elements);
<<<<<<< HEAD

        //controller.setClearPane();
        controller.startTime(5);
        //mapper.writeValue(new File("data3.yml"), data);
=======
        controller.startTime(5);
        //mapper.writeValue(new File("data.yml"), data);
>>>>>>> master

    }

}
