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

public abstract class Dialog {
    protected double x, y;
    protected Stage stage;
    private final String os = System.getProperty("os.name");

    public Dialog() {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        if (os != null && os.startsWith("Mac")) {
            stage.initStyle(StageStyle.UNDECORATED);
        }
    }

    public void loadFXML(String FXMLPath, String CSSPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource(CSSPath).toExternalForm());
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        stage.setTitle(title);
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
