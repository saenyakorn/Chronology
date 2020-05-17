package component.dialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Dialogs are shown to help a user configure components, documents, and projects.
 */
public abstract class Dialog {
    /**
     * OS of system running the app.
     */
    private final String os = System.getProperty("os.name");
    /**
     * Position of dialog.
     */
    protected double x, y;
    /**
     * Stage of this dialog.
     */
    protected Stage stage;

    /**
     * Constructor of dialog.
     */
    public Dialog() {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        if (os != null && os.startsWith("Mac")) {
            stage.initStyle(StageStyle.UNDECORATED);
        }
    }

    /**
     * Loads the FXML of the dialog and sets styles from the links specified.
     * @param FXMLPath path to FXML file.
     * @param CSSPath path to CSS file.
     */
    public void loadFXML(String FXMLPath, String CSSPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(ClassLoader.getSystemResource("component/dialog/" + CSSPath).toExternalForm());
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets stage title.
     * @param title the title to be set.
     */
    public void setTitle(String title) {
        stage.setTitle(title);
    }

    /**
     * Shows the stage.
     */
    public void show() {
        stage.show();
    }

    /**
     * Closes the stage.
     */
    public void close() {
        stage.close();
    }

    /**
     * Checks whether or not all text fields are filled.
     * @param args text fields to be checked.
     * @return whether or not all text fields are filled.
     */
    public boolean isSomeEmpty(TextField... args) {
        boolean isSomeEmpty = false;
        for (TextField textField : args) {
            isSomeEmpty |= textField.getText().trim().isEmpty();
        }
        return isSomeEmpty;
    }

    /**
     * Disables the submit button when all required fields are not yet filled.
     * @param button button to be disabled.
     * @param args text fields to be checked.
     */
    public void disableButtonWhenTextFieldEmpty(Button button, TextField... args) {
        button.setDisable(isSomeEmpty(args));
    }

}
