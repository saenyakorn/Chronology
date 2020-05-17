package component.dialog.edit;

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
 * A dialog called on a document. Sets the description of the document to the inputted name.
 */
public class SetNameDialog extends Dialog {
    /**
     * The document whose name will be set.
     */
    private final Document document;

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Text field to input name.
     */
    @FXML
    TextField textField;
    /**
     * Set (confirm) button.
     */
    @FXML
    Button setButton;
    /**
     * Cancel (close) button.
     */
    @FXML
    Button cancelButton;

    /**
     * Constructor for SetNameDialog.
     * @param document the document whose name will be set.
     */
    public SetNameDialog(Document document) {
        this.document = document;
        setTitle(SystemUtils.EDIT_DESCRIPTION);
        loadFXML("SetNameDialog.fxml", "../Dialog.css");
    }

    /**
     * Sets the name and display of the document.
     * @param text the name to be set.
     */
    private void setName(String text) {
        document.setName(text);
        ApplicationUtils.updateWorkspace();
        close();
    }

    /**
     * FXML initialize method, called after SetNameDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Disables set button, and sets it to be enabled when the text field is filled.</li>
     *     <li>Setups set button and cancel button.</li>
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
        setButton.setDisable(true);
        textField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(setButton, textField));
        setButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(textField)) {
                setName(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
