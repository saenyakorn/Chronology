package component.components.eventCard;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import component.dialog.SetColorDialog;
import component.dialog.SetTimePeriodDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private Storyline selfStoryline;
    private Chapter selfChapter;

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
        this.loadFXML();
    }

    public EventCard(String title, String description) {
        super(title, description);
        selfChapter = null;
        selfStoryline = null;
        this.loadFXML();
    }

    public EventCard(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        this.loadFXML();
    }

    @FXML
    public void initialize() {
        this.setTitle(this.getTitle());
        cardTitle.setDisable(true);
        cardTitleContainer.setOnMouseClicked((MouseEvent event) -> cardTitle.setDisable(false));
        cardTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if(!title.equals(cardTitle.getText())){
                this.setTitle(cardTitle.getText());
                System.out.println("Card title set to " + title);
            }
            cardTitle.setDisable(true);
        });

        this.setDescription(this.getDescription());
        cardDescription.setDisable(true);
        cardDescriptionContainer.setOnMouseClicked((MouseEvent event) -> cardDescription.setDisable(false));
        cardDescriptionContainer.setOnMouseExited((MouseEvent event) -> {
            if(!description.equals(cardDescription.getText())){
                this.setDescription(cardDescription.getText());
                System.out.println("Card description set to " + description);
            }
            cardDescription.setDisable(true);
        });

        this.setColor(this.getColor());
        this.setTimePeriod(this.getTimePeriod());
        initializeContextMenu();

        if(selfChapter == null){
            chapterMarker.setStyle("-fx-background-color: " + SystemConstants.WHITE + ";");
        }
        else {
            setChapter(this.getChapter());
        }
    }

    public Storyline getStoryline() {
        return selfStoryline;
    }

    public void setStoryline(Storyline selfStoryLine) {
        this.selfStoryline = selfStoryLine;
        this.setColor(selfStoryLine.getColor());
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
        if(selfStoryline != null)
            setSelfComponentTimePeriod(timePeriod, selfStoryline);
        if(selfChapter != null)
            setSelfComponentTimePeriod(timePeriod, selfChapter);

        date.setText(timePeriod.getBeginDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        time.setText(timePeriod.getBeginDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        //selfStoryline.renderEventCards();
    }

    public void setSelfComponentTimePeriod (TimePeriod timePeriod, BasicStoryComponent component) {
        LocalDateTime newBeginDateTime = timePeriod.getBeginDateTime();
        LocalDateTime newEndDateTime = timePeriod.getEndDateTime();

        if(newBeginDateTime.isBefore(component.getTimePeriod().getBeginDateTime())) {
            component.getTimePeriod().setBeginDateTime(newBeginDateTime);
            System.out.println("Storyline BeginDateTime set to " + component.getTimePeriod().getBeginDateTime());

        }
        if(newEndDateTime.isAfter(component.getTimePeriod().getEndDateTime())) {
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

    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EventCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem setTimePeriodMenu = new MenuItem("Edit event date/time");
        MenuItem setColorMenu = new MenuItem("Edit storyline color");
        contextMenu.getItems().addAll(setTimePeriodMenu,setColorMenu);
        setTimePeriodMenu.setOnAction((ActionEvent event) -> {
            SetTimePeriodDialog dialog = new SetTimePeriodDialog(this);
            dialog.show();
        });
        setColorMenu.setOnAction((ActionEvent event) ->{
            SetColorDialog dialog = new SetColorDialog(this.selfStoryline);
            dialog.show();
        });

        dateTimeContainer.setOnMousePressed((MouseEvent event) -> {
            if (event.isSecondaryButtonDown()) {
                contextMenu.show(dateTimeContainer, event.getScreenX(), event.getScreenY());
            }
        });
    }

    public boolean clickInEventCard(MouseEvent event) {
        return this.contains(this.screenToLocal(event.getScreenX(), event.getScreenY()));
    }

}
