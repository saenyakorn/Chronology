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

    private final VBox canvas;
    private final ScrollPane scrollPane;
    private final ContextMenu contextMenu;

    public Viewer() {
        canvas = new VBox();
        scrollPane = new ScrollPane();
        contextMenu = new ContextMenu();

        canvas.setStyle("-fx-background-color: rgba(165, 135, 251,0.2);");
        canvas.setPrefSize(1500, 1500);
        canvas.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        canvas.setMinSize(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        System.out.println("LayoutX: " + canvas.getLayoutX());
        System.out.println("LayoutY: " + canvas.getLayoutY());
        System.out.println("Max H: " + canvas.getMaxHeight());
        System.out.println("Max W: " + canvas.getMaxHeight());
        System.out.println("Pref H: " + canvas.getPrefHeight());
        System.out.println("Pref W: " + canvas.getPrefWidth());

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

    public void addStorylineToCanvas(Storyline storyline) {
        canvas.getChildren().add(storyline);
        scrollPane.setContent(canvas);
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
        contextMenu.hide();
        if (event.isSecondaryButtonDown()) {
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        }
        event.consume();
    }

    private void initializeEventHandler() {
        canvas.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        canvas.setOnDragDropped((DragEvent event) -> {
            String componentId = event.getDragboard().getString();
            BasicStoryComponent obj = ApplicationResource.getValueFromCurrentWorkspaceHashMap(componentId);
            if (obj instanceof Storyline) {
                Storyline interestStoryline = (Storyline) obj;
                interestStoryline.relocate(event.getX() - (interestStoryline.getPrefWidth() / 2), event.getY() - (interestStoryline.getPrefHeight() / 2));
            }
            event.consume();
        });

        setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
        canvas.setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
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
