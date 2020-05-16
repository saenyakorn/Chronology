package component.dialog.edit;

import component.components.document.Document;
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

public class SetNameDialog extends Dialog {
    private final Document document;

    @FXML
    VBox root;
    @FXML
    TextField textField;
    @FXML
    Button setButton;
    @FXML
    Button cancelButton;

    public SetNameDialog(Document document) {
        this.document = document;
        setTitle(SystemUtils.EDIT_DESCRIPTION);
        loadFXML("SetNameDialog.fxml", "../Dialog.css");
    }

    private void setName(String text) {
        document.setName(text);
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
                setName(textField.getText());
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
