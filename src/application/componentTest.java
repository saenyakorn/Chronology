package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class componentTest extends Application {

    public static void main(String[] args) {
        // On branch components
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/componentTest.fxml"));
            mainWindow.setTitle(SystemConstants.APP_NAME);
            mainWindow.setMaximized(true);
            mainWindow.setScene(new Scene(root, mainWindow.getMaxWidth(), mainWindow.getMaxHeight()));
            mainWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
