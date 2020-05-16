package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.base.BlankBasicStoryComponent;
import component.base.cell.ComboBoxButtonCell;
import component.base.cell.ComboBoxListCell;
import component.components.chapter.Chapter;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.ApplicationUtils;

public class SetStorylineDialog extends Dialog {
    private final EventCard component;
    private final ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();
    private final ComboBox<BasicStoryComponent> storylineCombo = new ComboBox<>();
    @FXML
    HBox extensionContainer;
    @FXML
    Button setButton;
    @FXML
    Button cancelButton;

    public SetStorylineDialog(EventCard component) {
        this.component = component;
        setTitle("Move to Storyline");
        loadFXML("SetStorylineDialog.fxml", "../Dialog.css");
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
        vBox.getChildren().addAll(storylineCombo);
        extensionContainer.getChildren().add(vBox);
    }

    @FXML
    public void initialize() {
        createStorylineComboBox();
        setButton.setOnAction((ActionEvent e) -> {
            if (chapterCombo.getValue() instanceof BlankBasicStoryComponent) {
                component.setChapterAndDisplay(null);
            } else {
                component.setChapterAndDisplay((Chapter) chapterCombo.getValue());
            }
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
