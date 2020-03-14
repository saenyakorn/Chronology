package elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Chapter extends BasicStoryElement {
    private ArrayList<EventCard> eventCards;

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
        for(EventCard eventCard: eventCards){
            eventCards.add(eventCard);
        }
        Collections.sort(eventCards);
        return eventCards;
    }

    public ArrayList<EventCard> removeEventCard(EventCard eventCard) {
        eventCards.remove(eventCard);
        return eventCards;
    }

    @Override
    public String toString() {
        return null;
    }
}