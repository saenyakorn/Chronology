package component.dialog;

import application.ApplicationResource;
import component.base.BasicStoryComponent;
import component.base.ComboBoxButtonCell;
import component.base.ComboBoxListCell;
import component.base.OnlyBodyBasicStoryComponents;
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
        System.out.println("Creating a new EventCard");
        EventCard newEventCard = new EventCard(title, description);
        ApplicationResource.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        ApplicationResource.update();
        System.out.println("Done");
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
        Document document = ApplicationResource.getCurrentWorkspace().getActiveDocument();
        ChapterList chapters = document.getChapterList();
        chapterCombo.getItems().add(new OnlyBodyBasicStoryComponents("None", "None"));
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
        Document document = ApplicationResource.getCurrentWorkspace().getActiveDocument();
        StorylineList storylines = document.getStorylineList();
        storylineCombo.getItems().add(new OnlyBodyBasicStoryComponents("None", "None"));
        for (Storyline storyline : storylines) {
            storylineCombo.getItems().add(storyline);
        }
        storylineCombo.setCellFactory(params -> new ComboBoxListCell());
        storylineCombo.setButtonCell(new ComboBoxButtonCell());
        storylineCombo.getSelectionModel().selectFirst();
        comboContainer.getChildren().add(storylineCombo);
    }
}
