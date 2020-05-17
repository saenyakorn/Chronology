package component.components.document;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;
import utils.TransitionUtils;

/**
 * A tab in the document tab bar. Each tab represents a document.
 * @see Document
 */
public class DocumentCustomTab extends HBox {

    /**
     * Label showing document name.
     */
    private final Label label = new Label();
    /**
     * Close button.
     */
    private final Button closeButton = new Button();
    /**
     * Whether or not this document is active. Wrapped with SimpleBooleanProperty.
     */
    private final SimpleBooleanProperty active = new SimpleBooleanProperty(false);
    /**
     * Close button icon SVG.
     */
    private final SVGPath closeIcon = SystemUtils.getIconSVG("close_icon_24px.svg");

    /**
     * Constructor for DocumentCustomTab that requires document name as text.
     *
     * @param text the document's name.
     */
    public DocumentCustomTab(String text) {
        label.setText(text);
        initializeFXComponent();
    }

    /**
     * Setter for text in label.
     *
     * @param text the String value to be set.
     */
    public void setTabText(String text) {
        label.setText(text);
    }

    /**
     * Getter for active.
     *
     * @return whether or not this document is active.
     */
    public boolean isActive() {
        return active.get();
    }

    /**
     * Setter for active.
     * @param active the boolean value to be set.
     */
    public void setActive(boolean active) {
        this.active.setValue(active);
        if (active) {
            getStyleClass().add("active");
        } else {
            getStyleClass().remove("active");
        }
    }

    /**
     * Initializes an event handler for when close button is clicked.
     * @param takeAction an action.
     */
    public void setOnCloseRequest(TransitionUtils.TakeAction takeAction) {
        closeButton.setOnMouseClicked((MouseEvent event) -> takeAction.action());
    }

    /**
     * Initializes the look of this tab.
     */
    private void initializeFXComponent() {
        getStylesheets().add(ClassLoader.getSystemResource("component/components/document/Document.css").toExternalForm());
        getChildren().addAll(label, closeButton);
        getStyleClass().add("document-tab-container");
        label.getStyleClass().add("document-tab-text");
        closeIcon.getStyleClass().add("icon-24px");
        closeButton.getStyleClass().add("document-tab-button");
        closeButton.setGraphic(closeIcon);
        closeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
