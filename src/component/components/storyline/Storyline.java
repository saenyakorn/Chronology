package component.components.storyline;

import application.ApplicationResource;
import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import component.dialog.SetColorDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Storyline extends BasicStoryComponent {
    private final EventCardList eventCards;

    @FXML
    private Pane root;
    @FXML
    private Line line;
    @FXML
    private TextField storylineTitle;
    @FXML
    private HBox storylineTitleContainer;
    @FXML
    private EventCardList eventCardList;

    public Storyline() {
        eventCards = new EventCardList();
        loadFXML("Storyline.fxml");
        initializedEventHandler();
    }

    public Storyline(String title, String description) {
        super(title, description);
        eventCards = new EventCardList();
        loadFXML("Storyline.fxml");
        initializedEventHandler();
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        eventCards = new EventCardList();
        loadFXML("Storyline.fxml");
        initializedEventHandler();
    }

    @FXML
    public void initialize() {
        this.setTitle(this.getTitle());
        storylineTitle.setDisable(true);
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!title.equals(storylineTitle.getText())) {
                this.setTitle(storylineTitle.getText());
                System.out.println("Storyline title set to " + title);
            }
            storylineTitle.setDisable(true);
        });

        this.setColor(this.getColor());
        initializeContextMenu();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        storylineTitle.setText(title);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        line.setStroke(color);
        storylineTitle.setStyle("-fx-text-fill: " + colorToHex(color) + ";");
        for (EventCard eventCard : eventCards) {
            eventCard.setColor(color);
        }
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        eventCard.setStoryline(this);
        renderEventCards();
        line.setEndX(line.getEndX() + (SystemConstants.EVENTCARD_PREF_WIDTH + 30));
    }

    public void renderEventCards() {
        eventCardList.getChildren().clear();
        for (EventCard eventCard : eventCards) {
            eventCardList.getChildren().add(eventCard);
        }
    }

    public void removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
        eventCardList.getChildren().remove(eventCard);
        line.setEndX(line.getEndX() - (SystemConstants.EVENTCARD_PREF_WIDTH + 30));
    }

    @Override
    public String toString() {
        return title;
    }

    public void initializedEventHandler() {
        this.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        this.setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationResource.getValueFromCurrentWorkspaceHashMap(itemId);
            if (item instanceof EventCard) {
                EventCard eventCard = (EventCard) item;
                addEventCard(eventCard);
                ApplicationResource.update();
            }
        });
    }

    private void initializeContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem setColorMenu = new MenuItem("Edit storyline color");
        contextMenu.getItems().add(setColorMenu);
        setColorMenu.setOnAction((ActionEvent event) -> {
            SetColorDialog dialog = new SetColorDialog(this);
            dialog.show();
        });

        root.setOnMousePressed((MouseEvent event) -> {
            if (event.isSecondaryButtonDown() && !clickInEventCard(event)) {
                contextMenu.show(root, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private boolean clickInEventCard(MouseEvent event) {
        if (this.eventCardList.contains(eventCardList.screenToLocal(event.getScreenX(), event.getScreenY()))) {
            for (EventCard eventCard : eventCards) {
                if (eventCard.clickInEventCard(event))
                    return true;
            }
            return false;
        }
        return false;
    }

}
