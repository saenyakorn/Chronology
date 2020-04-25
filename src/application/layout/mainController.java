package application.layout;

import application.ApplicationResource;
import component.dialog.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.io.IOException;

public class mainController {

    @FXML
    private VBox vBox;
    @FXML
    private MenuBar menuBar;

    private final String os = System.getProperty("os.name");

    @FXML
    public void initialize() {
        // new project
        ApplicationResource.newProject();

        // vBox contain workspace
        vBox.getChildren().add(1, ApplicationResource.getCurrentWorkspace());
        VBox.setVgrow(vBox.getChildren().get(1), Priority.ALWAYS);

        // menu bar property
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);
        Platform.runLater(() -> menuBar.setUseSystemMenuBar(true));
    }

    @FXML
    protected void handleNewProjectClick(ActionEvent event) {
        NewProjectDialog dialog = new NewProjectDialog();
        dialog.show();
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
        try {
            FileWriter file = new FileWriter(ApplicationResource.getSavedFile()); //savedFile is currently null!
            file.write(ApplicationResource.getCurrentWorkspace().getJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleSaveAsClick(ActionEvent event) {

    }

    @FXML
    protected void handleOpenClick(ActionEvent event) {

    }
}
