package component.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.ApplicationUtils;

public class NewProjectDialog extends Dialog {

    @FXML
    TextField nameTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;


    public NewProjectDialog() {
        loadFXML("Create New Storyline", "NewStorylineDialog.fxml");
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
        nameTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, nameTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(nameTextField)) {
                createNewProject();
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
