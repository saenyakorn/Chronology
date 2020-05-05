package application.layout;

import application.ApplicationResource;
import component.dialog.*;
import component.layouts.workspace.Workspace;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
            //FileWriter file = new FileWriter(ApplicationResource.getSavedFile()); //savedFile is currently null!
            FileWriter file = new FileWriter("output.json");
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
        JSONParser parser = new JSONParser();
        try (FileReader file = new FileReader("JSONoutput/output_saved.json")) {
            Object obj = parser.parse(file);
            readJSONFile((JSONObject) obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void readJSONFile(JSONObject workspaceObject) {
        ApplicationResource.setCurrentWorkspace((new Workspace()).readJSONObject(workspaceObject));
    }
}
