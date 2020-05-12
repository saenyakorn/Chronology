package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private final String os = System.getProperty("os.name");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/MainWindow.fxml"));
            root.getStylesheets().add(getClass().getResource("StylingConstant.css").toExternalForm());
            ApplicationResource.setMainWindow(mainWindow);
            Scene scene = new Scene(root, mainWindow.getMaxWidth(), mainWindow.getMaxHeight());
            mainWindow.setScene(scene);
            root.getStylesheets().add(getClass().getResource("StylingConstants.css").toExternalForm());
            mainWindow.setTitle(SystemConstants.APP_NAME);
            mainWindow.resizableProperty().setValue(true);
            mainWindow.setMaximized(true);
            mainWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
