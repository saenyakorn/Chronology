package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.SystemUtils;

public class Main extends Application {

    private final String os = System.getProperty("os.name");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/MainWindow.fxml"));
            SystemUtils.loadStyleSheet(root, "StylingConstants.css");

            // Added loading layout to application
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.getChildren().addAll(root);

            mainWindow.setScene(new Scene(stackPane, mainWindow.getMaxWidth(), mainWindow.getMaxHeight()));
            mainWindow.setTitle(SystemUtils.APP_NAME);
            mainWindow.resizableProperty().setValue(true);
            mainWindow.setMaximized(true);
            mainWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
