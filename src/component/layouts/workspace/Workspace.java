package component.layouts.workspace;

import component.ability.SavableAsJSONObject;
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

/**
 * The main area of a project. Consists of a sidebar and a viewer.
 */
public class Workspace extends HBox implements SavableAsJSONObject<Workspace> {

    /**
     * List of all documents in this workspace.
     */
    private final DocumentList documents = new DocumentList();
    /**
     * This workspace's viewer.
     */
    private final Viewer viewer = new Viewer();
    /**
     * This workspace's sidebar.
     */
    private final SideBar sideBar = new SideBar();

    /**
     * Constructor for a Workspace. Configures the viewer and sidebar size, and adds them to the scene graph.
     */
    public Workspace() {
        HBox.setHgrow(viewer, Priority.ALWAYS);
        viewer.setMinSize(viewer.getPrefWidth(), viewer.getPrefHeight());
        viewer.setMaxSize(viewer.getPrefWidth(), viewer.getPrefHeight());
        sideBar.setMinSize(sideBar.getPrefWidth(), sideBar.getPrefHeight());
        sideBar.setMaxSize(sideBar.getPrefWidth(), sideBar.getPrefHeight());

        getChildren().addAll(sideBar, viewer);
    }

    /**
     * Getter for viewer.
     * @return this workspace's viewer.
     */
    public Viewer getViewer() {
        return viewer;
    }

    /**
     * Getter for sidebar.
     * @return this workspace's sidebar.
     */
    public SideBar getSideBar() {
        return sideBar;
    }

    /**
     * Getter for documentList.
     * @return this workspace's documentList.
     */
    public DocumentList getDocumentList() {
        return documents;
    }

    /**
     * Gets the currently active document.
     * @return the current active document.
     */
    public Document getActiveDocument() {
        return documents.getActiveDocument();
    }

    /**
     * Sets the active document to another document specified by the document parameter.
     * @param document the document to be set as active.
     */
    public void setActiveDocument(Document document) {
        Document previousActiveDocument = getActiveDocument();
        if (previousActiveDocument != null) {
            sideBar.clear(previousActiveDocument);
        }
        if (document != null) {
            sideBar.initBindings(document);
            documents.setActiveTab(documents.getTabFromDocument(document));
        } else {
            documents.setActiveTab(null);
        }
        viewer.setContent(document);
    }

    /**
     * Adds a document to the workspace's documentList.
     * @param document the document to be added.
     */
    public void addDocument(Document document) {
        documents.addDocument(document);
    }

    /**
     * Removes a document from the workspace's documentList.
     * @param document the document to be removed.
     */
    public void removeDocument(Document document) {
        documents.removeDocument(document);
    }

    /**
     * Overrides toString method
     * @return "Workspace" string
     */
    @Override
    public String toString() {
        return "Workspace";
    }

    /**
     * Gets the workspace in JSON string format.
     * @return the workspace JSONObject converted to a string.
     */
    @Override
    public String getJSONString() {
        return this.writeJSONObject().toJSONString();
    }

    /**
     * Converts workspace to a JSONObject.
     * @return workspace in JSONObject form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject workspaceObject = new JSONObject();
        workspaceObject.put("hashMap", ApplicationUtils.getCurrentHashMapAsJSONObject());
        workspaceObject.put("documentList", documents.writeJSONArray());
        return workspaceObject;
    }

    /**
     * Loads data in the JSONObject into a Workspace.
     * @param workspaceObject the JSONObject that is to be read.
     * @return a Workspace with data loaded from the workspaceObject parameter.
     */
    @Override
    public Workspace readJSONObject(JSONObject workspaceObject) {
        JSONObject hashMapObject = (JSONObject) workspaceObject.get("hashMap");
        JSONArray documentArray = (JSONArray) workspaceObject.get("documentList");

        hashMapObject.forEach((key, value) -> {
            String componentID = (String) key;
            JSONObject componentObject = (JSONObject) value;
            if (!componentObject.get("type").equals("EventCard")) {
                BasicStoryComponent component = BasicStoryComponent.JSONObjectToBasicStoryComponent(componentID, componentObject);
                System.out.println("Populating hashMap - key: " + key + ", type: " + component.getClass().getName());
                ApplicationUtils.putItemToCurrentHashMap(componentID, component.readJSONObject(componentObject));
            }
        });
        hashMapObject.forEach((key, value) -> {
            String componentID = (String) key;
            JSONObject componentObject = (JSONObject) value;
            if (componentObject.get("type").equals("EventCard")) {
                BasicStoryComponent component = BasicStoryComponent.JSONObjectToBasicStoryComponent(componentID, componentObject);
                System.out.println("Populating hashMap - key: " + key + ", type: " + component.getClass().getName());
                ApplicationUtils.putItemToCurrentHashMap(componentID, component.readJSONObject(componentObject));
            }
        });
        documents.readJSONArray(documentArray);
        return this;
    }

}
