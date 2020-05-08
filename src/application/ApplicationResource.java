package application;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.layouts.workspace.Workspace;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;

public class ApplicationResource {

    private static final HashMap<String, BasicStoryComponent> hashMap = new HashMap<>(); //contains all basic story components
    private static File savedFile = null;
    private static Workspace currentWorkspace = null;

    public static void initialize() {
        savedFile = null;
        Workspace workspace = new Workspace();
        setCurrentWorkspace(workspace);
    }

    public static Workspace getCurrentWorkspace() {
        return ApplicationResource.currentWorkspace;
    }

    public static File getSavedFile() {
        return savedFile;
    }

    public static void setSavedFile(File savedFile) {
        ApplicationResource.savedFile = savedFile;
    }

    public static void update() {
        Document currentDocument = ApplicationResource.getCurrentWorkspace().getActiveDocument();
        ApplicationResource.getCurrentWorkspace().setActiveDocument(currentDocument);
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationResource.currentWorkspace = currentWorkspace;
    }

    public static void putItemToCurrentHashMap(String key, BasicStoryComponent value) {
        ApplicationResource.hashMap.put(key, value);
    }

    public static BasicStoryComponent getValueFromCurrentHashMap(String key) {
        return ApplicationResource.hashMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static JSONObject getCurrentHashMapAsJSONObject() {
        JSONObject hashMapObject = new JSONObject();
        ApplicationResource.hashMap.forEach((key, value) -> {
            if (value instanceof EventCard || value instanceof Storyline || value instanceof Chapter) {
                System.out.println("HashMap key: " + key + ", value = " + value.toString() + ", type = " + value.getClass().getName());
                hashMapObject.put(key, value.writeJSONObject());
            }
        });
        System.out.println();
        return hashMapObject;
    }

    public static void newProject() {
        // initialized application
        ApplicationResource.initialize();

        // add template structure
        ApplicationResource.getCurrentWorkspace().addDocument(new Document("New Document"));

        // render side bar
        ApplicationResource.getCurrentWorkspace().getSideBar().renderSideBar(ApplicationResource.getCurrentWorkspace().getActiveDocument());
    }
}
