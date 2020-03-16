package components.eventCard;

import java.util.ArrayList;
import java.util.Collections;

public class EventCardList {
    private final ArrayList<EventCard> eventCards;

    public EventCardList(ArrayList<EventCard> eventCards) {
        this.eventCards = eventCards;
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
            System.out.println("This event card is not exist");
            return 0;
        }
    }
}
