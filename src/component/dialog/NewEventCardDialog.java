package component.dialog;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import utils.ApplicationUtils;

public class NewEventCardDialog extends Dialog {

    @FXML
    TextField titleTextField;
    @FXML
    TextField descriptionTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;
    @FXML
    HBox comboContainer;

    public NewEventCardDialog() {
        loadFXML("Create New Event", "NewEventCardDialog.fxml");
    }

    public void addNewEventCard(String title, String description) {
        EventCard newEventCard = new EventCard(title, description);
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        ApplicationUtils.updateWorkspace();
        this.close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        titleTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField, descriptionTextField));
        descriptionTextField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, titleTextField, descriptionTextField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(titleTextField, descriptionTextField)) {
                addNewEventCard(titleTextField.getText(), descriptionTextField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
        createChapterComboBox();
        createStorylineComboBox();
    }

    private void createChapterComboBox() {
        ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();
        Document document = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ChapterList chapters = document.getChapters();
        chapterCombo.getItems().add(new BlankBasicStoryComponent("None", "None"));
        for (Chapter chapter : chapters) {
            chapterCombo.getItems().add(chapter);
        }
        chapterCombo.setCellFactory(params -> new ComboBoxListCell());
        chapterCombo.setButtonCell(new ComboBoxButtonCell());
        chapterCombo.getSelectionModel().selectFirst();
        comboContainer.getChildren().add(chapterCombo);
    }

    private void createStorylineComboBox() {
        ComboBox<BasicStoryComponent> storylineCombo = new ComboBox<>();
        Document document = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        StorylineList storylines = document.getStorylines();
        storylineCombo.getItems().add(new BlankBasicStoryComponent("None", "None"));
        for (Storyline storyline : storylines) {
            storylineCombo.getItems().add(storyline);
        }
        storylineCombo.setCellFactory(params -> new ComboBoxListCell());
        storylineCombo.setButtonCell(new ComboBoxButtonCell());
        storylineCombo.getSelectionModel().selectFirst();
        comboContainer.getChildren().add(storylineCombo);
    }
}
