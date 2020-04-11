package component.layouts.viewer;

import component.components.document.Document;
import javafx.scene.control.TabPane;

public class Viewer extends TabPane {

    public Viewer() {

    }

    public void addDocument(Document document) {
        this.getTabs().add(document);
    }

    public void removeDocument(Document document) {
        this.getTabs().remove(document);
    }
}
