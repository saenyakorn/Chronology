package component.components.chapter;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

import java.io.IOException;

public class Chapter extends BasicStoryComponent {
    private final EventCardList eventCards;

    public Chapter() {
        eventCards = new EventCardList();
    }

    public Chapter(String title, String description) {
        super(title, description);
        eventCards = new EventCardList();
    }

    public Chapter(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        this.eventCards = new EventCardList();
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        eventCard.setChapter(this);
    }

    public void addAllEventCards(EventCard... args) {
        for (EventCard eventCard : args) {
            eventCards.addEventCard(eventCard);
            eventCard.setChapter(this);
        }
    }

    public EventCardList removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
        return eventCards;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chapter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
