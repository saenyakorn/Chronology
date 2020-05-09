package component.layouts.viewer;

import application.ApplicationResource;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;

public class Viewer extends ScrollPane {

    private final ContextMenu contextMenu;

    public Viewer() {
        contextMenu = new ContextMenu();
        initializeContextMenu();
        initializeEventHandler();
    }

    public void setDocument(Document document) {
        document.setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
        setContent(document);
    }

    private void createStoryline() {
        Storyline newStoryline = new Storyline();
        ApplicationResource.getCurrentWorkspace().getActiveDocument().addStoryLine(newStoryline);
        ApplicationResource.update();
    }

    private void createEventCard() {
        EventCard newEventCard = new EventCard();
        ApplicationResource.getCurrentWorkspace().getActiveDocument().addEventCard(newEventCard);
        ApplicationResource.update();
    }

    private void rightClickContextMenu(MouseEvent event) {
        contextMenu.hide();
        if (event.isSecondaryButtonDown()) {
            System.out.println("Viewer: " + event.getTarget());
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        }
        event.consume();
    }

    private void initializeEventHandler() {
        setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
    }

    private void initializeContextMenu() {
        MenuItem item1 = new MenuItem("add storyline");
        item1.setOnAction((ActionEvent innerEvent) -> createStoryline());
        MenuItem item2 = new MenuItem("add event card");
        item2.setOnAction((ActionEvent innerEvent) -> createEventCard());
        contextMenu.getItems().addAll(item1, new SeparatorMenuItem(), item2);
    }
}
