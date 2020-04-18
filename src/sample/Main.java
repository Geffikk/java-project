package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import sample.source.imap.Drawable;
import sample.source.map.Autobus;
import sample.source.map.Coordinate;
import sample.source.map.Line;
import sample.source.map.Street;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        elements.add(new Autobus(new Coordinate(100, 100), 2));
        elements.add(new Autobus(new Coordinate(200, 100), 2));
        elements.add(new Autobus(new Coordinate(400, 250), 2));
        elements.add(new Street("Komenskeho", new Coordinate(100, 100), new Coordinate(200, 100)));
        elements.add(new Street("Sazavskeho", new Coordinate(200, 100), new Coordinate(350, 300)));
        elements.add(new Street("Tacevska", new Coordinate(400, 250), new Coordinate(350, 400)));
        elements.add(new Street("Kokotna", new Coordinate(350, 400), new Coordinate(200, 560)));
        elements.add(new Street("Jebo", new Coordinate(200, 560), new Coordinate(200, 400)));
        elements.add(new Street("Tota na picu", new Coordinate(200, 400), new Coordinate(100, 100)));

        controller.setElements(elements);

    }


    public static void main(String[] args) { launch(args); }
}
