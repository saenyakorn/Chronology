package application.layout;

import component.components.document.DocumentList;
import component.dialog.initialize.*;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.ApplicationUtils;
import utils.SystemUtils;

import java.io.*;
import java.util.Optional;

/**
 * Controller class for Main. Handles initialization of the main window's FXML and clicks on the app's menu bar.
 */
public class MainWindowController {

    /**
     * OS of system running the app.
     */
    private final String os = System.getProperty("os.name");
    /**
     * The fileChooser for this window.
     * @see FileChooser
     */
    private final FileChooser fileChooser = new FileChooser();

    /**
     * Root node of the window.
     */
    @FXML
    private VBox root;
    /**
     * Menu bar.
     */
    @FXML
    private MenuBar menuBar;
    /**
     * Document tab bar.
     */
    @FXML
    private HBox tabContainer;
    /**
     * Hamburger button.
     */
    @FXML
    private HBox hamburgerContainer;
    /**
     * Add tab button.
     */
    @FXML
    private Button addTabButton;

    /**
     * FXML initialize method, called after MainWindow.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Creates a default new project.</li>
     *     <li>Adds a VBox containing the workspace to the scene graph.</li>
     *     <li>Setups menu bar property for MacOS.</li>
     *     <li>Setups the file chooser.</li>
     *     <li>Setups the add tab button and hamburger button.</li>
     *     <li>Binds tabContainer HBox with the current DocumentList.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        ApplicationUtils.newProject();

        root.getChildren().add(ApplicationUtils.getCurrentWorkspace());
        VBox.setVgrow(root.getChildren().get(root.getChildren().size() - 1), Priority.ALWAYS);

        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));

        SVGPath plusIcon = SystemUtils.getIconSVG("plus_icon_24px.svg");
        plusIcon.getStyleClass().add("icon-24px");
        addTabButton.setGraphic(plusIcon);
        addTabButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        SVGPath hamburgerIcon = SystemUtils.getIconSVG("hamburger_icon_24px.svg");
        hamburgerIcon.getStyleClass().add("hamburger-button");
        hamburgerContainer.getChildren().add(hamburgerIcon);

        bindTabContainer();
    }

    /**
     * Binds tabContainer HBox with the current DocumentList.
     */
    public void bindTabContainer() {
        DocumentList documentList = ApplicationUtils.getCurrentWorkspace().getDocumentList();
        Bindings.bindContent(tabContainer.getChildren(), documentList.tabsProperty());
    }

    /**
     * Handler for when the new project option on the menu bar is clicked. Shows a NewProjectDialog.
     * @param event the click event.
     * @see NewProjectDialog
     */
    @FXML
    protected void handleNewProjectClick(ActionEvent event) {
        NewProjectDialog dialog = new NewProjectDialog();
        dialog.show();
    }

    /**
     * Handler for when the new document option on the menu bar is clicked. Shows a NewDocumentDialog.
     * @param event the click event.
     * @see NewDocumentDialog
     */
    @FXML
    protected void handleNewDocumentClick(ActionEvent event) {
        NewDocumentDialog dialog = new NewDocumentDialog();
        dialog.show();
    }

    /**
     * Handler for when the new storyline option on the menu bar is clicked. Shows a NewStorylineDialog.
     * @param event the click event.
     * @see NewStorylineDialog
     */
    @FXML
    protected void handleNewStoryLineClick(ActionEvent event) {
        NewStorylineDialog dialog = new NewStorylineDialog();
        dialog.show();
    }

    /**
     * Handler for when the new event card option on the menu bar is clicked. Shows a NewEventCardDialog.
     * @param event the click event.
     * @see NewEventCardDialog
     */
    @FXML
    protected void handleNewEventCardClick(ActionEvent event) {
        NewEventCardDialog dialog = new NewEventCardDialog();
        dialog.show();
    }

    /**
     * Handler for when the new chapter option on the menu bar is clicked. Shows a NewChapterDialog.
     * @param event the click event.
     * @see NewChapterDialog
     */
    @FXML
    protected void handleNewChapterClick(ActionEvent event) {
        NewChapterDialog dialog = new NewChapterDialog();
        dialog.show();
    }

    /**
     * Handler for when the save option on the menu bar is clicked. If file already has a saved directory, the save data will be overwritten. Otherwise an alert will appear.
     * @param event the click event.
     * @see MainWindowController#writeToFile(File)
     */
    @FXML
    protected void handleSaveClick(ActionEvent event) {
        if(ApplicationUtils.getSavedFile() != null) {
            writeToFile(ApplicationUtils.getSavedFile());
        } else {
            File selectedFile = fileChooser.showSaveDialog(root.getScene().getWindow());
            ApplicationUtils.setSavedFile(selectedFile);
            writeToFile(selectedFile);
        }
    }

    /**
     * Handler for when the save as option on the menu bar is clicked. Opens up a fileChooser so the user can select a save directory before saving.
     * @param event the click event.
     * @see MainWindowController#writeToFile(File)
     */
    @FXML
    protected void handleSaveAsClick(ActionEvent event) {
        File selectedFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        ApplicationUtils.setSavedFile(selectedFile);
        writeToFile(selectedFile);
    }

    /**
     * Handler for when the open option on the menu bar is clicked. Opens up a fileChooser so the user can select a directory to open a file from.
     * @param event the click event.
     * @see MainWindowController#readFromFile(File)
     */
    @FXML
    protected void handleOpenClick(ActionEvent event) {
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        ApplicationUtils.setSavedFile(selectedFile);
        readFromFile(selectedFile);
    }

    /**
     * Handler for when the hamburger button is clicked.
     */
    @FXML
    protected void toggleMenuView() {

    }

    /**
     * Writes the necessary workspace data into a .json file.
     * @param selectedFile file selected from fileChooser by user.
     * @see component.ability.Savable
     */
    private void writeToFile(File selectedFile) {
        if(selectedFile != null) {
            try {
                FileWriter file = new FileWriter(selectedFile);
                file.write(ApplicationUtils.getCurrentWorkspace().getJSONString());
                file.flush();
                showSavedAlert();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prepares the workspace for file opening, then reads data from File specified by the selectedFile parameter. Type of file must be .json.
     * The whole process is as follows:
     * <ol>
     *     <li>Clears the previous workspace and creates a new one.</li>
     *     <li>Binds tabContainer HBox to this new workspace's documentList.</li>
     *     <li>Reads the file, loads data into workspace.</li>
     *     <li>Sets the first document as active.</li>
     *     <li>Adds loaded workspace to root.</li>
     * </ol>
     * @param selectedFile file selected from fileChooser by user.
     * @see component.ability.Savable
     * @see MainWindowController#readJSONObject(JSONObject)
     */
    public void readFromFile(File selectedFile) {
        if (selectedFile != null) {
            ApplicationUtils.clear();
            bindTabContainer();

            JSONParser parser = new JSONParser();
            try (FileReader file = new FileReader(selectedFile)) {
                Object obj = parser.parse(file);
                readJSONObject((JSONObject) obj);

                ApplicationUtils.setFirstDocumentAsActive();
                root.getChildren().remove(root.getChildren().size() - 1);
                root.getChildren().add(ApplicationUtils.getCurrentWorkspace());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses data in the workspaceObject parameter into the current Workspace.
     *
     * @param workspaceObject JSONObject of the workspace
     */
    @SuppressWarnings("unchecked")
    private void readJSONObject(JSONObject workspaceObject) {
        ApplicationUtils.getCurrentWorkspace().readJSONObject(workspaceObject);
        System.out.println("Open file complete - Current workspace set to loaded workspace");
    }

    /**
     * Shows an information alert that the file has been saved.
     */
    private void showSavedAlert() {
        Alert saved = SystemUtils.getCustomInformationAlert(SystemUtils.SAVED_TITLE, SystemUtils.SAVED_HEADER, SystemUtils.SAVED_CONTENT + " " + ApplicationUtils.getSavedFile().toString());
        saved.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = saved.showAndWait();
        if (result.get() == ButtonType.OK) {
            saved.close();
        } else {
            saved.close();
        }
    }

}
