package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * A dialog called on a component. Sets the color of the component to the selected color.
 */
public class SetColorDialog extends Dialog {
    /**
     * The component whose color will be set.
     */
    private final BasicStoryComponent component;

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Color picker to input color.
     */
    @FXML
    ColorPicker colorPicker;
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
     * Constructor for SetColorDialog.
     * @param component the component whose color will be set.
     */
    public SetColorDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_COLOR);
        loadFXML("SetColorDialog.fxml", "../Dialog.css");
    }

    /**
     * FXML initialize method, called after SetColorDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Configures color picker.</li>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Setups set button and cancel button.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        colorPicker.setValue(component.getColor());
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        setButton.setOnAction((ActionEvent e) -> {
            component.setColorAndDisplay(colorPicker.getValue());
            ApplicationUtils.updateWorkspace();
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
