package component.layouts.viewer;

import component.components.document.Document;
import javafx.scene.control.TabPane;

public class Viewer extends TabPane {

    public Viewer() {

    }

    public void addDocument(Document document) {
        System.out.println("Viewer: " + document.getText());
        this.getTabs().add(document);
    }

    public void removeDocument(Document document) {
        this.getTabs().remove(document);
    }
}
