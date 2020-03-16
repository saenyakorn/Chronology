package component.components.chapter;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import elements.TimePeriod;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Chapter extends BasicStoryComponent {
    private final ArrayList<EventCard> eventCards;

    public Chapter(String title, String description, Color color, TimePeriod timePeriod, ArrayList<EventCard> eventCards) {
        super(title, description, color, timePeriod);
        this.eventCards = eventCards;
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
