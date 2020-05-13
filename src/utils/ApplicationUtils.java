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

    public static void clear() {
        //clear sidebar
        Document activeDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ApplicationUtils.getCurrentWorkspace().getSideBar().clear(activeDocument);
        //document demo isn't cleared though

        //clear document tabs
        /*DocumentList documentList = ApplicationUtils.getCurrentWorkspace().getDocumentList();
        Bindings.unbindContent(tabContainer.getChildren(), documentList.tabsProperty());*/

        hashMap = new HashMap<>();
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

    public static void updateWorkspace() {
        Document activeDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(activeDocument);
        ApplicationUtils.getCurrentWorkspace().getDocumentList().getActiveDocument().getEventCards().update();
    }

    public static void updateOnOpen() {
        Document firstDocument = ApplicationUtils.getCurrentWorkspace().getDocumentList().get(0);
        //basically rebind everything
        ApplicationUtils.getCurrentWorkspace().getSideBar().initBindings(firstDocument);
    }
}

