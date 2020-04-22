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
import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.File;
import java.sql.Array;
import java.util.*;

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

        Street str1 = new Street("Tacevska", new Coordinate(100,100), new Coordinate(200, 100));
        Street str2 = new Street("Komenskeho", new Coordinate(100,100), new Coordinate(200, 200));
        Street str3 = new Street("Bezrucova", new Coordinate(200,200), new Coordinate(300, 200));
        Street str4 = new Street("Komenskeho", new Coordinate(300,200), new Coordinate(300, 500));
        Street str5 = new Street("Kokotna", new Coordinate(300,500), new Coordinate(100, 500));
        Street str6 = new Street("Ajaja", new Coordinate(300,200), new Coordinate(600, 200));
        Street str7 = new Street("Tuhore", new Coordinate(600,200), new Coordinate(600, 400));
        Street str8 = new Street("Trinac", new Coordinate(600,400), new Coordinate(300, 400));
        Street str9 = new Street("Bacovska", new Coordinate(300,200), new Coordinate(600, 400));

        Stop stop1 = new Stop("Zastavka1", new Coordinate(100, 100));
        Stop stop2 = new Stop("Zastavka2", new Coordinate(300, 300));
        Stop stop3 = new Stop("Zastavka3", new Coordinate(300, 500));
        Stop stop4 = new Stop("Zastavka4", new Coordinate(300, 200));
        Stop stop5 = new Stop("Zastavka5", new Coordinate(600, 400));

        elements.add(str1);
        elements.add(str2);
        elements.add(str3);
        /*
        str1.addStop(stop1);
        str4.addStop(stop2);
        str5.addStop(stop3);
        str8.addStop(stop5);
        str6.addStop(stop4);
        */
       // DataStreets data = new DataStreets(stops);

        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);
        // Load vehicles and their path *//
        DataAutobuses data1 = mapper.readValue(new File("data.yml"), DataAutobuses.class);

        Line line = new Line("1");
        Line line2 = new Line("2");

        line2.addStreet(str3, str9);
        line.addStreet(str5, str4, str6, str7, str8);

        for( int i = 0; i<line.getRoute().size(); i++) {
            elements.add(line);
        }
        for( int i = 0; i<line2.getRoute().size(); i++) {
            elements.add(line2);
        }

        YAMLFactory factory2 = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper2 = new ObjectMapper(factory2);

        // Load streets and stops
        DataStreets data2 = mapper2.readValue(new File("data2.yml"), DataStreets.class);
        // Add streets to map
        elements.addAll(data2.getStreets());
        // Add all stops on each street to map
        for (Street street: data2.getStreets()) {
            List<Stop> stops = street.getStops();
            for (Stop stop: stops) {
                elements.add(stop);
            }
        }

        // Add vehicles to map
        elements.addAll(data1.getAutobuses());
        
        controller.setElements(elements);
        controller.startTime(5);
       // mapper.writeValue(new File("data2.yml"), data);

    }

}
