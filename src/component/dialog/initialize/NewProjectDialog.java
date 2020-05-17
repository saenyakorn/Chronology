package component.dialog.initialize;

import application.Main;
import component.dialog.Dialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * A dialog called on when creating a new project. App is restart.
 */
public class NewProjectDialog extends Dialog {

    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Create (confirm) button.
     */
    @FXML
    Button createButton;
    /**
     * Cancel (close) button.
     */
    @FXML
    Button cancelButton;

    /**
     * Constructor for NewProjectDialog.
     */
    public NewProjectDialog() {
        setTitle(SystemUtils.NEW_PROJECT);
        loadFXML("NewProjectDialog.fxml", "../Dialog.css");
    }

    /**
     * Creates a new project by closing the window and restarting the app.
     */
    private void createNewProject() {
        System.out.println("Creating a new project");
        Stage primaryStage = (Stage) ApplicationUtils.getCurrentWorkspace().getActiveDocument().getScene().getWindow();
        primaryStage.close();
        Platform.runLater(() -> new Main().start(new Stage()));
        close();
    }

    /**
     * FXML initialize method, called after NewChapterDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Setups create button and cancel button.</li>
     * </ol>
     */
    @FXML
    protected void initialize() {
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        createButton.setOnAction((ActionEvent e) -> {
            createNewProject();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
