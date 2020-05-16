package component.dialog.initialize;

import application.Main;
import component.dialog.Dialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class NewProjectDialog extends Dialog {

    @FXML
    VBox root;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;


    public NewProjectDialog() {
        setTitle(SystemUtils.NEW_PROJECT);
        loadFXML("NewProjectDialog.fxml", "../Dialog.css");
    }

    private void createNewProject() {
        System.out.println("Creating a new project");
        Stage primaryStage = (Stage) ApplicationUtils.getCurrentWorkspace().getActiveDocument().getScene().getWindow();
        primaryStage.close();
        Platform.runLater(() -> new Main().start(new Stage()));
        close();
    }

    @FXML
    protected void initialize() {
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        createButton.setOnAction((ActionEvent e) -> {
            createNewProject();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
