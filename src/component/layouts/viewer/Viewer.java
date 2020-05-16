package component.layouts.viewer;

import component.components.chapter.Chapter;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * The area where components are displayed in a timeline-based manner.
 */
public class Viewer extends ScrollPane {

    /**
     * The context menu shown in viewer area
     */
    private final ContextMenu contextMenu;

    /**
     * Constructor for a Viewer. Initializes the context menu.
     */
    public Viewer() {
        contextMenu = new ContextMenu();
        initializeContextMenu();
        setContextMenu(contextMenu);
    }

    /**
     * Sets the content of this viewer to the document specified in document parameter.
     * @param document document to be set as the viewer's content.
     */
    public void setDocument(Document document) {
        setContent(document);
    }

    /**
     * Creates a blank storyline set to the default values. Called through context menu.
     */
    private void createStoryline() {
        Storyline newStoryline = new Storyline();
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addStoryLine(newStoryline);
        ApplicationUtils.updateWorkspace();
    }

    /**
     * Creates a blank event card set to the default values. Called through context menu.
     */
    private void createEventCard() {
        EventCard newEventCard = new EventCard();
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        ApplicationUtils.updateWorkspace();
    }

    /**
     * Creates a blank chapter set to the default values. Called through context menu.
     */
    private void createChapter() {
        Chapter newChapter = new Chapter();
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addChapter(newChapter);
        ApplicationUtils.updateWorkspace();
    }

    /**
     * Initializes the context menu to be shown within viewer area.
     */
    private void initializeContextMenu() {
        MenuItem storylineMenuItem = new MenuItem(SystemUtils.NEW_STORYLINE);
        storylineMenuItem.setOnAction((ActionEvent innerEvent) -> createStoryline());
        MenuItem eventCardMenuitem = new MenuItem(SystemUtils.NEW_EVENT_CARD);
        eventCardMenuitem.setOnAction((ActionEvent innerEvent) -> createEventCard());
        MenuItem chapterMenuitem = new MenuItem(SystemUtils.NEW_CHAPTER);
        chapterMenuitem.setOnAction((ActionEvent innerEvent) -> createChapter());
        contextMenu.getItems().addAll(storylineMenuItem, eventCardMenuitem, chapterMenuitem);
    }
}
