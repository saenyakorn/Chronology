package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class SetTitleDialog extends Dialog {
    BasicStoryComponent component;

    @FXML
    TextField textField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;

    public SetTitleDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_TITLE);
        loadFXML("SetTitleDialog.fxml", "../Dialog.css");
    }

    private void setTitleToComponent(String text) {
        component.setTitleAndDisplay(text);
        ApplicationUtils.updateWorkspace();
        close();
    }

    @FXML
    protected void initialize() {
        createButton.setDisable(true);
        textField.setOnKeyReleased((KeyEvent event) -> disableButtonWhenTextFieldEmpty(createButton, textField));
        createButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty(textField)) {
                setTitleToComponent(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}


