package application.layout;

import application.ApplicationResource;
import component.dialog.*;
import component.layouts.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class mainController {

    @FXML
    private VBox root;
    @FXML
    private MenuBar menuBar;
    @FXML
    private HBox navigationBar;
    @FXML
    private Circle closeButton;
    @FXML
    private Circle hideButton;
    @FXML
    private Circle expandButton;


    private final String os = System.getProperty("os.name");

    @FXML
    public void initialize() {
        // new project
        ApplicationResource.newProject();

        // vBox contain workspace
        root.getChildren().add(ApplicationResource.getCurrentWorkspace());
        VBox.setVgrow(root.getChildren().get(root.getChildren().size() - 1), Priority.ALWAYS);

        // setup menu bar property
        //if (os != null && os.startsWith("Mac"))
        //    menuBar.useSystemMenuBarProperty().set(true);
        //Platform.runLater(() -> menuBar.setUseSystemMenuBar(true));
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

    @FXML
    protected void handleCloseWindow(MouseEvent event) {
        Stage mainWindow = (Stage) root.getScene().getWindow();
        mainWindow.close();
    }

    @FXML
    protected void handleHideWindow(MouseEvent event) {
        Stage mainWindow = (Stage) root.getScene().getWindow();
    }

    @FXML
    protected void handleExpandWindow(MouseEvent event) {
        Stage mainWindow = (Stage) root.getScene().getWindow();
        mainWindow.setFullScreen(!mainWindow.isFullScreen());

    }

    @SuppressWarnings("unchecked")
    private void readJSONFile(JSONObject workspaceObject) {
        ApplicationResource.setCurrentWorkspace((new Workspace()).readJSONObject(workspaceObject));
        System.out.println("Open file complete - Current workspace set to loaded workspace");
    }
}
