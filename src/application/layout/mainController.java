package application.layout;

import component.components.dialog.NewDocumentDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class mainController {

    @FXML
    protected void handleNewDocumentClick(ActionEvent event) {
        NewDocumentDialog dialog = new NewDocumentDialog();
        dialog.show();
    }
}
