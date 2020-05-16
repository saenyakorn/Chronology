package component.dialog.initialize;

import component.components.storyline.Storyline;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class NewStorylineDialog extends Dialog {

    @FXML
    TextField titleTextField;
    @FXML
    TextField descriptionTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;

    public NewStorylineDialog() {
        setTitle(SystemUtils.NEW_STORYLINE);
        loadFXML("NewStorylineDialog.fxml", "../Dialog.css");
    }

    private void AddNewStoryline(String title, String description) {
        System.out.println("Creating a new Storyline");
        Storyline newStoryline = new Storyline(title, description);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addStoryLine(newStoryline);
        ApplicationUtils.updateWorkspace();
        System.out.println("Done");
        this.close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                AddNewStoryline(titleTextField.getText(), descriptionTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
