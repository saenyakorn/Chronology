package component.components.eventCard;

import component.SavableAsJSONArray;
import javafx.scene.layout.HBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EventCardList extends HBox implements Iterable<EventCard>, SavableAsJSONArray {
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

    @Override
    public String getJSONString() {
        return this.getJSONArray().toJSONString();
    }

    @Override @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray eventCardList = new JSONArray();
        for(EventCard eventCard : eventCards) {
            eventCardList.add(eventCard.getJSONObject());
        }
        return eventCardList;
    }

    @SuppressWarnings("unchecked")
    public static EventCardList parseJSONArray(JSONArray eventCardArray) {
        EventCardList eventCards = new EventCardList();
        for(Object eventCardObject : eventCardArray) {
            EventCard eventCard = EventCard.parseJSONObject((JSONObject) eventCardObject);
            eventCards.addEventCard(eventCard);
        }
        return eventCards;
    }

}
