package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainWindow) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("layout/mainWindow.fxml"));
            root.getStylesheets().add(getClass().getResource("StylingConstant.css").toExternalForm());
            mainWindow.setTitle(SystemConstants.APP_NAME);
            mainWindow.setScene(new Scene(root, mainWindow.getMaxWidth(), mainWindow.getMaxHeight()));
            mainWindow.initStyle(StageStyle.TRANSPARENT);
            mainWindow.setMaximized(true);
            mainWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
