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

        ////////////////////////////

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
            if (stopsOfStreet == null){
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
        controller.startTime(5);
        //mapper.writeValue(new File("data.yml"), data);

    }

}
