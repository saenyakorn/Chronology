package component.components.eventCard;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EventCardList extends HBox implements Iterable<EventCard> {
    private final ArrayList<EventCard> eventCards;

    public EventCardList() {
        this.eventCards = new ArrayList<>();
    }

    public ArrayList<EventCard> getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.add(eventCard);
        sortEventCards();
    }

    public void removeEventCard(EventCard eventCard) {
        if (eventCards.contains(eventCard)) {
            eventCards.remove(eventCard);
        } else {
            System.out.println("This event card does not exist");
        }
    }

    public void sortEventCards() {
        Collections.sort(eventCards);
    }

    public int size() {
        return eventCards.size();
    }

    @Override
    public Iterator<EventCard> iterator() {
        return eventCards.iterator();
    }

    @Override
    public String toString() {
        String content = "";
        for (EventCard eventCard : eventCards) {
            content += eventCard + " ";
        }
        return content;
    }
}
