package component.components.eventCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EventCardList implements Iterable<EventCard> {
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

    public void addAllEventCards(EventCardList eventCards) {
        for(EventCard e : eventCards)
            this.eventCards.add(e);
        Collections.sort(this.eventCards);
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

    public int size() {
        return eventCards.size();
    }

    @Override
    public Iterator<EventCard> iterator() {
        return eventCards.iterator();
    }
}
