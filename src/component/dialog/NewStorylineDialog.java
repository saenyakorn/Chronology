package component.dialog;

import application.ApplicationResource;
import application.SystemConstants;
import component.components.document.Document;
import component.components.storyline.Storyline;
import component.layouts.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewStorylineDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Create New Storyline");
            stage.setScene(new Scene(root, SystemConstants.DIALOG_PREF_HEIGHT, SystemConstants.DIALOG_PREF_WIDTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void AddNewStoryline(String title, String description) {
        System.out.println("Creating a new Storyline");
        Workspace currentWorkspace = ApplicationResource.getCurrentWorkspace();
        Document currentDocument = currentWorkspace.getCurrentDocument();
        Storyline newStoryline = new Storyline(title, description);
        currentDocument.addStoryLine(newStoryline);
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
                AddNewStoryline(titleTextField.getText(), descriptionTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
