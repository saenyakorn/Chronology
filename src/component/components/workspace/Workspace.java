package component.components.workspace;

import application.ApplicationResource;
import component.components.document.Document;
import component.components.document.DocumentList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Workspace extends TabPane {
    private DocumentList documents;

    public Workspace() {
        this.documents = new DocumentList();
        ApplicationResource.setSelectedTab(this.getSelectionModel());
    }

    public void addDocument(Document document) {
        documents.addDocument(document);
        this.getTabs().add(new Tab(document.getName()));
        //this.getTabs().add(document);
    }
}
