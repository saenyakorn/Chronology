package component.layouts.workspace;

import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.components.document.DocumentList;
import component.layouts.sideBar.SideBar;
import component.layouts.viewer.Viewer;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.HashMap;

public class Workspace extends HBox {
    private final DocumentList documents;
    private final Viewer viewer;
    private final SideBar sideBar;
    private final HashMap<String, BasicStoryComponent> hashMapBasicStoryComponents;

    public Workspace() {
        // Construct components
        documents = new DocumentList();
        viewer = new Viewer();
        sideBar = new SideBar();
        hashMapBasicStoryComponents = new HashMap<>();

        // Set Style
        HBox.setHgrow(viewer, Priority.ALWAYS);

        // Changing Tab event
        viewer.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observableValue, Tab previousTab, Tab currentTab) -> {
            if (currentTab instanceof Document) {
                Document selectedDocument = (Document) currentTab;
                sideBar.renderSideBar(selectedDocument);
            }
        });

        // Added all components into HBox
        this.getChildren().addAll(sideBar, viewer);
    }

    public HashMap<String, BasicStoryComponent> getHashMapBasicStoryComponents() {
        return hashMapBasicStoryComponents;
    }

    public DocumentList getDocumentList() {
        return documents;
    }

    public Document getActiveDocument() {
        return documents.get(viewer.getSelectionModel().getSelectedIndex());
    }

    public void setActiveDocument(Document document) {
        sideBar.renderSideBar(document);
        viewer.renderViewer(document);
    }

    public void addDocument(Document document) {
        documents.addDocument(document);
        viewer.addDocument(document);
        Document currentDocument = this.getActiveDocument();
        setActiveDocument(currentDocument);
    }

    public void addAllDocument(Document... args) {
        for (Document document : args) {
            documents.addDocument(document);
            viewer.addDocument(document);
        }
        Document currentDocument = this.getActiveDocument();
        setActiveDocument(currentDocument);
    }

    public void removeDocument(Document document) {
        documents.removeDocument(document);
        viewer.removeDocument(document);
    }

    public Viewer getViewer() {
        return viewer;
    }

    @Override
    public String toString() {
        return "Workspace: " + getId();
    }
}
