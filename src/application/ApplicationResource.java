package application;

import component.base.BasicStoryComponent;
import component.layouts.workspace.Workspace;

import java.util.HashMap;
import java.util.Hashtable;

public class ApplicationResource {

    private static Workspace currentWorkspace;
    private static Hashtable<String, BasicStoryComponent> hashtableBasicStoryComponents;

    public static void initialize() {
        hashtableBasicStoryComponents = new Hashtable<>();
    }

    public static Workspace getCurrentWorkspace() {
        return currentWorkspace;
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationResource.currentWorkspace = currentWorkspace;
    }

    public static Hashtable<String, BasicStoryComponent> getHashtableBasicStoryComponents() {
        return hashtableBasicStoryComponents;
    }
}
