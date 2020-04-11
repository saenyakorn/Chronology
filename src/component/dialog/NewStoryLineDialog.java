package component.dialog;

import application.ApplicationResource;
import component.components.document.Document;
import component.components.storyline.Storyline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class NewStoryLineDialog extends Dialog {

    public NewStoryLineDialog() {
        stage.setTitle("Create a new story line");
        Button button = new Button("Create a new story line");
        TextField titleTextField = new TextField();
        TextField descriptionTextField = new TextField();
        VBox vBox = new VBox();

        button.setOnAction((ActionEvent e) -> {
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            AddNewStoryLine(title, description);
        });

        vBox.getChildren().add(new Label("Title"));
        vBox.getChildren().add(titleTextField);
        vBox.getChildren().add(new Label("dexcription"));
        vBox.getChildren().add(descriptionTextField);
        vBox.getChildren().add(button);
        stage.setScene(new Scene(vBox, 300, 400));
    }

    private void AddNewStoryLine(String name, String description) {
        System.out.println("Creating a new story line");
        Document currentDocument = ApplicationResource.getCurrentWorkspace().getCurrentDocument();
        currentDocument.addStoryLine(new Storyline(name, description));
        System.out.println("Done");
        this.close();
    }
}
