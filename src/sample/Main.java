package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import sample.source.imap.Drawable;
import sample.source.map.*;

import java.util.ArrayList;
import java.util.Arrays;
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
        elements.add(new Vehicle(new Coordinate(0, 0), 2, new Path(Arrays.asList(
                new Coordinate(100, 100),
                    new Coordinate(150, 150)
        ))));
        elements.add(new Street("Komenskeho", new Coordinate(100, 100), new Coordinate(200, 100)));
        elements.add(new Street("Sazavskeho", new Coordinate(400, 250), new Coordinate(350, 300)));

        controller.setElements(elements);
        controller.startTime();
    }


    public static void main(String[] args) { launch(args); }
}
