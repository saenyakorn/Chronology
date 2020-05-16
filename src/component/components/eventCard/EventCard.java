package component.components.eventCard;

import colors.GlobalColor;
import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import component.dialog.edit.*;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;
import utils.SystemUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private final ContextMenu contextMenu = new ContextMenu();
    private final Property<Storyline> selfStoryline = new SimpleObjectProperty<>(null);
    private final Property<Chapter> selfChapter = new SimpleObjectProperty<>(null);
    private int index = -1;

    @FXML
    private Pane root;
    @FXML
    private Text date;
    @FXML
    private Text time;
    @FXML
    private HBox dateTimeContainer;
    @FXML
    private TextField cardTitle;
    @FXML
    private StackPane cardTitleContainer;
    @FXML
    private TextArea cardDescription;
    @FXML
    private StackPane cardDescriptionContainer;
    @FXML
    private StackPane chapterTitleContainer;
    @FXML
    private TextField chapterTitle;

    public EventCard() {
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    public EventCard(String componentID) {
        super(componentID);
    }

    public EventCard(String title, String description) {
        super(title, description);
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    public EventCard(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    public Pane getDisplay() {
        return root;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Property<Storyline> storylineProperty() {
        return selfStoryline;
    }

    public Storyline getStoryline() {
        return selfStoryline.getValue();
    }

    public void setStoryline(Storyline storyline) {
        this.selfStoryline.setValue(storyline);
    }

    public Property<Chapter> chapterProperty() {
        return selfChapter;
    }

    public Chapter getChapter() {
        return selfChapter.getValue();
    }

    public void setChapter(Chapter chapter) {
        this.selfChapter.setValue(chapter);
    }

    public void setChapterColor(Color color) {
        chapterTitleContainer.setStyle("-fx-background-color: " + GlobalColor.colorToHex(color) + ";");
    }

    @Override
    public void setTitleAndDisplay(String title) {
        setTitle(title);
        cardTitle.setText(title);
        ApplicationUtils.updateWorkspace();
    }

    @Override
    public void setDescriptionAndDisplay(String description) {
        setDescription(description);
        cardDescription.setText(description);
    }

    @Override
    public void setColorAndDisplay(Color color) {
        setColor(color);
        date.setFill(color);
        time.setFill(color);
        cardTitleContainer.setStyle("-fx-background-color: " + GlobalColor.colorToHex(color) + ";");
    }

    @Override
    public void setTimePeriodAndDisplay(TimePeriod timePeriod) {
        setTimePeriod(timePeriod);
        if (storylineProperty().getValue() != null) {
            setSelfComponentTimePeriod(timePeriod, getStoryline());
        }
        if (chapterProperty().getValue() != null) {
            setSelfComponentTimePeriod(timePeriod, getChapter());
        }

        date.setText(timePeriod.getBeginDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        time.setText(timePeriod.getBeginDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public void setSelfComponentTimePeriod(TimePeriod timePeriod, BasicStoryComponent component) {
        LocalDateTime newBeginDateTime = timePeriod.getBeginDateTime();
        LocalDateTime newEndDateTime = timePeriod.getEndDateTime();

        if (component.eventCardsInComponent() == 1) {
            component.getTimePeriod().setBeginDateTime(newBeginDateTime);
            System.out.println("Storyline BeginDateTime set to " + component.getTimePeriod().getBeginDateTime());
            component.getTimePeriod().setEndDateTime(newEndDateTime);
            System.out.println("Storyline EndDateTime set to " + component.getTimePeriod().getEndDateTime());
        } else {
            if (newBeginDateTime.isBefore(component.getTimePeriod().getBeginDateTime())) {
                component.getTimePeriod().setBeginDateTime(newBeginDateTime);
                System.out.println("Storyline BeginDateTime set to " + component.getTimePeriod().getBeginDateTime());

            }
            if (newEndDateTime.isAfter(component.getTimePeriod().getEndDateTime())) {
                component.getTimePeriod().setEndDateTime(newEndDateTime);
                System.out.println("Storyline EndDateTime set to " + component.getTimePeriod().getEndDateTime());
            }
        }
    }

    public void setStorylineAndDisplay(Storyline storyline) {
        setStoryline(storyline);
        if (storyline != null) {
            setColorAndDisplay(storyline.getColor());
            setSelfComponentTimePeriod(getTimePeriod(), storyline);
        }
    }

    public void setChapterAndDisplay(Chapter chapter) {
        setChapter(chapter);
        if (chapter != null) {
            setChapterColor(chapter.getColor());
            setSelfComponentTimePeriod(getTimePeriod(), chapter);
            chapterTitle.setText(chapter.getTitle());
        } else {
            chapterTitle.setText(null);
            chapterTitleContainer.setStyle("-fx-background-color: white;");
        }
    }

    public void setChapterTitleAndDisplay(String title) {
        getChapter().setTitle(title);
        chapterTitle.setText(title);
        ApplicationUtils.updateWorkspace();
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int compareTo(EventCard o) throws IllegalArgumentException {
        return getTimePeriod().compareTo(o.getTimePeriod());
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject eventCardObject = super.writeJSONObject();

        if (chapterProperty().getValue() != null) {
            eventCardObject.put("selfChapter", chapterProperty().getValue().getComponentId());
        } else {
            eventCardObject.put("selfChapter", null);
        }

        if (storylineProperty().getValue() != null) {
            eventCardObject.put("selfStoryline", storylineProperty().getValue().getComponentId());
        } else {
            eventCardObject.put("selfStoryline", null);
        }

        eventCardObject.put("type", "EventCard");

        return eventCardObject;
    }

    @Override
    public EventCard readJSONObject(JSONObject eventCardObject) {
        super.readJSONObject(eventCardObject);
        String selfChapterID = (String) eventCardObject.get("selfChapter");
        String selfStorylineID = (String) eventCardObject.get("selfStoryline");

        setChapter((Chapter) ApplicationUtils.getValueFromCurrentHashMap(selfChapterID));
        setStoryline((Storyline) ApplicationUtils.getValueFromCurrentHashMap(selfStorylineID));

        return this;
    }

    public static EventCard readJSONObjectAsComponentID(JSONObject componentObject) {
        //if not correct type, throw error?
        EventCard readEventCard = (EventCard) ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
        readEventCard.initializeDisplayAfterRead();
        return readEventCard;
    }

    private void initializeDisplayAfterRead() {
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    private void initializeContextMenu() {
        contextMenu.setAutoHide(true);
        contextMenu.setConsumeAutoHidingEvents(true);

        MenuItem setTitleMenuItem = new MenuItem(SystemUtils.EDIT_TITLE);
        setTitleMenuItem.setOnAction((ActionEvent event) -> new SetTitleDialog(this).show());

        MenuItem setDescriptionMenuItem = new MenuItem(SystemUtils.EDIT_DESCRIPTION);
        setDescriptionMenuItem.setOnAction((ActionEvent event) -> new SetDescriptionDialog(this).show());

        MenuItem timePeriodMenuItem = new MenuItem(SystemUtils.EDIT_DATA_TIME);
        timePeriodMenuItem.setOnAction((ActionEvent event) -> new SetTimePeriodDialog(this).show());

        MenuItem colorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        colorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(getStoryline()).show());

        MenuItem chapterMenuItem = new MenuItem(SystemUtils.MOVE_TO_CHAPTER);
        chapterMenuItem.setOnAction((ActionEvent event) -> new SetChapterDialog(this).show());

        MenuItem storylineMenuItem = new MenuItem(SystemUtils.MOVE_TO_STORYLINE);
        storylineMenuItem.setOnAction((ActionEvent event) -> new SetStorylineDialog(this).show());

        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards().removeEventCard(this));

        contextMenu.getItems().addAll(setTitleMenuItem, setDescriptionMenuItem, timePeriodMenuItem, colorMenuItem, chapterMenuItem, storylineMenuItem, removeMenuItem);
    }

    @FXML
    public void initialize() {
        // toggle override setter
        setTitleAndDisplay(getTitle());
        setColorAndDisplay(getColor());
        setTimePeriodAndDisplay(getTimePeriod());
        setDescriptionAndDisplay(getDescription());

        // if notHasChapter set bottom color to white
        // else set bottom color according to chapter color
        if (getChapter() == null) {
            chapterTitleContainer.setStyle("-fx-background-color: white;");
        } else {
            setChapterAndDisplay(getChapter());
        }

        chapterTitle.setPrefHeight(Region.USE_COMPUTED_SIZE);

        // if has Storyline set color to storyline color
        if (getStoryline() != null) {
            setStorylineAndDisplay(getStoryline());
        }

        // When Event Card get right click
        root.setOnContextMenuRequested((ContextMenuEvent event) -> {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        // Hide context menu when click other
        root.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            }
            event.consume();
        });

        // When Event Card is dragged
        root.setOnDragDetected((MouseEvent event) -> {
            Dragboard dragboard = root.startDragAndDrop(TransferMode.MOVE);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            dragboard.setDragView(root.snapshot(parameters, null));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(getComponentId());
            dragboard.setContent(clipboardContent);
            event.consume();
        });

        // Click the card title to change text
        cardTitle.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        cardTitleContainer.setOnMouseClicked((MouseEvent event) -> cardTitle.setDisable(false));
        cardTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!getTitle().equals(cardTitle.getText())) {
                setTitleAndDisplay(cardTitle.getText());
                System.out.println("Card title set to " + getTitle());
            }
            cardTitle.setDisable(true);
        });

        // Click the card description to change text
        cardDescription.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        cardDescriptionContainer.setOnMouseClicked((MouseEvent event) -> cardDescription.setDisable(false));
        cardDescriptionContainer.setOnMouseExited((MouseEvent event) -> {
            if (!getDescription().equals(cardDescription.getText())) {
                setDescriptionAndDisplay(cardDescription.getText());
                System.out.println("Card description set to " + getDescription());
            }
            cardDescription.setDisable(true);
        });

        // Click the chapter title to change text
        chapterTitle.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        chapterTitleContainer.setOnMouseClicked((MouseEvent event) -> {
            if (getChapter() != null)
                chapterTitle.setDisable(false);
        });
        chapterTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if ((getChapter() != null) && !getChapter().getTitle().equals(chapterTitle.getText())) {
                setChapterTitleAndDisplay(chapterTitle.getText());
                System.out.println("Chapter name set to " + getChapter().getTitle());
            }
            chapterTitle.setDisable(true);
        });
        chapterTitleContainer.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
    }
}
