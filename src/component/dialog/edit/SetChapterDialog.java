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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.ApplicationUtils;

import java.io.IOException;

public class SetChapterDialog extends Dialog {
    private final EventCard component;
    private final ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();


    @FXML  HBox extensionContainer;
    @FXML  Button setButton;
    @FXML  Button cancelButton;

    public SetChapterDialog(EventCard component) {
        this.component = component;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetChapterDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Move to Chapter");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        chapterCombo.getSelectionModel().select(component.getChapter());

        VBox vBox = new VBox();
        Label header = new Label("Select Chapter");
        vBox.getChildren().addAll(header, chapterCombo);
        extensionContainer.getChildren().add(vBox);
    }

    @FXML
    public void initialize() {
        createChapterComboBox();
        setButton.setOnAction((ActionEvent e) -> {
            if(chapterCombo.getValue() instanceof BlankBasicStoryComponent) {
                component.setChapterAndDisplay(null);
            } else {
                component.setChapterAndDisplay((Chapter) chapterCombo.getValue());
            }
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
