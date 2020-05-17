package component.dialog.initialize;

import component.components.chapter.Chapter;
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
 * A dialog called on when creating a new chapter. User is required to input a title.
 * A description can also be input, but is optional.
 */
public class NewChapterDialog extends Dialog {

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
     * Constructor for NewChapterDialog.
     */
    public NewChapterDialog() {
        setTitle(SystemUtils.NEW_CHAPTER);
        loadFXML("NewChapterDialog.fxml", "../Dialog.css");
    }

    /**
     * Adds a new chapter to the document.
     * @param title title of chapter.
     * @param description description of chapter.
     */
    private void AddNewChapter(String title, String description) {
        Chapter newChapter = new Chapter(title, description);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addChapter(newChapter);
        ApplicationUtils.updateWorkspace();
        close();
    }

    /**
     * FXML initialize method, called after NewChapterDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Disables create button, and sets it to be enabled when the required field is filled.</li>
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
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                AddNewChapter(titleTextField.getText(), descriptionTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
