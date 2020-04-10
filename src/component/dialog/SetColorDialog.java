package component.dialog;

import application.SystemConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SetColorDialog extends Dialog {
    @FXML private ColorPicker colorPicker;
    @FXML private Button setButton;
    @FXML private Button cancelButton;

    public SetColorDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetColorDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Set Color");
            stage.setScene(new Scene(root, SystemConstants.DIALOG_PREF_HEIGHT, SystemConstants.DIALOG_PREF_WIDTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color getColorFromColorPicker(Color color) {
        return color;
    }

    @FXML
    public void initialize() {
        setButton.setOnAction((ActionEvent e) -> getColorFromColorPicker(colorPicker.getValue()));
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
