package component.components.dialog;

import application.ApplicationResource;
import component.components.document.Document;
import component.components.document.DocumentList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NewDocumentDialog extends Dialog {

    public NewDocumentDialog() {
        Button button = new Button("Create new document");
        TextField textField = new TextField();
        VBox vBox = new VBox();

        button.setOnAction((ActionEvent e) -> {
            String input = textField.getText();
            AddNewDocument(input);
        });


        vBox.getChildren().add(textField);
        vBox.getChildren().add(button);
        stage.setScene(new Scene(vBox, 300, 400));
    }

    public void AddNewDocument(String name) {
        System.out.println("Creating a new document");
        DocumentList temp = ApplicationResource.getDocumentList();
        temp.addDocument(new Document(name));
        ApplicationResource.setDocumentList(temp);
    }
}
