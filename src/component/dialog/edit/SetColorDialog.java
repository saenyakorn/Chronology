package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import utils.SystemUtils;

public class SetColorDialog extends Dialog {
    private final BasicStoryComponent component;

    @FXML  ColorPicker colorPicker;
    @FXML  Button setButton;
    @FXML  Button cancelButton;

    public SetColorDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_COLOR);
        loadFXML("SetColorDialog.fxml", "../Dialog.css");
    }

    @FXML
    public void initialize() {
        colorPicker.setValue(component.getColor());

        setButton.setOnAction((ActionEvent e) -> {
            component.setColorAndDisplay(colorPicker.getValue());
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
