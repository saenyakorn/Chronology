package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.base.BlankBasicStoryComponent;
import component.base.cell.ComboBoxButtonCell;
import component.base.cell.ComboBoxListCell;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.document.Document;
import component.components.eventCard.EventCard;
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
 * A dialog called on an event card. Sets the chapter of the event card to the selected chapter.
 */
public class SetChapterDialog extends Dialog {
    /**
     * The event card whose chapter will be set.
     */
    private final EventCard component;
    /**
     * Combo box shown on this dialog.
     */
    private final ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();

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
     * Constructor for SetChapterDialog.
     * @param component the event card whose chapter will be set.
     */
    public SetChapterDialog(EventCard component) {
        this.component = component;
        setTitle("Move to Chapter");
        loadFXML("SetChapterDialog.fxml", "Dialog.css");
    }

    /**
     * Initializes combo box.
     */
    private void createChapterComboBox() {
        Document document = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ChapterList chapters = document.getChapters();
        chapterCombo.getItems().add(new BlankBasicStoryComponent("None", "None"));
        for (Chapter chapter : chapters) {
            chapterCombo.getItems().add(chapter);
        }
        chapterCombo.setCellFactory(params -> new ComboBoxListCell());
        chapterCombo.setButtonCell(new ComboBoxButtonCell());
        chapterCombo.getSelectionModel().selectFirst();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("combo-box-container");
        vBox.getChildren().addAll(chapterCombo);
        extensionContainer.getChildren().add(vBox);
    }

    /**
     * FXML initialize method, called after SetChapterDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Initializes combo box.</li>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Setups set button and cancel button.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        createChapterComboBox();
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
            if (chapterCombo.getValue() instanceof BlankBasicStoryComponent) {
                component.setChapterAndDisplay(null);
            } else {
                component.setChapterAndDisplay((Chapter) chapterCombo.getValue());
            }
            ApplicationUtils.updateWorkspace();
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
