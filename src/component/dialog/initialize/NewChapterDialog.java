package component.dialog.initialize;

import component.components.chapter.Chapter;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.ApplicationUtils;

public class NewChapterDialog extends Dialog {

    @FXML
    TextField titleTextField;
    @FXML
    TextField descriptionTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;

    public NewChapterDialog() {
        loadFXML("Create New Storyline", "NewChapterDialog.fxml", "../Dialog.css");
    }

    private void AddNewChapter(String title, String description) {
        Chapter newChapter = new Chapter(title, description);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addChapter(newChapter);
        ApplicationUtils.updateWorkspace();
        this.close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                AddNewChapter(titleTextField.getText(), descriptionTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
