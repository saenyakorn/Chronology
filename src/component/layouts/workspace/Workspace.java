package component.layouts.workspace;

import component.components.document.Document;
import component.components.document.DocumentList;
import component.layouts.sideBar.SideBar;
import component.layouts.viewer.Viewer;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Workspace extends HBox {
    private DocumentList documents;
    private Viewer viewer;
    private SideBar sideBar;

    public Workspace() {
        // Construct components
        this.documents = new DocumentList();
        this.viewer = new Viewer();
        this.sideBar = new SideBar();

        HBox.setHgrow(viewer, Priority.ALWAYS);
        //this.setPrefHeight(USE_COMPUTED_SIZE);

        // Added initial example document
        this.addDocument(new Document("New Document"));
        this.addDocument(new Document("New Document 2"));
        this.addDocument(new Document("New Document 3"));

        // Added all components into HBox
        this.getChildren().addAll(sideBar, viewer);

        // HBox handling event
        this.setOnMouseClicked((MouseEvent event) -> System.out.println("WORKSPACE WAS CLICKED " + documents.getSize()));
    }

    public void addDocument(Document document) {
        System.out.println(document.getText());
        documents.addDocument(document);
        viewer.addDocument(document);
    }

    public void removeDocument(Document document) {
        documents.removeDocument(document);
        viewer.removeDocument(document);
    }
}
