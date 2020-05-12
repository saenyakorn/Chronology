package component.layouts.workspace;

import ability.Savable;
import ability.SavableAsJSONObject;
import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.components.document.DocumentList;
import component.layouts.sideBar.SideBar;
import component.layouts.viewer.Viewer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

public class Workspace extends HBox implements SavableAsJSONObject<Workspace> {
    private final DocumentList documents = new DocumentList();
    private final Viewer viewer = new Viewer();
    private final SideBar sideBar = new SideBar();

    public Workspace() {
        // Set Style
        HBox.setHgrow(viewer, Priority.ALWAYS);
        viewer.setMinSize(viewer.getPrefWidth(), viewer.getPrefHeight());
        viewer.setMaxSize(viewer.getPrefWidth(), viewer.getPrefHeight());
        sideBar.setMinSize(sideBar.getPrefWidth(), sideBar.getPrefHeight());
        sideBar.setMaxSize(sideBar.getPrefWidth(), sideBar.getPrefHeight());

        // Added all components into HBox
        this.getChildren().addAll(sideBar, viewer);
    }

    public Viewer getViewer() {
        return viewer;
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public DocumentList getDocumentList() {
        return documents;
    }

    public Document getActiveDocument() {
        return documents.getActiveDocument();
    }

    public void setActiveDocument(Document document) {
        sideBar.renderSideBar(document);
    }

    public void addDocument(Document document) {
        documents.addDocument(document);
    }

    public void addAllDocument(Document... args) {
        for (Document document : args) {
            documents.addDocument(document);
        }
        Document currentDocument = this.getActiveDocument();
        setActiveDocument(currentDocument);
    }

    public void removeDocument(Document document) {
        documents.removeDocument(document);
    }

    @Override
    public String toString() {
        return "Workspace: " + getId();
    }

    @Override
    public String getJSONString() {
        return this.writeJSONObject().toJSONString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject workspaceObject = new JSONObject();
        workspaceObject.put("hashMap", ApplicationUtils.getCurrentHashMapAsJSONObject());
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
            BasicStoryComponent component = BasicStoryComponent.JSONObjectToBasicStoryComponent(componentID, componentObject);
            System.out.println("Populating hashMap - key: " + key + ", type: " + component.getClass().getName());
            ApplicationUtils.putItemToCurrentHashMap(componentID, component.readJSONObject(componentObject));
        });
        Savable.printReadingFinishedMessage("hashMap");

        Savable.printReadingMessage("documentList");
        documents.readJSONArray(documentArray);
        Savable.printReadingFinishedMessage("documentList");
        Savable.printReadingFinishedMessage("workspace");

        return this;
    }

}
