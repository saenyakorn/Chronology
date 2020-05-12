package application.layout;

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
import utils.ApplicationUtils;
import utils.SystemUtils;

import java.io.*;

public class MainController {

    private final String os = System.getProperty("os.name");
    private final FileChooser fileChooser = new FileChooser();

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
        ApplicationUtils.newProject();

        // vBox contain workspace
        root.getChildren().add(ApplicationUtils.getCurrentWorkspace());
        VBox.setVgrow(root.getChildren().get(root.getChildren().size() - 1), Priority.ALWAYS);

        // setup menu bar property
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);

        // setup file chooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));

        // add tab button setUp
        SVGPath plusIcon = SystemUtils.getIconSVG("plus_icon_24px.svg");
        plusIcon.getStyleClass().add("icon-24px");
        addTabButton.setGraphic(plusIcon);
        addTabButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // hamburger setUp
        SVGPath hamburgerIcon = SystemUtils.getIconSVG("hamburger_icon_24px.svg");
        hamburgerIcon.getStyleClass().add("hamburger-button");
        hamburgerContainer.getChildren().add(hamburgerIcon);

        // binding tabContainer with documentList
        DocumentList documentList = ApplicationUtils.getCurrentWorkspace().getDocumentList();
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
        if(ApplicationUtils.getSavedFile() != null) {
            writeToFile(ApplicationUtils.getSavedFile());
            //TODO : Display "Saved"! somewhere on UI
        } else {
            System.out.println("Save not available!");
        }
        //TODO : Disable this option if savedFile is null
    }

    @FXML
    protected void handleSaveAsClick(ActionEvent event) {
        File selectedFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        ApplicationUtils.setSavedFile(selectedFile);
        writeToFile(selectedFile);
    }

    @FXML
    protected void handleOpenClick(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        ApplicationUtils.setSavedFile(selectedFile);
        readFromFile(selectedFile);
    }

    @FXML
    protected void toggleMenuView() {

    }

    private void writeToFile(File selectedFile) {
        if(selectedFile != null) {
            try {
                FileWriter file = new FileWriter(selectedFile);
                file.write(ApplicationUtils.getCurrentWorkspace().getJSONString());
                file.flush();
                System.out.println("File saved to " + ApplicationUtils.getSavedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readFromFile(File selectedFile) {
        if(selectedFile != null) {
            JSONParser parser = new JSONParser();
            try (FileReader file = new FileReader(selectedFile)) {
                Object obj = parser.parse(file);
                readJSONObject((JSONObject) obj);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void readJSONObject(JSONObject workspaceObject) {
        ApplicationUtils.setCurrentWorkspace((new Workspace()).readJSONObject(workspaceObject));
        System.out.println("Open file complete - Current workspace set to loaded workspace");
    }
}
