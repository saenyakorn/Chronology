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

    public Chapter(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        this.eventCards = new EventCardList();
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public EventCardList addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        return eventCards;
    }

    public EventCardList addAllEventCards(EventCardList eventCards) {
        this.eventCards.addAllEventCards(eventCards);
        return eventCards;
    }

    public EventCardList removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
        return eventCards;
    }

    @Override
    public String toString() {
        return "Chapter [" + title + "]\n"
                + "\tdescription: " + description + "\n"
                + "\tcolor: " + color + "\n"
                + "\ttime period: " + timePeriod + "\n"
                + "\tevent card: " + eventCards.size() + "\n";
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
