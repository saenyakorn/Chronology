package component.layouts.sideBar;

import component.components.document.Document;
import component.layouts.sideBar.panel.ChapterPanel;
import component.layouts.sideBar.panel.DocumentPanel;
import component.layouts.sideBar.panel.EventCardPanel;
import component.layouts.sideBar.panel.StorylinePanel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * The area where all components in the active document, and all documents in the workspace, are listed.
 */
public class SideBar extends ScrollPane {

    /**
     * Document panel area.
     */
    private final DocumentPanel documentPanel = new DocumentPanel();
    /**
     * EventCard panel area.
     */
    private final EventCardPanel eventCardPanel = new EventCardPanel();
    /**
     * Chapter panel area.
     */
    private final ChapterPanel chapterPanel = new ChapterPanel();
    /**
     * Storyline panel area.
     */
    private final StorylinePanel storylinePanel = new StorylinePanel();

    /**
     * Constructor for a Sidebar. Css style is loaded, then VBox container and scroll pane are configured.
     */
    public SideBar() {
        getStylesheets().add(getClass().getResource("SideBar.css").toExternalForm());
        getStyleClass().add("body");

        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.POSITIVE_INFINITY);
        vBox.getStyleClass().add("v-box");
        vBox.getChildren().addAll(documentPanel, eventCardPanel, chapterPanel, storylinePanel);

        getStyleClass().add("side-bar");
        setFitToWidth(true);
        setPrefWidth(SystemUtils.SIDEBAR_PREF_WIDTH);
        setContent(vBox);
    }

    /**
     * Binds all panels to their corresponding lists.
     * @param activeDocument the document that the sidebar will be bound to.
     */
    public void initBindings(Document activeDocument) {
        documentPanel.binding(ApplicationUtils.getCurrentWorkspace().getDocumentList().listProperty());
        eventCardPanel.binding(activeDocument.getEventCards().getEventCards());
        storylinePanel.binding(activeDocument.getStorylines().getStorylines());
        chapterPanel.binding(activeDocument.getChapters().getChapters());
    }

    /**
     * Unbinds all panels from their corresponding lists.
     * @param document the document that the sidebar will be unbound from.
     */
    public void clear(Document document) {
        documentPanel.unbinding(ApplicationUtils.getCurrentWorkspace().getDocumentList().listProperty());
        eventCardPanel.unbinding(document.getEventCards().getEventCards());
        storylinePanel.unbinding(document.getStorylines().getStorylines());
        chapterPanel.unbinding(document.getChapters().getChapters());
    }

}
