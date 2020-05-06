package component.layouts.workspace;

import ability.Savable;
import ability.SavableAsJSONObject;
import application.ApplicationResource;
import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.components.document.DocumentList;
import component.layouts.sideBar.SideBar;
import component.layouts.viewer.Viewer;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Workspace extends HBox implements SavableAsJSONObject<Workspace> {
    private final DocumentList documents;
    private final Viewer viewer;
    private final SideBar sideBar;
    private final HashMap<String, BasicStoryComponent> hashMap; //contains all basic story components

    public Workspace() {
        // Construct components
        documents = new DocumentList();
        viewer = new Viewer();
        sideBar = new SideBar();
        hashMap = new HashMap<>();

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

    public HashMap<String, BasicStoryComponent> getHashMap() {
        return hashMap;
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

    @Override
    public String getJSONString() {
        return this.writeJSONObject().toJSONString();
    }

    @Override @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject workspaceObject = new JSONObject();
        workspaceObject.put("hashMap", ApplicationResource.getCurrentHashMapAsJSONObject());
        workspaceObject.put("documentList", documents.writeJSONArray());
        return workspaceObject;
    }

    @Override
    public Workspace readJSONObject(JSONObject workspaceObject) {
        Savable.printReadingMessage("workspace");
        JSONObject hashMapObject = (JSONObject) workspaceObject.get("hashMap");
        JSONArray documentArray = (JSONArray) workspaceObject.get("documentList");

        Savable.printReadingMessage("hashMap");
        hashMapObject.forEach((key, value) -> {
            String componentID = (String) key;
            JSONObject componentObject = (JSONObject) value;
            BasicStoryComponent component = BasicStoryComponent.JSONObjectToBasicStoryComponent(componentID,componentObject);
            System.out.println("Populating hashMap - key: " + key + ", type: " + component.getClass().getName());
            hashMap.put(componentID, component.readJSONObject(componentObject));
        });
        Savable.printReadingFinishedMessage("hashMap");

        Savable.printReadingMessage("documentList");
        documents.readJSONArray(documentArray);
        Savable.printReadingFinishedMessage("documentList");
        Savable.printReadingFinishedMessage("workspace");

        return this;
    }

}
