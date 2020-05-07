package component.components.eventCard;

import ablity.SavableAsJSONArray;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.layout.HBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class EventCardList extends HBox implements Iterable<EventCard>, SavableAsJSONArray {
    private final ObservableList<EventCard> eventCards;
    private final SortedList<EventCard> sortedEventCards;

    public EventCardList() {
        eventCards = FXCollections.observableArrayList(eventCard -> new Observable[]{eventCard.getTimePeriod()});
        sortedEventCards = new SortedList<>(eventCards, (item1, item2) -> sortByEventCardDate(item1, item2));
    }

    @SuppressWarnings("unchecked")
    public static EventCardList parseJSONArray(JSONArray eventCardArray) {
        EventCardList eventCards = new EventCardList();
        for (Object eventCardObject : eventCardArray) {
            EventCard eventCard = EventCard.parseJSONObject((JSONObject) eventCardObject);
            eventCards.addEventCard(eventCard);
        }
        return eventCards;
    }

    public ObservableList<EventCard> getEventCards() {
        return eventCards;
    }

    public SortedList<EventCard> getSortedEventCards() {
        return sortedEventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.add(eventCard);
    }

    public void removeEventCard(EventCard eventCard) {
        if (eventCards.contains(eventCard)) {
            eventCards.remove(eventCard);
        } else {
            System.out.println("This event card does not exist");
        }
    }

    public int size() {
        return eventCards.size();
    }

    private int sortByEventCardDate(EventCard item1, EventCard item2) {
        return item1.compareTo(item2);
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

    private int sortByUserSelection(EventCard item1, EventCard item2) {
        return 0; // TODO: implement how to sort
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray eventCardArray = new JSONArray();
        for (EventCard eventCard : eventCards) {
            eventCardArray.add(eventCard.getJSONObject());
        }
        return eventCardArray;
    }

}
