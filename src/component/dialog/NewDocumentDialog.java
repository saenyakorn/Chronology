package component.dialog;

import application.ApplicationResource;
import application.SystemConstants;
import component.components.document.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class NewDocumentDialog extends Dialog {
    @FXML private TextField textField;
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    public NewDocumentDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewDocumentDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Create New Document");
            stage.setScene(new Scene(root, SystemConstants.DIALOG_PREF_HEIGHT, SystemConstants.DIALOG_PREF_WIDTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddNewDocument(String name) {
        System.out.println("Creating a new document");
        ApplicationResource.getCurrentWorkspace().addDocument(new Document(name));
        System.out.println("Done");
        this.close();
    }

    @FXML
    public void initialize() {
        createButton.setDisable(true);
        textField.setOnKeyReleased((KeyEvent e) -> {
            String input = textField.getText();
            if((input.isEmpty()) || (input.trim().isEmpty()))
                createButton.setDisable(true);
            else
                createButton.setDisable(false);
        });
        createButton.setOnAction((ActionEvent e) -> {
            String input = textField.getText();
            if( !( (input.isEmpty()) || (input.trim().isEmpty()) ) )
                AddNewDocument(textField.getText());
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

}
