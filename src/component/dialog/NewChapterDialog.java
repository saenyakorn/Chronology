package component.dialog;

import component.components.chapter.Chapter;
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
        loadFXML("Create New Storyline", "NewChapterDialog.fxml");
    }

    private void AddNewChapter(String title, String description) {
        Chapter newChapter = new Chapter(title, description);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addChapter(newChapter);
        ApplicationUtils.updateViewer();
        this.close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField, descriptionTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField, descriptionTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField, descriptionTextField)) {
                AddNewChapter(titleTextField.getText(), descriptionTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
