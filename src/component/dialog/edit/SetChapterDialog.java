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

public class SetChapterDialog extends Dialog {
    private final EventCard component;
    private final ComboBox<BasicStoryComponent> chapterCombo = new ComboBox<>();

    @FXML
    VBox root;
    @FXML
    VBox extensionContainer;
    @FXML
    Button setButton;
    @FXML
    Button cancelButton;

    public SetChapterDialog(EventCard component) {
        this.component = component;
        setTitle("Move to Chapter");
        loadFXML("SetChapterDialog.fxml", "../Dialog.css");
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
        vBox.getChildren().addAll(chapterCombo);
        extensionContainer.getChildren().add(vBox);
    }

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
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
