package sample.source.imap;

import javafx.scene.shape.Shape;
import java.util.List;

public interface Drawable {

    /**
     * Paint streets to GUI
     * @return -> GUI */
    List<Shape> getGUI();
}
