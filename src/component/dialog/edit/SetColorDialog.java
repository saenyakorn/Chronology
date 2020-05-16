package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class SetColorDialog extends Dialog {
    private final BasicStoryComponent component;

    @FXML
    VBox root;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Button setButton;
    @FXML
    Button cancelButton;

    public SetColorDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_COLOR);
        loadFXML("SetColorDialog.fxml", "../Dialog.css");
    }

    @FXML
    public void initialize() {
        colorPicker.setValue(component.getColor());
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        setButton.setOnAction((ActionEvent e) -> {
            component.setColorAndDisplay(colorPicker.getValue());
            ApplicationUtils.updateWorkspace();
            stage.close();
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
