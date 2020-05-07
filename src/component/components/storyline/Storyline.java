package component.components.storyline;

import application.ApplicationResource;
import application.SystemConstants;
import colors.RandomColor;
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
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Storyline extends BasicStoryComponent {
    private final EventCardList eventCards;
    private ContextMenu contextMenu;

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
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String title, String description) {
        super(title, description);
        eventCards = new EventCardList();
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        eventCards = new EventCardList();
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod, EventCardList eventCards) {
        super(title, description, color, timePeriod);
        this.eventCards = eventCards;
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    @FXML
    public void initialize() {
        setTitle(getTitle());
        setColor(getColor());
        storylineTitle.setDisable(true);
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!title.equals(storylineTitle.getText())) {
                setTitle(storylineTitle.getText());
                System.out.println("Storyline title set to " + title);
            }
            storylineTitle.setDisable(true);
        });
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
//        renderEventCards();
        line.setEndX(line.getEndX() + (SystemConstants.EVENTCARD_PREF_WIDTH + 30));
    }

    public void renderEventCards() {
//        eventCardList.getChildren().clear();
//        for (EventCard eventCard : eventCards) {
//            eventCardList.getChildren().add(eventCard);
//        }
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

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject getJSONObject() {
        JSONObject storyline = super.getJSONObject();
        storyline.put("eventCardList", eventCards.getJSONArray());
        return storyline;
    }

    public static Storyline parseJSONObject(JSONObject storylineObject) {
        String name = (String) storylineObject.get("name");
        String description = (String) storylineObject.get("description");
        Color color = Color.web((String) storylineObject.get("Color"));
        TimePeriod timePeriod = TimePeriod.stringToTimePeriod((String) storylineObject.get("TimePeriod"));
        EventCardList eventCards = EventCardList.parseJSONArray((JSONArray) storylineObject.get("eventCardList"));

        return new Storyline(name, description, color, timePeriod, eventCards);
    }

    public void initializeEventHandler() {
        setOnDragDetected((MouseEvent event) -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(snapshot(null, null));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(componentId);
            System.out.println(DataFormat.lookupMimeType("Storyline"));
            dragboard.setContent(clipboardContent);
            event.consume();
        });
        setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationResource.getValueFromCurrentWorkspaceHashMap(itemId);
            if (item instanceof EventCard) {
                EventCard eventCard = (EventCard) item;
                ApplicationResource.getCurrentWorkspace().getActiveDocument().removeEventCard(eventCard);
                addEventCard(eventCard);
                ApplicationResource.update();
            }
            event.consume();
        });
        setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
    }

    private void rightClickContextMenu(MouseEvent event) {
        if (contextMenu.isShowing()) {
            contextMenu.hide();
        }
        if (event.isSecondaryButtonDown()) {
            System.out.println("Storyline: " + event.getTarget());
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
        event.consume();
    }

    private void initializeContextMenu() {
        contextMenu = new ContextMenu();
        MenuItem colorMenuItem = new MenuItem("Edit storyline color");
        colorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(this).show());
        contextMenu.getItems().add(colorMenuItem);
    }

}
