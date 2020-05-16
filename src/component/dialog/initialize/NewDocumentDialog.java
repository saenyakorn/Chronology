package component.dialog.initialize;

import component.components.document.Document;
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

public class NewDocumentDialog extends Dialog {
    @FXML
    VBox root;
    @FXML
    TextField docNameTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;

    public NewDocumentDialog() {
        setTitle(SystemUtils.NEW_DOCUMENT);
        loadFXML("NewDocumentDialog.fxml", "../Dialog.css");
    }

    private void addNewDocument(String name) {
        ApplicationUtils.getCurrentWorkspace().addDocument(new Document(name));
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
        docNameTextField.setOnKeyReleased((KeyEvent e) -> disableButtonWhenTextFieldEmpty(createButton, docNameTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(docNameTextField)) {
                addNewDocument(docNameTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

}
