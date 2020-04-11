package component.components.storyline;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;

public class Storyline extends BasicStoryComponent {
    private EventCardList eventCards;

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
        eventCards.addEventCard(new EventCard());
        this.loadFXML();
    }

    public Storyline(String title, String description) {
        super(title, description);
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        eventCards = new EventCardList();
        eventCards.addEventCard(new EventCard());
        this.loadFXML();
    }

    @FXML
    public void initialize() {
        this.setTitle(this.getTitle());
        this.setColor(this.getColor());
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        storylineTitle.setText(title);
        storylineTitle.setDisable(true);
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> storylineTitle.setDisable(true));
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        line.setFill(color);
        storylineTitle.setStyle("-fx-fill: " + colorToHex(color) + ";");
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        eventCard.setStoryLine(this);
        eventCardList.getChildren().add(eventCard);
    }

    public void addAllEventCards(EventCard... args) {
        for (EventCard eventCard : args) {
            eventCards.addEventCard(eventCard);
            eventCard.setStoryLine(this);
            eventCardList.getChildren().add(eventCard);
        }
    }

    public EventCardList removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
        eventCardList.getChildren().remove(eventCard);
        return eventCards;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Storyline.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()));
    }
}
