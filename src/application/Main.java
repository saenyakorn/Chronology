package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private final String os = System.getProperty("os.name");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/MainWindow.fxml"));
            root.getStylesheets().add(getClass().getResource("StylingConstants.css").toExternalForm());
            mainWindow.setTitle(SystemConstants.APP_NAME);
            mainWindow.setScene(new Scene(root, mainWindow.getMaxWidth(), mainWindow.getMaxHeight()));
            if (os != null && os.startsWith("Windows")) {
                mainWindow.initStyle(StageStyle.TRANSPARENT);
            } else {
                mainWindow.initStyle(StageStyle.UNDECORATED);
            }
            mainWindow.resizableProperty().setValue(true);
            mainWindow.setMaximized(true);
            mainWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
