package utils;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.layouts.workspace.Workspace;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;

public class ApplicationUtils {

    private static File savedFile = null;
    private static Workspace currentWorkspace = null;
    private static HashMap<String, BasicStoryComponent> hashMap = new HashMap<>(); //contains all basic story components

    public static void initialize() {
        savedFile = null;
        Workspace workspace = new Workspace();
        setCurrentWorkspace(workspace);
    }

    public static Workspace getCurrentWorkspace() {
        return ApplicationUtils.currentWorkspace;
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationUtils.currentWorkspace = currentWorkspace;
        // TODO : Problem is that document tabProperty has to be rebinded

    }

    public static File getSavedFile() {
        return savedFile;
    }

    public static void setSavedFile(File savedFile) {
        ApplicationUtils.savedFile = savedFile;
    }

    public static void removeItemToCurrentHashMap(String key) {
        ApplicationUtils.hashMap.remove(key);
    }

    public static void putItemToCurrentHashMap(String key, BasicStoryComponent value) {
        ApplicationUtils.hashMap.put(key, value);
    }

    public static BasicStoryComponent getValueFromCurrentHashMap(String key) {
        return ApplicationUtils.hashMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static JSONObject getCurrentHashMapAsJSONObject() {
        JSONObject hashMapObject = new JSONObject();
        ApplicationUtils.hashMap.forEach((key, value) -> {
            if (value instanceof EventCard || value instanceof Storyline || value instanceof Chapter) {
                System.out.println("HashMap key: " + key + ", value = " + value.toString() + ", type = " + value.getClass().getName());
                hashMapObject.put(key, value.writeJSONObject());
            }
        });
        System.out.println();
        return hashMapObject;
    }

    public static void newProject() {
        ApplicationUtils.initialize();
        Document newDocument = new Document("New Document");
        ApplicationUtils.getCurrentWorkspace().getSideBar().initBindings(newDocument);
        ApplicationUtils.getCurrentWorkspace().addDocument(newDocument);
    }

    public static void clear() {
        //unbind and clear sidebar listener
        Document activeDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        if(activeDocument != null) {
            ApplicationUtils.getCurrentWorkspace().getSideBar().clear(activeDocument);
        }

        //clear documents - will clear sidebar, tabs and viewer
        ApplicationUtils.getCurrentWorkspace().getDocumentList().removeAllDocuments();

        //init new workspace
        setCurrentWorkspace(new Workspace());
        hashMap = new HashMap<>();
    }

    public static void updateWorkspace() {
        Document activeDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(activeDocument);
        ApplicationUtils.getCurrentWorkspace().getDocumentList().getActiveDocument().getEventCards().update();
    }

    public static void updateWorkspaceOnOpen() {
        //rebind sidebar
        Document firstDocument = ApplicationUtils.getCurrentWorkspace().getDocumentList().get(0);
        System.out.println(firstDocument.toString());
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(firstDocument);

        //rerender viewer
        ApplicationUtils.updateWorkspace();
    }
}

