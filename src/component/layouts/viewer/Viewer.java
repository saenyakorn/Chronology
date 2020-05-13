package component.layouts.viewer;

import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class Viewer extends ScrollPane {

    private final ContextMenu contextMenu;

    public Viewer() {
        contextMenu = new ContextMenu();
        initializeContextMenu();
        setContextMenu(contextMenu);
    }

    public void setDocument(Document document) {
        setContent(document);
    }

    private void createStoryline() {
        Storyline newStoryline = new Storyline();
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addStoryLine(newStoryline);
        ApplicationUtils.update();
    }

    private void createEventCard() {
        EventCard newEventCard = new EventCard();
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        ApplicationUtils.update();
    }

    private void initializeContextMenu() {
        MenuItem storylineMenuItem = new MenuItem(SystemUtils.NEW_STORYLINE);
        storylineMenuItem.setOnAction((ActionEvent innerEvent) -> createStoryline());
        MenuItem eventCardMenuitem = new MenuItem(SystemUtils.NEW_EVENT_CARD);
        eventCardMenuitem.setOnAction((ActionEvent innerEvent) -> createEventCard());
        contextMenu.getItems().addAll(eventCardMenuitem, storylineMenuItem);
    }
}
