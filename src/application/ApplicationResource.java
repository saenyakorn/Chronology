package application;

import component.layouts.workspace.Workspace;

public class ApplicationResource {

    private static Workspace currentWorkspace;

    public static Workspace getCurrentWorkspace() {
        return currentWorkspace;
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationResource.currentWorkspace = currentWorkspace;
    }
}
