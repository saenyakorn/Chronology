package component.components.storyline;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.timeModifier.TimePeriod;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Storyline extends BasicStoryComponent {

    public ArrayList<EventCard> eventCards;

    public Storyline() {
        super();
        eventCards = new ArrayList<>();
        this.loadFXML();
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        eventCards = new ArrayList<>();
        this.loadFXML();
    }

    public ArrayList<EventCard> addEventCard(EventCard eventCard) {
        eventCards.add(eventCard);
        Collections.sort(eventCards);
        return eventCards;
    }

    public ArrayList<EventCard> addAllEventCards(ArrayList<EventCard> eventCards) {
        this.eventCards.addAll(eventCards);
        Collections.sort(eventCards);
        return eventCards;
    }

    public ArrayList<EventCard> removeEventCard(EventCard eventCard) {
        eventCards.remove(eventCard);
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Storyline.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
