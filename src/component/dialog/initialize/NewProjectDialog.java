package component.dialog.initialize;

import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class NewProjectDialog extends Dialog {

    @FXML
    VBox root;
    @FXML
    TextField nameTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;


    public NewProjectDialog() {
        setTitle(SystemUtils.NEW_PROJECT);
        loadFXML("NewStorylineDialog.fxml", "../Dialog.css");
    }

    private void createNewProject() {
        System.out.println("Creating a new project");
        ApplicationUtils.newProject();
        System.out.println("Done");
        this.close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        nameTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, nameTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(nameTextField)) {
                createNewProject();
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
