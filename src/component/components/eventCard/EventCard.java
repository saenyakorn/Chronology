package component.components.eventCard;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private Storyline selfStoryLine;
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

    public EventCard() {
        selfChapter = new Chapter();
        selfStoryLine = new Storyline();
        this.loadFXML();
    }

    public EventCard(Storyline storyline) {
        selfStoryLine = storyline;
        selfChapter = null;
        this.loadFXML();
    }

    public EventCard(Storyline storyline, Chapter chapter) {
        selfStoryLine = storyline;
        selfChapter = chapter;
        this.loadFXML();
    }

    public Storyline getStoryLine() {
        return selfStoryLine;
    }

    public void setStoryLine(Storyline selfStoryLine) {
        this.selfStoryLine = selfStoryLine;
    }

    public Chapter getChapter() {
        return selfChapter;
    }

    public void setChapter(Chapter selfChapter) {
        this.selfChapter = selfChapter;
    }

    public EventCard(String title, String description, Color color, TimePeriod timePeriod, String characters, String place) {
        super(title, description, color, timePeriod);
        this.loadFXML();
    }


    @FXML
    public void initialize() {
        this.setTitle(this.getTitle());
        this.setDescription(this.getDescription());
        this.setColor(this.getColor());
        this.setTimePeriod(this.getTimePeriod());
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        cardTitle.setText(title);
        cardTitle.setDisable(true);
        cardTitleContainer.setOnMouseClicked((MouseEvent event) -> cardTitle.setDisable(false));
        cardTitleContainer.setOnMouseExited((MouseEvent event) -> cardTitle.setDisable(true));
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
        cardDescription.setText(description);
        cardDescription.setDisable(true);
        cardDescriptionContainer.setOnMouseClicked((MouseEvent event) -> cardDescription.setDisable(false));
        cardDescriptionContainer.setOnMouseExited((MouseEvent event) -> cardDescription.setDisable(true));
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        date.setFill(color);
        time.setFill(color);
        cardTitleContainer.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    @Override
    public void setTimePeriod(TimePeriod timePeriod) {
        super.setTimePeriod(timePeriod);
        date.setText(timePeriod.getBeginDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        time.setText(String.valueOf(timePeriod.getBeginDateTime().toLocalTime()));
        //dateTimeContainer.setOnMouseClicked((MouseEvent event) ->
        // TODO : Set TimePeriod dialog
        //  );
    }

    @Override
    public String toString() {
        return name;
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

}
