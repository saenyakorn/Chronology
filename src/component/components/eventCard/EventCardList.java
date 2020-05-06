package component.components.eventCard;

import ability.Savable;
import ability.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.layout.HBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class EventCardList extends HBox implements Iterable<EventCard>, SavableAsJSONArray<EventCardList> {
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
            eventCardArray.add(eventCard.writeJSONObjectAsComponentID());
        }
        return eventCardArray;
    }

    @Override
    public EventCardList readJSONArray(JSONArray eventCardArray) {
        Savable.printReadingMessage("eventCardList");
        for (Object eventCardObject : eventCardArray) {
            System.out.println("Populating eventCardList");
            eventCards.add((new EventCard()).readJSONObject((JSONObject) eventCardObject));
            //null null problem due to here, because eventCard object called from hashmap has only componentID
            // TODO : Different method when called from hash map (add current event card instead of a new one?)
        }
        Savable.printReadingFinishedMessage("eventCardList");
        return this;
    }

    private int sortByUserSelection(EventCard item1, EventCard item2) {
        return 0; // TODO: implement how to sort
    }

}
