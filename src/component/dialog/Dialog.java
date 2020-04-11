package component.dialog;

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

}
