package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.SystemUtils;

/**
 * The main class. Launches the JavaFX application.
 * @see application.layout.MainWindowController
 */
public class Main extends Application {

    /**
     * Main method.
     * @param args arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method. Initializes the main window, then shows the intro screen followed by main window.
     * @param mainWindow the application's main stage.
     * @see application.layout.IntroScreen
     */
    @Override
    public void start(Stage mainWindow) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/MainWindow.fxml"));
            Parent intro = FXMLLoader.load(getClass().getResource("layout/IntroScreen.fxml"));

            // Added loading layout to application
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.getChildren().addAll(root, intro);

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
