package sample;

import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.source.imap.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Pane content;
    private List<Drawable> elements = new ArrayList<>();

    @FXML
    private void onZoom(ScrollEvent event) {
        event.consume();
        double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
        content.setScaleX(zoom * content.getScaleX());
        content.setScaleY(zoom * content.getScaleY());
        content.layout();
    }

    public void setElements(List<Drawable> elements) {
        this.elements = elements;

        for (Drawable drawable : elements) {
            content.getChildren().addAll(drawable.getGUI());
        }
    }
}
