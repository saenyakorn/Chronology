package application;

import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.layouts.workspace.Workspace;

import java.io.File;

public class ApplicationResource {

    private static File savedFile;
    private static Workspace currentWorkspace;

    public static void initialize() {
        savedFile = null;
        Workspace workspace = new Workspace();
        setCurrentWorkspace(workspace);
    }

    public static Workspace getCurrentWorkspace() {
        return ApplicationResource.currentWorkspace;
    }

    public static void update() {
        Document currentDocument = ApplicationResource.getCurrentWorkspace().getCurrentDocument();
        ApplicationResource.getCurrentWorkspace().setActiveDocument(currentDocument);
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationResource.currentWorkspace = currentWorkspace;
    }

    public static void putItemToCurrentWorkspaceHashMap(String key, BasicStoryComponent value) {
        ApplicationResource.currentWorkspace.getHashMapBasicStoryComponents().put(key, value);
    }

    public static BasicStoryComponent getValueFromCurrentWorkspaceHashMap(String key) {
        return ApplicationResource.currentWorkspace.getHashMapBasicStoryComponents().get(key);
    }
}
