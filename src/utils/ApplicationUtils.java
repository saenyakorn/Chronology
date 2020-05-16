package utils;

import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.layouts.workspace.Workspace;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Provides global utility functions for the application.
 */
public class ApplicationUtils {

    /**
     * File the current workspace is saved at. If not yet saved, the value is null.
     */
    private static File savedFile = null;
    /**
     * The currently open Workspace.
     */
    private static Workspace currentWorkspace = null;
    /**
     * HashMap containing all EventCards, Storylines and Chapters in the current workspace. A unique componentID is used as the HashMap key for each component.
     */
    private static HashMap<String, BasicStoryComponent> hashMap = new HashMap<>();

    /**
     * Initializes a new project and workspace.
     */
    public static void initialize() {
        savedFile = null;
        Workspace workspace = new Workspace();
        setCurrentWorkspace(workspace);
    }

    /**
     * Getter for currentWorkspace parameter.
     * @return the app's current workspace.
     */
    public static Workspace getCurrentWorkspace() {
        return ApplicationUtils.currentWorkspace;
    }

    /**
     * Setter for currentWorkspace parameter.
     * @param currentWorkspace the Workspace to be set as current workspace.
     */
    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationUtils.currentWorkspace = currentWorkspace;
    }

    /**
     * Getter for savedFile parameter.
     * @return the current savedFile.
     */
    public static File getSavedFile() {
        return savedFile;
    }

    /**
     * Setter for savedFile parameter.
     * @param savedFile the File to be set as the savedFile parameter.
     */
    public static void setSavedFile(File savedFile) {
        ApplicationUtils.savedFile = savedFile;
    }

    /**
     * Removes a specified BasicStoryComponent from current HashMap.
     * @param key componentID of component to remove from HashMap.
     */
    public static void removeItemToCurrentHashMap(String key) {
        ApplicationUtils.hashMap.remove(key);
    }

    /**
     * Adds a specified BasicStoryComponent to current HashMap.
     * @param key componentID of component to add to HashMap.
     * @param value the BasicStoryComponent to be added to HashMap.
     */
    public static void putItemToCurrentHashMap(String key, BasicStoryComponent value) {
        ApplicationUtils.hashMap.put(key, value);
    }

    /**
     * Getter for components in HashMap.
     * @param key componentID of component to get.
     * @return the specified BasicStoryComponent corresponding to componentID.
     */
    public static BasicStoryComponent getValueFromCurrentHashMap(String key) {
        return ApplicationUtils.hashMap.get(key);
    }

    /**
     * Converts current HashMap into a JSONObject.
     * @return the HashMap converted JSONObject form.
     */
    @SuppressWarnings("unchecked")
    public static JSONObject getCurrentHashMapAsJSONObject() {
        JSONObject hashMapObject = new JSONObject();
        ApplicationUtils.hashMap.forEach((key, value) -> {
            hashMapObject.put(key, value.writeJSONObject());
        });
        System.out.println();
        return hashMapObject;
    }

    /**
     * Initializes a default new project with one empty document.
     */
    public static void newProject() {
        ApplicationUtils.initialize();
        Document newDocument = new Document(SystemUtils.DEFAULT_DOCUMENT_TITLE);
        ApplicationUtils.getCurrentWorkspace().getSideBar().initBindings(newDocument);
        ApplicationUtils.getCurrentWorkspace().addDocument(newDocument);
    }

    /**
     * Clears the workspace and creates a new one. Used to prepare for opening a .json file. The process is as follows:
     * <ol>
     *     <li>Unbinds and clear the sidebar listener.</li>
     *     <li>Removes all documents from Workspace.</li>
     *     <li>Initializes a new Workspace and HashMap.</li>
     * </ol>
     */
    public static void clear() {
        Document activeDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        if(activeDocument != null) {
            ApplicationUtils.getCurrentWorkspace().getSideBar().clear(activeDocument);
        }

        ApplicationUtils.getCurrentWorkspace().getDocumentList().removeAllDocuments();

        setCurrentWorkspace(new Workspace());
        hashMap = new HashMap<>();
    }

    /**
     * Sets the first document in DocumentList as the active document.
     */
    public static void setFirstDocumentAsActive() {
        Document firstDocument = ApplicationUtils.getCurrentWorkspace().getDocumentList().get(0);
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(firstDocument);
    }

    /**
     * Updates display of the workspace.
     */
    public static void updateWorkspace() {
        Document activeDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(activeDocument);
        ApplicationUtils.getCurrentWorkspace().getDocumentList().getActiveDocument().getEventCards().update();
    }
}

