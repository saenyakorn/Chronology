package component.dialog;

import application.SystemConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class Dialog {
    protected Stage stage;

    public Dialog() {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
    }

    public void loadFXML(String title, String path) {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("Dialog.css").toExternalForm());
            stage.setTitle(title);
            stage.setScene(new Scene(root, SystemConstants.DIALOG_PREF_HEIGHT, SystemConstants.DIALOG_PREF_WIDTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public boolean isSomeEmpty(TextField... args) {
        boolean isSomeEmpty = false;
        for (TextField textField : args) {
            isSomeEmpty |= textField.getText().trim().isEmpty();
        }
        return isSomeEmpty;
    }

    public void disableButtonWhenTextFieldEmpty(Button button, TextField... args) {
        button.setDisable(isSomeEmpty(args));
    }

}
