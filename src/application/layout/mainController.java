package application.layout;

import application.ApplicationResource;
import component.components.document.Document;
import component.dialog.NewChapterDialog;
import component.dialog.NewDocumentDialog;
import component.dialog.NewEventCardDialog;
import component.dialog.NewStorylineDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class mainController {

    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBar;

    @FXML
    public void initialize() {
        ApplicationResource.initialize();
        ApplicationResource.getCurrentWorkspace().addDocument(new Document("New Document"));
        vBox.getChildren().add(1, ApplicationResource.getCurrentWorkspace());
        VBox.setVgrow(vBox.getChildren().get(1), Priority.ALWAYS);
    }

    @FXML
    protected void handleNewDocumentClick(ActionEvent event) {
        NewDocumentDialog dialog = new NewDocumentDialog();
        dialog.show();
    }

    @FXML
    protected void handleNewStoryLineClick(ActionEvent event) {
        NewStorylineDialog dialog = new NewStorylineDialog();
        dialog.show();
    }

    @FXML
    protected void handleNewEventCardClick(ActionEvent event) {
        NewEventCardDialog dialog = new NewEventCardDialog();
        dialog.show();
    }

    @FXML
    protected void handleNewChapterClick(ActionEvent event) {
        NewChapterDialog dialog = new NewChapterDialog();
        dialog.show();
    }

    @FXML
    protected void handleSaveClick(ActionEvent event) {

    }

    @FXML
    protected void handleSaveAsClick(ActionEvent event) {

    }

    @FXML
    protected void handleOpenClick(ActionEvent event) {

    }
}
