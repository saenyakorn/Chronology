package application.layout;

import application.ApplicationResource;
import component.components.document.DocumentList;
import component.dialog.*;
import component.layouts.workspace.Workspace;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class MainController {

    private final String os = System.getProperty("os.name");

    @FXML
    private VBox root;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private HBox tabContainer;
    @FXML
    private HBox hamburgerContainer;
    @FXML
    private Button addTabButton;

    @FXML
    public void initialize() {
        // new project
        ApplicationResource.newProject();

        // vBox contain workspace
        root.getChildren().add(ApplicationResource.getCurrentWorkspace());
        VBox.setVgrow(root.getChildren().get(root.getChildren().size() - 1), Priority.ALWAYS);

        // setup menu bar property
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);

        // add tab button setUp
        SVGPath plusIcon = ApplicationResource.getIconSVG("plus_icon_24px.svg");
        plusIcon.getStyleClass().add("icon-24px");
        addTabButton.setGraphic(plusIcon);
        addTabButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // hamburger setUp
        SVGPath hamburgerIcon = ApplicationResource.getIconSVG("hamburger_icon_24px.svg");
        hamburgerIcon.getStyleClass().add("hamburger-button");
        hamburgerContainer.getChildren().add(hamburgerIcon);

        // binding tabContainer with documentList
        DocumentList documentList = ApplicationResource.getCurrentWorkspace().getDocumentList();
        Bindings.bindContent(tabContainer.getChildren(), documentList.getDocumentCustomTabs());

        // TODO Rewrite this class more clearly. it work but hard for debugging.
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
        if(ApplicationResource.getSavedFile() != null) {
            writeJSONFile(ApplicationResource.getSavedFile());
            System.out.println("[Save] File saved to " + ApplicationResource.getSavedFile());
            //TODO : Display "Saved"! somewhere on UI
        } else {
            System.out.println("Save not available!");
        }
        //TODO : Disable this option if savedFile is null
    }

    @FXML
    protected void handleSaveAsClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(ApplicationResource.getMainWindow());
        ApplicationResource.setSavedFile(selectedFile);
        writeJSONFile(selectedFile);
        System.out.println("[Save As] File saved to " + selectedFile);
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
    protected void toggleMenuView() {

    }

    private void writeJSONFile(File selectedFile) {
        try {
            FileWriter file = new FileWriter(selectedFile);
            file.write(ApplicationResource.getCurrentWorkspace().getJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void readJSONFile(JSONObject workspaceObject) {
        ApplicationResource.setCurrentWorkspace((new Workspace()).readJSONObject(workspaceObject));
        System.out.println("Open file complete - Current workspace set to loaded workspace");
    }
}
