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
import component.layouts.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewEventCardDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Create New Event");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewEventCard(String title, String description) {
        System.out.println("Creating a new EventCard");
        Workspace currentWorkspace = ApplicationResource.getCurrentWorkspace();
        Document currentDocument = currentWorkspace.getCurrentDocument();
        EventCard newEventCard = new EventCard(title, description);
        currentDocument.addEventCard(newEventCard);
        currentWorkspace.setActiveDocument(currentDocument);
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
        Document document = ApplicationResource.getCurrentWorkspace().getCurrentDocument();
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
        Document document = ApplicationResource.getCurrentWorkspace().getCurrentDocument();
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
