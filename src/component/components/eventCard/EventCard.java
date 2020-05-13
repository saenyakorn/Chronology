package component.components.eventCard;

import colors.GlobalColor;
import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import component.dialog.SetColorDialog;
import component.dialog.SetTimePeriodDialog;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private StackPane chapterMarker;

    public EventCard() {
        loadFXML("EventCard.fxml");
        initializeContextMenu();
    }

    public EventCard(String componentID) {
        super(componentID);
        loadFXML("EventCard.fxml");
        initializeContextMenu();
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
        if (storyline != null) {
            setColor(storyline.getColor());
            setSelfComponentTimePeriod(getTimePeriod(), storyline);
        }
    }

    public Property<Chapter> chapterProperty() {
        return selfChapter;
    }

    public Chapter getChapter() {
        return selfChapter.getValue();
    }

    public void setChapter(Chapter chapter) {
        this.selfChapter.setValue(chapter);
        if (chapter != null) {
            setChapterColor(chapter.getColor());
            setSelfComponentTimePeriod(getTimePeriod(), chapter);
        }
    }

    public void setChapterColor(Color color) {
        chapterMarker.setStyle("-fx-background-color: " + GlobalColor.colorToHex(color) + ";");
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        cardTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
        cardDescription.setText(description);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        date.setFill(color);
        time.setFill(color);
        cardTitleContainer.setStyle("-fx-background-color: " + GlobalColor.colorToHex(color) + ";");
    }

    @Override
    public void setTimePeriod(TimePeriod timePeriod) {
        super.setTimePeriod(timePeriod);
        if (storylineProperty().getValue() != null) {
            setSelfComponentTimePeriod(timePeriod, storylineProperty().getValue());
        }
        if (chapterProperty().getValue() != null)
            setSelfComponentTimePeriod(timePeriod, chapterProperty().getValue());

        date.setText(timePeriod.getBeginDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        time.setText(timePeriod.getBeginDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public void setSelfComponentTimePeriod(TimePeriod timePeriod, BasicStoryComponent component) {
        LocalDateTime newBeginDateTime = timePeriod.getBeginDateTime();
        LocalDateTime newEndDateTime = timePeriod.getEndDateTime();

        if (newBeginDateTime.isBefore(component.getTimePeriod().getBeginDateTime())) {
            component.getTimePeriod().setBeginDateTime(newBeginDateTime);
            System.out.println("Storyline BeginDateTime set to " + component.getTimePeriod().getBeginDateTime());

        }
        if (newEndDateTime.isAfter(component.getTimePeriod().getEndDateTime())) {
            component.getTimePeriod().setEndDateTime(newEndDateTime);
            System.out.println("Storyline EndDateTime set to " + component.getTimePeriod().getEndDateTime());
        }
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

    private void initializeContextMenu() {
        contextMenu.setAutoHide(true);
        contextMenu.setConsumeAutoHidingEvents(true);
        MenuItem timePeriodMenuItem = new MenuItem(SystemUtils.EDIT_DATA_TIME);
        MenuItem colorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        timePeriodMenuItem.setOnAction((ActionEvent event) -> new SetTimePeriodDialog(this).show());
        colorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(storylineProperty().getValue()).show());
        contextMenu.getItems().addAll(timePeriodMenuItem, colorMenuItem);
    }

    @FXML
    public void initialize() {
        // toggle override setter
        setTitle(getTitle());
        setColor(getColor());
        setTimePeriod(getTimePeriod());
        setDescription(getDescription());

        // if notHasChapter set bottom color to white else set bottom color according to chapter color
        if (getChapter() == null) {
            chapterMarker.setStyle("-fx-background-color: white;");
        } else {
            setChapter(getChapter());
        }

        // When Event Card get right click
        root.setOnContextMenuRequested((ContextMenuEvent event) -> {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
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

        // When Click the card title to change text
        cardTitleContainer.setOnMouseClicked((MouseEvent event) -> cardTitle.setDisable(false));
        cardTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!getTitle().equals(cardTitle.getText())) {
                setTitle(cardTitle.getText());
                System.out.println("Card title set to " + getTitle());
            }
            cardTitle.setDisable(true);
        });

        // When Click the card description to change text
        cardDescriptionContainer.setOnMouseClicked((MouseEvent event) -> cardDescription.setDisable(false));
        cardDescriptionContainer.setOnMouseExited((MouseEvent event) -> {
            if (!getDescription().equals(cardDescription.getText())) {
                setDescription(cardDescription.getText());
                System.out.println("Card description set to " + getDescription());
            }
            cardDescription.setDisable(true);
        });
    }
}
