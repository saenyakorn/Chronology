package application;

import component.components.document.Document;
import component.components.workspace.Workspace;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class ApplicationResource {

    @FXML
    private static BorderPane main;

    private static Workspace currentWorkspace;
    private static SingleSelectionModel<Tab> selectedTab;

    public static void initialize() {
        Workspace workspace = new Workspace();
        workspace.addDocument(new Document("New Document"));
        ApplicationResource.currentWorkspace = workspace;
    }

    public static Workspace getCurrentWorkspace() {
        return currentWorkspace;
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationResource.currentWorkspace = currentWorkspace;
    }

    public static SingleSelectionModel<Tab> getSelectedTab() {
        return selectedTab;
    }

    public static void setSelectedTab(SingleSelectionModel<Tab> selectedTab) {
        ApplicationResource.selectedTab = selectedTab;
    }
}
