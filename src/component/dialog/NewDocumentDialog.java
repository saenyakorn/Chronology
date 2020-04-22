package component.dialog;

import application.ApplicationResource;
import component.components.document.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NewDocumentDialog extends Dialog {
    @FXML
    private TextField docNameTextField;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;

    public NewDocumentDialog() {
        loadFXML("Create New Document", "NewDocumentDialog.fxml");
    }

    private void addNewDocument(String name) {
        System.out.println("Creating a new document");
        ApplicationResource.getCurrentWorkspace().addDocument(new Document(name));
        System.out.println("Done");
        this.close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        docNameTextField.setOnKeyReleased((KeyEvent e) -> disableButtonWhenTextFieldEmpty(createButton, docNameTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(docNameTextField)) {
                addNewDocument(docNameTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

}
