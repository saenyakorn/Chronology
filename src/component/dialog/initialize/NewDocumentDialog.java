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

/**
 * A dialog called on when creating a new document. User is required to input a document name.
 */
public class NewDocumentDialog extends Dialog {
    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Text field to input name.
     */
    @FXML
    TextField docNameTextField;
    /**
     * Create (confirm) button.
     */
    @FXML
    Button createButton;
    /**
     * Cancel (close) button.
     */
    @FXML
    Button cancelButton;

    /**
     * Constructor for NewDocumentDialog.
     */
    public NewDocumentDialog() {
        setTitle(SystemUtils.NEW_DOCUMENT);
        loadFXML("NewDocumentDialog.fxml", "Dialog.css");
    }

    /**
     * Adds a new document to the workspace.
     * @param name name of document.
     */
    private void addNewDocument(String name) {
        ApplicationUtils.getCurrentWorkspace().addDocument(new Document(name));
        this.close();
    }

    /**
     * FXML initialize method, called after NewDocumentDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Disables create button, and sets it to be enabled when the text field is filled.</li>
     *     <li>Setups create button and cancel button.</li>
     * </ol>
     */
    @FXML
    protected void initialize() {
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
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
