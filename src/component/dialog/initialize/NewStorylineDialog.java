package component.dialog.initialize;

import colors.GlobalColor;
import component.components.storyline.Storyline;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * A dialog called on when creating a new storyline. User is required to input a title.
 * A description and color can also be input, but are optional.
 */
public class NewStorylineDialog extends Dialog {

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Text field to input title.
     */
    @FXML
    TextField titleTextField;
    /**
     * Text field to input description.
     */
    @FXML
    TextField descriptionTextField;
    /**
     * Color picker to input color.
     */
    @FXML
    ColorPicker colorPicker;
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
     * Constructor for NewStorylineDialog.
     */
    public NewStorylineDialog() {
        setTitle(SystemUtils.NEW_STORYLINE);
        loadFXML("NewStorylineDialog.fxml", "../Dialog.css");
    }

    /**
     * Adds a new storyline to the document.
     * @param title title of storyline.
     * @param description description of storyline.
     * @param color color of chapter. Default value is red from the default palette.
     */
    private void AddNewStoryline(String title, String description, Color color) {
        Storyline newStoryline = new Storyline(title, description, color, SystemUtils.DEFAULT_TIME_PERIOD);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addStoryLine(newStoryline);
        ApplicationUtils.updateWorkspace();
        this.close();
    }

    /**
     * FXML initialize method, called after NewStorylineDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Configures color picker.</li>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Disables create button, and sets it to be enabled when the required field is filled.</li>
     *     <li>Setups create button and cancel button.</li>
     * </ol>
     */
    @FXML
    protected void initialize() {
        colorPicker.setValue(GlobalColor.RED);
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
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                AddNewStoryline(titleTextField.getText(), descriptionTextField.getText(), colorPicker.getValue());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
