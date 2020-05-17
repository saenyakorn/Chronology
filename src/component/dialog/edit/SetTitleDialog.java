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
 * A dialog called on a component. Sets the title of the component to the inputted title.
 */
public class SetTitleDialog extends Dialog {
    /**
     * The component whose title will be set.
     */
    private final BasicStoryComponent component;

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Text field to input title.
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
     * Constructor for SetTitleDialog.
     * @param component the component whose title will be set.
     */
    public SetTitleDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_TITLE);
        loadFXML("SetTitleDialog.fxml", "../Dialog.css");
    }

    /**
     * Sets the title and display of the component.
     * @param text the title to be set.
     */
    private void setTitleToComponent(String text) {
        component.setTitleAndDisplay(text);
        ApplicationUtils.updateWorkspace();
        close();
    }

    /**
     * FXML initialize method, called after SetTitleDialog.fxml finishes loading.
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
                setTitleToComponent(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}


