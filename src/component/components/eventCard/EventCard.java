package component.components.eventCard;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
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
    }

    public EventCard(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        this.loadFXML();
    }

    public Storyline getStoryline() {
        return selfStoryline;
    }

    public void setStoryline(Storyline selfStoryLine) {
        this.selfStoryline = selfStoryLine;
        this.setColor(selfStoryLine.getColor());
    }

    public Chapter getChapter() {
        return selfChapter;
    }

    public void setChapter(Chapter selfChapter) {
        this.selfChapter = selfChapter;
        chapterMarker.setStyle("-fx-background-color: " + colorToHex(selfChapter.getColor()) + ";");
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
        date.setText(timePeriod.getBeginDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        time.setText(String.valueOf(timePeriod.getBeginDateTime().toLocalTime()));
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
        MenuItem setTimePeriodMenu = new MenuItem("Set event date/time");
        contextMenu.getItems().add(setTimePeriodMenu);
        setTimePeriodMenu.setOnAction((ActionEvent event) ->{
            SetTimePeriodDialog dialog = new SetTimePeriodDialog();
            dialog.show();
        });
        dateTimeContainer.setOnMousePressed((MouseEvent event) ->{
            if (event.isSecondaryButtonDown()) {
                contextMenu.show(dateTimeContainer, event.getScreenX(), event.getScreenY());
            }
        });
    }
}
