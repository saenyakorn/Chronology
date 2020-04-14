package application;

import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.layouts.workspace.Workspace;

public class ApplicationResource {

    private static Workspace currentWorkspace;

    public static void initialize() {
        ApplicationResource.currentWorkspace.addDocument(new Document("New Document"));
    }

    public static Workspace getCurrentWorkspace() {
        return ApplicationResource.currentWorkspace;
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
