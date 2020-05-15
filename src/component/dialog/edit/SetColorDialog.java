package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

import java.io.IOException;

public class SetColorDialog extends Dialog {
    private final BasicStoryComponent component;

    @FXML private ColorPicker colorPicker;
    @FXML private Button setButton;
    @FXML private Button cancelButton;

    public SetColorDialog(BasicStoryComponent component) {
        this.component = component;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetColorDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Set Color");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        colorPicker.setValue(component.getColor());
        System.out.println("Setting color of " + component.toString());

        setButton.setOnAction((ActionEvent e) -> {
            component.setColor(colorPicker.getValue());
            System.out.println("Color picked is " + colorPicker.getValue().toString());
            System.out.println("Color set to " + component.getColor().toString());
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
