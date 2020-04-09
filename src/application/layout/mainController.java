package application.layout;

import application.ApplicationResource;
import component.components.dialog.NewChapterDialog;
import component.components.dialog.NewDocumentDialog;
import component.components.dialog.NewEventCardDialog;
import component.components.dialog.NewStoryLineDialog;
import component.components.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class mainController {

    @FXML
    private Workspace workspace;

    @FXML
    public void initialize() {
        ApplicationResource.setCurrentWorkspace(workspace);
    }

    @FXML
    protected void handleNewDocumentClick(ActionEvent event) {
        NewDocumentDialog dialog = new NewDocumentDialog();
        dialog.show();
    }

    @FXML
    protected void handleNewStoryLineClick(ActionEvent event) {
        NewStoryLineDialog dialog = new NewStoryLineDialog();
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

}
