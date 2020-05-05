package component.components.eventCard;

import ablity.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
        eventCards = FXCollections.observableArrayList();
        eventCards.addListener((ListChangeListener.Change<? extends EventCard> change) -> {
            System.out.println("Change on eventCardList");
            while (change.next()) {
                System.out.println("EventCard -> " + change);
                if (change.wasUpdated()) {
                    System.out.println("Update detected");
                }
            }
        });
        sortedEventCards = new SortedList<>(eventCards, (item1, item2) -> sortByEventCardDate(item1, item2));
    }

    public ObservableList<EventCard> getEventCards() {
        return eventCards;
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

    public SortedList<EventCard> getSortedEventCards() {
        return sortedEventCards;
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
        return this.writeJSONArray().toJSONString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray eventCardArray = new JSONArray();
        for (EventCard eventCard : eventCards) {
            eventCardArray.add(eventCard.getJSONObjectAsComponentID());
        }
        return eventCardArray;
    }

    public static EventCardList readJSONArray(JSONArray eventCardArray) {
        EventCardList eventCards = new EventCardList();
        for (Object eventCardObject : eventCardArray) {
            EventCard eventCard = EventCard.readJSONObject((JSONObject) eventCardObject);
            eventCards.addEventCard(eventCard);
        }
        return eventCards;
    }

    private int sortByUserSelection(EventCard item1, EventCard item2) {
        return 0; // TODO: implement how to sort
    }

}
