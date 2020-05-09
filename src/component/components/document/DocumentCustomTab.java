package component.components.document;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DocumentCustomTab extends HBox {

    private final SimpleBooleanProperty active;
    private final Label label;
    private final Button closeButton;

    public DocumentCustomTab() {
        active = new SimpleBooleanProperty(false);
        label = new Label();
        closeButton = new Button("close");
        getChildren().addAll(label, closeButton);
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        getStyleClass().add("document-tab-container");
        label.getStyleClass().add("document-tab-text");
        closeButton.getStyleClass().add("document-tab-button");
    }

    public DocumentCustomTab(String text) {
        active = new SimpleBooleanProperty(false);
        label = new Label(text);
        closeButton = new Button("close");
        getChildren().addAll(label, closeButton);
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        getStyleClass().add("document-tab-container");
        label.getStyleClass().add("document-tab-text");
        closeButton.getStyleClass().add("document-tab-button");
    }

    public SimpleBooleanProperty getActiveProperty() {
        return active;
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.setValue(active);
        if (active) {
            getStyleClass().add("active");
        } else {
            getStyleClass().remove("active");
        }
    }

    public Button getButton() {
        return closeButton;
    }

    public void initializeEventHandler() {
    }
}
