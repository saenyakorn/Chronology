package component.components.document;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class DocumentCustomTab extends HBox {

    private final Label label = new Label();
    private final Button closeButton = new Button();
    private final SimpleBooleanProperty active = new SimpleBooleanProperty(false);
    private final SVGPath closeIcon = SystemUtils.getIconSVG("close_icon_24px.svg");

    public DocumentCustomTab() {
        initializeFXComponent();
    }

    public DocumentCustomTab(String text) {
        label.setText(text);
        initializeFXComponent();
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

    private void initializeFXComponent() {
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        getChildren().addAll(label, closeButton);
        getStyleClass().add("document-tab-container");
        label.getStyleClass().add("document-tab-text");
        closeIcon.getStyleClass().add("icon-24px");
        closeButton.getStyleClass().add("document-tab-button");
        closeButton.setGraphic(closeIcon);
        closeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
