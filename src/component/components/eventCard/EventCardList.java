package component.components.eventCard;

import java.util.ArrayList;
import java.util.Collections;

public class EventCardList {
    private final ArrayList<EventCard> eventCards;

    public EventCardList() {
        this.eventCards = new ArrayList<>();
    }

    public ArrayList<EventCard> getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.add(eventCard);
        Collections.sort(eventCards);
    }

    public int removeEventCard(EventCard eventCard) {
        if(eventCards.contains(eventCard)) {
            eventCards.remove(eventCard);
            return 1;
        }
        else {
            System.out.println("This event card does not exist");
            return 0;
        }
    }
}
