package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.base.BlankBasicStoryComponent;
import component.base.cell.ComboBoxButtonCell;
import component.base.cell.ComboBoxListCell;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;

/**
 * A dialog called on an event card. Sets the storyline of the event card to the selected storyline.
 */
public class SetStorylineDialog extends Dialog {
    /**
     * The event card whose storyline will be set.
     */
    private final EventCard component;
    /**
     * Combo box shown on this dialog.
     */
    private final ComboBox<BasicStoryComponent> storylineCombo = new ComboBox<>();

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Container for combo box.
     */
    @FXML
    VBox extensionContainer;
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
     * Constructor for SetStorylineDialog.
     * @param component the event card whose storyline will be set.
     */
    public SetStorylineDialog(EventCard component) {
        this.component = component;
        setTitle("Move to Storyline");
        loadFXML("SetStorylineDialog.fxml", "../Dialog.css");
    }

    /**
     * Initializes combo box.
     */
    private void createStorylineComboBox() {
        Document document = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        StorylineList storylines = document.getStorylines();
        storylineCombo.getItems().add(new BlankBasicStoryComponent("None", "None"));
        for (Storyline storyline : storylines) {
            storylineCombo.getItems().add(storyline);
        }
        storylineCombo.setCellFactory(params -> new ComboBoxListCell());
        storylineCombo.setButtonCell(new ComboBoxButtonCell());
        storylineCombo.getSelectionModel().selectFirst();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("combo-box-container");
        vBox.getChildren().addAll(storylineCombo);
        extensionContainer.getChildren().add(vBox);
    }

    /**
     * FXML initialize method, called after SetStorylineDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Initializes combo box.</li>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Setups set button and cancel button.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        createStorylineComboBox();
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
            if (storylineCombo.getValue() instanceof BlankBasicStoryComponent) {
                component.setStorylineAndDisplay(null);
            } else {
                component.setStorylineAndDisplay((Storyline) storylineCombo.getValue());
            }
            ApplicationUtils.updateWorkspace();
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
