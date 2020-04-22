package component.dialog;

import application.ApplicationResource;
import component.components.chapter.Chapter;
import component.components.document.Document;
import component.layouts.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
        System.out.println("Creating a new Chapter");
        Workspace currentWorkspace = ApplicationResource.getCurrentWorkspace();
        Document currentDocument = currentWorkspace.getCurrentDocument();
        Chapter newChapter = new Chapter(title, description);
        currentDocument.addChapter(newChapter);
        currentWorkspace.setActiveDocument(currentDocument);
        System.out.println("Done");
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
