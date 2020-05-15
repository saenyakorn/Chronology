package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.ApplicationUtils;

public class SetDescriptionDialog extends Dialog {

    BasicStoryComponent component;

    @FXML
    TextField textField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;

    public SetDescriptionDialog(BasicStoryComponent component) {
        this.component = component;
        loadFXML("Edit description", "SetDescriptionDialog.fxml", "../Dialog.css");
    }

    private void SetString(String text) {
        component.setDescription(text);
        ApplicationUtils.updateWorkspace();
        close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        textField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, textField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(textField)) {
                SetString(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
