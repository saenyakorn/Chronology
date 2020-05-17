package component.dialog.initialize;

import component.base.BasicStoryComponent;
import component.base.BlankBasicStoryComponent;
import component.base.cell.ComboBoxButtonCell;
import component.base.cell.ComboBoxListCell;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * A dialog called on when creating a new event card. User is required to input a title.
 * A description, storyline, and chapter can also be input, but are optional.
 */
public class NewEventCardDialog extends Dialog {

    /**
     * Chapter combo box shown on this dialog.
     */
    private final ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();
    /**
     * Storyline combo box shown on this dialog.
     */
    private final ComboBox<BasicStoryComponent> storylineCombo = new ComboBox<>();

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
     * Container for combo box.
     */
    @FXML
    VBox extensionContainer;

    /**
     * No-arg constructor for NewEventCardDialog. Is initialized from menu bar or sidebar context menu.
     * Create button is set up here, to specifically call the preferred method in this case.
     */
    public NewEventCardDialog() {
        setTitle(SystemUtils.NEW_EVENT_CARD);
        loadFXML("NewEventCardDialog.fxml", "../Dialog.css");
        createChapterComboBox();
        createStorylineComboBox();
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                addNewEventCard(titleTextField.getText(), descriptionTextField.getText());
            }
        });
    }

    /**
     * Constructor for NewEventCardDialog that requires a component. Is initialized from context menu of a storyline or chapter.
     * Create button is set up here, to specifically call the preferred method in this case.
     */
    public NewEventCardDialog(BasicStoryComponent component) {
        setTitle(SystemUtils.NEW_EVENT_CARD);
        loadFXML("NewEventCardDialog.fxml", "../Dialog.css");
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                addNewEventCard(titleTextField.getText(), descriptionTextField.getText(), component);
            }
        });
    }

    /**
     * Adds a new event card to the document, with values according to user input.
     * @param title title of event card.
     * @param description description of event card.
     */
    public void addNewEventCard(String title, String description) {
        EventCard newEventCard = new EventCard(title, description);
        BasicStoryComponent selectedStoryline = storylineCombo.getValue();
        if (selectedStoryline instanceof Storyline) {
            newEventCard.setStoryline((Storyline) selectedStoryline);
        }
        BasicStoryComponent selectedChapter = chapterCombo.getValue();
        if (selectedChapter instanceof Chapter) {
            newEventCard.setChapter((Chapter) selectedChapter);
        }
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        ApplicationUtils.updateWorkspace();
        close();
    }

    /**
     * Adds a new event card to the specified component, with values according to user input.
     * @param title title of event card.
     * @param description description of event card.
     * @param component component that the event card will be added to.
     */
    public void addNewEventCard(String title, String description, BasicStoryComponent component) {
        EventCard newEventCard = new EventCard(title, description);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        if (component instanceof Storyline) {
            newEventCard.setStorylineAndDisplay((Storyline) component);
        } else if (component instanceof Chapter) {
            newEventCard.setChapterAndDisplay((Chapter) component);
        }
        ApplicationUtils.updateWorkspace();
        close();
    }

    /**
     * FXML initialize method, called after NewEventCardDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Disables create button, and sets it to be enabled when the text field is filled.</li>
     *     <li>Setups cancel button.</li>
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
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

    /**
     * Initializes chapter combo box.
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
        Label header = new Label("Select Chapter");
        header.getStyleClass().add("combo-box-header");
        vBox.getChildren().addAll(header, chapterCombo);
        extensionContainer.getChildren().add(vBox);
    }

    /**
     * Initializes storyline combo box.
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
        Label header = new Label("Select Storyline");
        header.getStyleClass().add("combo-box-header");
        vBox.getChildren().addAll(header, storylineCombo);
        extensionContainer.getChildren().add(vBox);
    }
}
