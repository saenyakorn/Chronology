package component.components.storyline;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.timeModifier.TimePeriod;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Storyline extends BasicStoryComponent {

    public ArrayList<EventCard> eventCards;

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        eventCards = new ArrayList<>();
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
}
