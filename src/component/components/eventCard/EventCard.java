package component.components.eventCard;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import component.dialog.SetColorDialog;
import component.dialog.SetTimePeriodDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private Storyline selfStoryline;
    private Chapter selfChapter;
    private ContextMenu contextMenu;

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
        selfChapter = null;
        selfStoryline = null;
        loadFXML("EventCard.fxml");
        initializeEventHandler();
        initializeContextMenu();
    }

    public EventCard(String title, String description) {
        super(title, description);
        selfChapter = null;
        selfStoryline = null;
        loadFXML("EventCard.fxml");
        initializeEventHandler();
    }

    public EventCard(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        selfChapter = null;
        selfStoryline = null;
        loadFXML("EventCard.fxml");
        initializeEventHandler();
    }

    @FXML
    public void initialize() {
        setTitle(getTitle());
        setColor(getColor());
        setTimePeriod(getTimePeriod());
        setDescription(getDescription());
        cardTitle.setDisable(true);
        cardTitleContainer.setOnMouseClicked((MouseEvent event) -> cardTitle.setDisable(false));
        cardTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!title.equals(cardTitle.getText())) {
                this.setTitle(cardTitle.getText());
                System.out.println("Card title set to " + title);
            }
            cardTitle.setDisable(true);
        });
        cardDescription.setDisable(true);
        cardDescriptionContainer.setOnMouseClicked((MouseEvent event) -> cardDescription.setDisable(false));
        cardDescriptionContainer.setOnMouseExited((MouseEvent event) -> {
            if (!description.equals(cardDescription.getText())) {
                this.setDescription(cardDescription.getText());
                System.out.println("Card description set to " + description);
            }
            cardDescription.setDisable(true);
        });
        dateTimeContainer.setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));

        if (selfChapter == null) {
            chapterMarker.setStyle("-fx-background-color: white ;");
        } else {
            setChapter(getChapter());
        }
    }

    public Storyline getStoryline() {
        return selfStoryline;
    }

    public void setStoryline(Storyline selfStoryLine) {
        selfStoryline = selfStoryLine;
        setColor(selfStoryLine.getColor());
        setSelfComponentTimePeriod(timePeriod, selfStoryLine); //no null case
    }

    public Chapter getChapter() {
        return selfChapter;
    }

    public void setChapter(Chapter selfChapter) {
        this.selfChapter = selfChapter;
        //chapterMarker.setStyle("-fx-background-color: " + colorToHex(selfChapter.getColor()) + ";");
        setSelfComponentTimePeriod(timePeriod, selfChapter); //no null case
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
        cardTitleContainer.setStyle("-fx-background-color: " + colorToHex(color) + ";");
    }

    @Override
    public void setTimePeriod(TimePeriod timePeriod) {
        super.setTimePeriod(timePeriod);
        if (selfStoryline != null) {
            setSelfComponentTimePeriod(timePeriod, selfStoryline);
            selfStoryline.getEventCards().sortEventCards();
            selfStoryline.renderEventCards();
        }
        if (selfChapter != null)
            setSelfComponentTimePeriod(timePeriod, selfChapter);

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
        return title;
    }

    @Override
    public int compareTo(EventCard o) throws IllegalArgumentException {
        return timePeriod.compareTo(o.timePeriod);
    }

    @Override @SuppressWarnings("unchecked")
    public JSONObject getJSONObject() {
        JSONObject eventCard = super.getJSONObject();
        eventCard.put("selfChapter", Objects.toString(selfChapter));
        eventCard.put("selfStoryline", Objects.toString(selfChapter));
        return eventCard;
    }

    private void rightClickContextMenu(MouseEvent event) {
        System.out.println("EventCard: " + event.getTarget());
        if (contextMenu.isShowing()) {
            contextMenu.hide();
        }
        if (event.isSecondaryButtonDown()) {
            contextMenu.show(dateTimeContainer, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
        event.consume();
    }

    private void initializeContextMenu() {
        contextMenu = new ContextMenu();
        MenuItem timePeriodMenuItem = new MenuItem("Edit event date/time");
        MenuItem colorMenuItem = new MenuItem("Edit storyline color");
        timePeriodMenuItem.setOnAction((ActionEvent event) -> new SetTimePeriodDialog(this).show());
        colorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(selfStoryline).show());
        contextMenu.getItems().addAll(timePeriodMenuItem, colorMenuItem);
    }

    private void initializeEventHandler() {
        setOnDragDetected((MouseEvent event) -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(getComponentId());
            dragboard.setContent(clipboardContent);
            event.consume();
        });
    }

}
