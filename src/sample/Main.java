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

<<<<<<< HEAD
<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> master
=======
import java.io.File;
>>>>>>> master
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/sample.fxml"));
        BorderPane root = loader.load();
        primaryStage.setTitle("Map");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        Controller controller = loader.getController();
        List<Drawable> elements = new ArrayList<>();
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master


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
<<<<<<< HEAD
=======
        elements.add(new Vehicle(new Coordinate(0, 0), 2, new Path(Arrays.asList(
                new Coordinate(100, 100),
                    new Coordinate(150, 150)
        ))));
        elements.add(new Street("Komenskeho", new Coordinate(100, 100), new Coordinate(200, 100)));
        elements.add(new Street("Sazavskeho", new Coordinate(400, 250), new Coordinate(350, 300)));
>>>>>>> master
=======
>>>>>>> master

        controller.setElements(elements);
        controller.startTime();
        //mapper.writeValue(new File("data2.yml"), data);

    }



}
