package component.dialog.edit;

import component.base.BasicStoryComponent;
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
 * A dialog called on a component. Sets the description of the component to the inputted description.
 */
public class SetDescriptionDialog extends Dialog {
    /**
     * The component whose description will be set.
     */
    private final BasicStoryComponent component;

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Text field to input description.
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
     * Constructor for SetDescriptionDialog.
     * @param component the component whose description will be set.
     */
    public SetDescriptionDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_DESCRIPTION);
        loadFXML("SetDescriptionDialog.fxml", "Dialog.css");
    }

    /**
     * Sets the description and display of the component.
     * @param text the description to be set.
     */
    private void setString(String text) {
        component.setDescriptionAndDisplay(text);
        ApplicationUtils.updateWorkspace();
        close();
    }

    /**
     * FXML initialize method, called after SetDescriptionDialog.fxml finishes loading.
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
                setString(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
