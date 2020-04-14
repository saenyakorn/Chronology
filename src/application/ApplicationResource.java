package application;

import component.base.BasicStoryComponent;
import component.layouts.workspace.Workspace;

import java.util.HashMap;

public class ApplicationResource {

    private static Workspace currentWorkspace;
    private static HashMap<String, BasicStoryComponent> hashMapBasicStoryComponents;

    public static void initialize() {
        hashMapBasicStoryComponents = new HashMap<>();
    }

    public static Workspace getCurrentWorkspace() {
        return currentWorkspace;
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationResource.currentWorkspace = currentWorkspace;
    }

    public static HashMap<String, BasicStoryComponent> getHashMapBasicStoryComponents() {
        return hashMapBasicStoryComponents;
    }
}
