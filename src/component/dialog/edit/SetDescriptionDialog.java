package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class SetDescriptionDialog extends Dialog {
    private final BasicStoryComponent component;

    @FXML
    VBox root;
    @FXML
    TextField textField;
    @FXML
    Button setButton;
    @FXML
    Button cancelButton;

    public SetDescriptionDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_DESCRIPTION);
        loadFXML("SetDescriptionDialog.fxml", "../Dialog.css");
    }

    private void setString(String text) {
        component.setDescriptionAndDisplay(text);
        ApplicationUtils.updateWorkspace();
        close();
    }

    @FXML
    protected void initialize() {
        setButton.setDisable(true);
        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        textField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(setButton, textField));
        setButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(textField)) {
                setString(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
