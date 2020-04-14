package component.layouts.viewer;

import application.ApplicationResource;
import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.components.storyline.Storyline;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class Viewer extends TabPane {

    ScrollPane scrollPane;
    VBox vBox;

    public Viewer() {
        scrollPane = new ScrollPane();
        vBox = new VBox();
        addEventListenderToNode();
    }

    public void addDocument(Document document) {
        this.getTabs().add(document);
    }

    public void removeDocument(Document document) {
        this.getTabs().remove(document);
    }

    private void addEventListenderToNode() {
        this.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        this.setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationResource.getValueFromCurrentWorkspaceHashMap(itemId);
            if (item instanceof Storyline) {
                Storyline storyline = (Storyline) item;
                vBox.getChildren().add(storyline);
                scrollPane.setContent(vBox);
                this.getSelectionModel().getSelectedItem().setContent(scrollPane);
            }
        });
    }
}
