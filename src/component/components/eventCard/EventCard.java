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

/**
 * An instance of BasicStoryComponent. An event card represents an event or scene in the story.
 * @see EventCardList
 */
public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    /**
     * The context menu shown in event card area.
     */
    private final ContextMenu contextMenu = new ContextMenu();
    /**
     * The storyline containing this event card. Wrapped with SimpleObjectProperty.
     */
    private final Property<Storyline> selfStoryline = new SimpleObjectProperty<>(null);
    /**
     * The chapter containing this event card. Wrapped with SimpleObjectProperty.
     */
    private final Property<Chapter> selfChapter = new SimpleObjectProperty<>(null);
    /**
     * Event card's index when visualized in viewer.
     */
    private int index = -1;

    /**
     * Root node.
     */
    @FXML
    private Pane root;
    /**
     * Where event date is displayed.
     */
    @FXML
    private Text date;
    /**
     * Where event time is displayed.
     */
    @FXML
    private Text time;
    /**
     * Area containing date and time.
     */
    @FXML
    private HBox dateTimeContainer;
    /**
     * Where event card's title is displayed.
     */
    @FXML
    private TextField cardTitle;
    /**
     * Area containing event card's title.
     */
    @FXML
    private StackPane cardTitleContainer;
    /**
     * Where event card's description is displayed.
     */
    @FXML
    private TextArea cardDescription;
    /**
     * Area containing event card's description.
     */
    @FXML
    private StackPane cardDescriptionContainer;
    /**
     * Where title of this event card's chapter is displayed.
     */
    @FXML
    private TextField chapterTitle;
    /**
     * Area containing title of this event card's chapter.
     */
    @FXML
    private StackPane chapterTitleContainer;

    /**
     * No-arg constructor of EventCard. All fields are set to default values.
     */
    public EventCard() {
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    /**
     * Constructor for EventCard that requires componentID. All fields are set to default values. Used to populate HashMap during file opening process.
     * @param componentID this eventCard's unique ID.
     */
    public EventCard(String componentID) {
        super(componentID);
    }

    /**
     * Constructor for EventCard that requires title and description. Remaining fields are set to default values.
     * @param title this eventCard's title.
     * @param description this eventCard's description.
     */
    public EventCard(String title, String description) {
        super(title, description);
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    /**
     * Constructor for Chapter that requires all fields.
     * @param title this eventCard's title.
     * @param description this eventCard's description.
     * @param color this eventCard's Color.
     * @param timePeriod this eventCard's TimePeriod.
     */
    public EventCard(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    /**
     * Accepts a JSONObject with a componentID field, and gets an eventCard from that componentID.
     * @param componentObject the JSONObject that is to be read.
     * @return an eventCard with data loaded from the eventCard referenced by the JSONObject's componentID.
     */
    public static EventCard readJSONObjectAsComponentID(JSONObject componentObject) {
        //if not correct type, throw error?
        EventCard readEventCard = (EventCard) ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
        readEventCard.initializeDisplayAfterRead();
        return readEventCard;
    }

    /**
     * Gets the root node of this event card.
     * @return the root node.
     */
    public Pane getDisplay() {
        return root;
    }

    /**
     * Getter for index.
     * @return this eventCard's index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter for index.
     * @param index the index to be set.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter for storylineProperty.
     * @return this eventCard's storylineProperty.
     */
    public Property<Storyline> storylineProperty() {
        return selfStoryline;
    }

    /**
     * Getter for storyline.
     * @return this eventCard's storyline.
     */
    public Storyline getStoryline() {
        return selfStoryline.getValue();
    }

    /**
     * Setter for storyline.
     * @param storyline the storyline to be set.
     */
    public void setStoryline(Storyline storyline) {
        this.selfStoryline.setValue(storyline);
    }

    /**
     * Getter for chapterProperty.
     * @return this eventCard's chapterProperty.
     */
    public Property<Chapter> chapterProperty() {
        return selfChapter;
    }

    /**
     * Getter for chapter.
     * @return this eventCard's chapter.
     */
    public Chapter getChapter() {
        return selfChapter.getValue();
    }

    /**
     * Setter for chapter.
     * @param chapter the chapter to be set.
     */
    public void setChapter(Chapter chapter) {
        this.selfChapter.setValue(chapter);
    }

    /**
     * Sets the chapter color displayed on this event card.
     * @param color the color to be set.
     */
    public void setChapterColor(Color color) {
        chapterTitleContainer.setStyle("-fx-background-color: " + GlobalColor.colorToHex(color) + ";");
    }

    /**
     * Sets title value and everything related to display of event card's title. This consists of:
     * <ul>
     *     <li>Text on cardTitle.</li>
     * </ul>
     * @param title the title to be set.
     */
    @Override
    public void setTitleAndDisplay(String title) {
        setTitle(title);
        cardTitle.setText(title);
        ApplicationUtils.updateWorkspace();
    }

    /**
     * Sets description value and everything related to display of event card's description. This consists of:
     * <ul>
     *     <li>Text on cardDescription.</li>
     * </ul>
     * @param description the description to be set.
     */
    @Override
    public void setDescriptionAndDisplay(String description) {
        setDescription(description);
        cardDescription.setText(description);
    }

    /**
     * Sets color value and everything related to display of event card's color. This consists of:
     * <ul>
     *     <li>Color of date and time text.</li>
     *     <li>Color of cardTitleContainer.</li>
     * </ul>
     * @param color the color to be set.
     */
    @Override
    public void setColorAndDisplay(Color color) {
        setColor(color);
        date.setFill(color);
        time.setFill(color);
        cardTitleContainer.setStyle("-fx-background-color: " + GlobalColor.colorToHex(color) + ";");
    }

    /**
     * Sets timePeriod value and everything related to display of event card's color. This consists of:
     * <ul>
     *     <li>Text displaying date and time.</li>
     * </ul>
     * The timePeriod of chapter and storyline is also reset accordingly.
     * @param timePeriod the timePeriod to be set.
     */
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

    /**
     * Resets timePeriod of chapter and storyline this event card is in. according to the event card's timePeriod.
     * @param timePeriod the timePeriod to be set.
     * @param component the component whose timePeriod is to be reset.
     */
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

    /**
     * Sets storyline value and everything related to display of event card's storyline. This consists of:
     * <ul>
     *     <li>Color of this event card.</li>
     * </ul>
     * @param storyline the storyline to be set.
     */
    public void setStorylineAndDisplay(Storyline storyline) {
        setStoryline(storyline);
        if (storyline != null) {
            setColorAndDisplay(storyline.getColor());
            setSelfComponentTimePeriod(getTimePeriod(), storyline);
        }
    }

    /**
     * Sets chapter value and everything related to display of event card's chapter. This consists of:
     * <ul>
     *     <li>Color of this event card's chapterTitleContainer. If null, color is set to white.</li>
     * </ul>
     * @param chapter the chapter to be set.
     */
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

    /**
     * Sets title value of chapter and everything related to display of title of this event card's chapter. This consists of:
     * <ul>
     *     <li>Text on chapterTitle.</li>
     * </ul>
     * @param title the title to be set.
     */
    public void setChapterTitleAndDisplay(String title) {
        getChapter().setTitle(title);
        chapterTitle.setText(title);
        ApplicationUtils.updateWorkspace();
    }

    /**
     * Overrides toString method.
     * @return title.
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * Defines how eventCards are to be compared. EventCards are compared by comparing their timePeriods.
     *
     * @param o the other eventCard compared to this eventCard.
     * @return the result of comparing their timePeriods.
     * @throws IllegalArgumentException Illegal argument used to invoke a method.
     */
    @Override
    public int compareTo(EventCard o) throws IllegalArgumentException {
        return getTimePeriod().compareTo(o.getTimePeriod());
    }

    /**
     * Converts an eventCard into a JSONObject.
     * @return the passed eventCard, in JSONObject form.
     */
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

    /**
     * Loads data in the JSONObject into an eventCard.
     * @param eventCardObject the JSONObject that is to be read.
     * @return an eventCard with data loaded from the eventCardObject parameter.
     */
    @Override
    public EventCard readJSONObject(JSONObject eventCardObject) {
        super.readJSONObject(eventCardObject);
        String selfChapterID = (String) eventCardObject.get("selfChapter");
        String selfStorylineID = (String) eventCardObject.get("selfStoryline");

        setChapter((Chapter) ApplicationUtils.getValueFromCurrentHashMap(selfChapterID));
        setStoryline((Storyline) ApplicationUtils.getValueFromCurrentHashMap(selfStorylineID));

        return this;
    }

    /**
     * Initializes everything related to this event card's display, after event card data is completely read from opened file.
     */
    private void initializeDisplayAfterRead() {
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    /**
     * Initializes context menu. There are 7 context menus.
     * <ol>
     *     <li><i>Edit Title Menu</i> to edit the event card's title.</li>
     *     <li><i>Edit Description Menu</i> to edit the event card's description.</li>
     *     <li><i>Edit Date and Time Menu</i> to edit the event card's time period.</li>
     *     <li><i>Edit Color</i> to edit the its Storyline color.</li>
     *     <li><i>Move to Chapter</i> to edit the event card's time period.</li>
     *     <li><i>Move to Storyline</i> to edit the event card's time period.</li>
     *     <li><i>Remove Menu</i> to remove this event card.</li>
     * </ol>
     */
    private void initializeContextMenu() {
        contextMenu.setAutoHide(true);
        contextMenu.setConsumeAutoHidingEvents(true);

        MenuItem editTitleMenuItem = new MenuItem(SystemUtils.EDIT_TITLE);
        editTitleMenuItem.setOnAction((ActionEvent event) -> new SetTitleDialog(this).show());

        MenuItem editDescriptionMenuItem = new MenuItem(SystemUtils.EDIT_DESCRIPTION);
        editDescriptionMenuItem.setOnAction((ActionEvent event) -> new SetDescriptionDialog(this).show());

        MenuItem editTimePeriodMenuItem = new MenuItem(SystemUtils.EDIT_DATA_TIME);
        editTimePeriodMenuItem.setOnAction((ActionEvent event) -> new SetTimePeriodDialog(this).show());

        MenuItem editColorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        editColorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(getStoryline()).show());

        MenuItem editChapterMenuItem = new MenuItem(SystemUtils.MOVE_TO_CHAPTER);
        editChapterMenuItem.setOnAction((ActionEvent event) -> new SetChapterDialog(this).show());

        MenuItem editStorylineMenuItem = new MenuItem(SystemUtils.MOVE_TO_STORYLINE);
        editStorylineMenuItem.setOnAction((ActionEvent event) -> new SetStorylineDialog(this).show());

        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());

        contextMenu.getItems().addAll(editTitleMenuItem, editDescriptionMenuItem, editTimePeriodMenuItem, editColorMenuItem, editChapterMenuItem, editStorylineMenuItem, removeMenuItem);
    }

    /**
     * Removes this event card from the document.
     */
    @Override
    public void removeItem() {
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeEventCard(this);
    }

    /**
     * FXML initialize method, called after EventCard.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Sets display of all fields.</li>
     *     <li>Setups context menu to display when right clicked, and hide when clicked elsewhere.</li>
     *     <li>Setups event card drag-drop.</li>
     *     <li>Setups card title, description, and chapter name to be editable when clicked.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        setTitleAndDisplay(getTitle());
        setColorAndDisplay(getColor());
        setTimePeriodAndDisplay(getTimePeriod());
        setDescriptionAndDisplay(getDescription());

        if (getChapter() == null) {
            chapterTitleContainer.setStyle("-fx-background-color: white;");
        } else {
            setChapterAndDisplay(getChapter());
        }
        chapterTitle.setPrefHeight(Region.USE_COMPUTED_SIZE);

        if (getStoryline() != null) {
            setStorylineAndDisplay(getStoryline());
        }

        root.setOnContextMenuRequested((ContextMenuEvent event) -> {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        root.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            }
            event.consume();
        });

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

        cardTitle.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        cardTitleContainer.setOnMouseClicked((MouseEvent event) -> cardTitle.setDisable(false));
        cardTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!getTitle().equals(cardTitle.getText())) {
                setTitleAndDisplay(cardTitle.getText());
                System.out.println("Card title set to " + getTitle());
            }
            cardTitle.setDisable(true);
        });

        cardDescription.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        cardDescriptionContainer.setOnMouseClicked((MouseEvent event) -> cardDescription.setDisable(false));
        cardDescriptionContainer.setOnMouseExited((MouseEvent event) -> {
            if (!getDescription().equals(cardDescription.getText())) {
                setDescriptionAndDisplay(cardDescription.getText());
                System.out.println("Card description set to " + getDescription());
            }
            cardDescription.setDisable(true);
        });

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
