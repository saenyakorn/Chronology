package fxmlutils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class FXMLUtils {
    public static void loadFXML(Parent component) {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(component);
        loader.setControllerFactory(theClass -> component);
        String fileName = component.getClass().getSimpleName() + ".fxml";
        try {
            loader.load(component.getClass().getResource(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
