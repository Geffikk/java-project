
/**
 -  PROJECT: Simulacia liniek MHD
 -  Authors: Maroš Geffert <xgeffe00>, Patrik Tomov <xtomov02>
 -  Date: 10.5.2020
 -  School: VUT Brno
 */

/* Package */
package sample.source.imap;

/* Imports */
import javafx.scene.shape.Shape;
import java.util.List;
public interface Drawable {

    /**
     * Paint streets to GUI
     * @return  GUI.
     */
    List<Shape> getGUI();
}