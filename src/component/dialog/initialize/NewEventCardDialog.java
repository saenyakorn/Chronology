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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class NewEventCardDialog extends Dialog {

    @FXML
    VBox root;
    @FXML
    TextField titleTextField;
    @FXML
    TextField descriptionTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;
    @FXML
    HBox extensionContainer;

    private final ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();
    private final ComboBox<BasicStoryComponent> storylineCombo = new ComboBox<>();

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

    public NewEventCardDialog(BasicStoryComponent component) {
        setTitle(SystemUtils.NEW_EVENT_CARD);
        loadFXML("NewEventCardDialog.fxml", "../Dialog.css");
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField)) {
                addNewEventCard(titleTextField.getText(), descriptionTextField.getText(), component);
            }
        });
    }

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


    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField));
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

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
