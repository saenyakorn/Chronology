package component.dialog;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Dialog {
    protected Stage stage;

    public Dialog() {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UTILITY);
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
        if (isSomeEmpty(args)) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }

}
