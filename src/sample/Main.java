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
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Controller controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();


        //elements.add(new Stop("stop1", new Coordinate(100,100)));

        //controller.setElements(elements);
        //controller.startTime();

        elements.add(new Street("Tacevska", new Coordinate(100,100), new Coordinate(200, 100)));
        elements.add(new Street("Komenskeho", new Coordinate(100,100), new Coordinate(200, 200)));
        elements.add(new Street("Bezrucova", new Coordinate(200,200), new Coordinate(300, 200)));
       // DataStreets data = new DataStreets(stops);


        YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        ObjectMapper mapper = new ObjectMapper(factory);

        DataAutobuses data1 = mapper.readValue(new File("data.yml"), DataAutobuses.class);

        elements.addAll(data1.getAutobuses());

        controller.setElements(elements);
        controller.startTime();
        //mapper.writeValue(new File("data2.yml"), data);

    }



}
