package component.layouts.viewer;

import application.ApplicationResource;
import component.base.BasicStoryComponent;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class Viewer extends TabPane {

    private final VBox vBox;
    private final ScrollPane scrollPane;
    private final ContextMenu contextMenu;

    public Viewer() {
        vBox = new VBox();
        scrollPane = new ScrollPane();
        contextMenu = new ContextMenu();
        initializeContextMenu();
        initializeEventHandler();
    }

    public void renderViewer(Document document) {

    }

    public void addDocument(Document document) {
        this.getTabs().add(document);
    }

    public void removeDocument(Document document) {
        this.getTabs().remove(document);
    }

    public void addStoryline(Storyline storyline) {
        vBox.getChildren().add(storyline);
        scrollPane.setContent(vBox);
        getSelectionModel().getSelectedItem().setContent(scrollPane);
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
        if (contextMenu.isShowing()) {
            contextMenu.hide();
        }
        if (event.isSecondaryButtonDown()) {
            System.out.println("Viewer: " + event.getTarget());
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
        event.consume();
    }

    private void initializeEventHandler() {
        setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationResource.getValueFromCurrentWorkspaceHashMap(itemId);
            if (item instanceof Storyline) {
                Storyline storyline = (Storyline) item;
                if (!vBox.getChildren().contains(storyline)) {
                    addStoryline(storyline);
                }
            }
        });
        setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
        vBox.setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
        scrollPane.setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
    }

    private void initializeContextMenu() {
        MenuItem item1 = new MenuItem("add storyline");
        item1.setOnAction((ActionEvent innerEvent) -> createStoryline());
        MenuItem item2 = new MenuItem("add event card");
        item2.setOnAction((ActionEvent innerEvent) -> createEventCard());
        contextMenu.getItems().addAll(item1, new SeparatorMenuItem(), item2);
    }
}
