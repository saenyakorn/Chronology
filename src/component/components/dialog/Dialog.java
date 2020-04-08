package component.components.dialog;

import javafx.stage.Stage;

public abstract class Dialog {
    protected Stage stage;

    public Dialog() {
        stage = new Stage();
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

}
