package component.components.eventCard;

import component.ability.SavableAsJSONArray;
import component.base.BasicStoryComponent;
import component.components.storyline.Storyline;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.layout.GridPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

import java.util.Iterator;

public class EventCardList implements Iterable<EventCard>, SavableAsJSONArray<EventCardList> {
    private final ObservableList<EventCard> eventCards;
    private final SortedList<EventCard> sortedEventCards;

    public EventCardList() {
        eventCards = FXCollections.observableArrayList(eventCard -> new Observable[]{eventCard.getTimePeriodProperty(), eventCard.storylineProperty(), eventCard.chapterProperty()});
        sortedEventCards = new SortedList<>(eventCards, (item1, item2) -> sortByEventCardDate(item1, item2));
        sortedEventCards.addListener((ListChangeListener.Change<? extends EventCard> change) -> update());
    }

    public void update() {
        int counter = 0;
        for (EventCard eventCard : sortedEventCards) {
            eventCard.setIndex(-1);
            if (eventCard.getStoryline() != null) {
                eventCard.setIndex(counter++);
                eventCard.getStoryline().getContainer().getChildren().clear(); // clear gridPane
                eventCard.getStoryline().resetMinMaxIndex();
            }
        }
        for (EventCard eventCard : sortedEventCards) {
            if (eventCard.getStoryline() != null) {
                Storyline focusedStoryline = eventCard.getStoryline();
                GridPane focusedGridPane = focusedStoryline.getContainer();
                focusedStoryline.setMinIndex(eventCard.getIndex());
                focusedStoryline.setMaxIndex(eventCard.getIndex());
                focusedGridPane.add(eventCard.getDisplay(), eventCard.getIndex(), 0);
            }
        }
        for (Storyline storyline : ApplicationUtils.getCurrentWorkspace().getActiveDocument().getStorylines()) {
            storyline.modifyStorylineStructure();
        }
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

    public void clear() {
        eventCards.clear();
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
        for (Object eventCardObject : eventCardArray) {
            eventCards.add((EventCard) BasicStoryComponent.readJSONObjectAsComponentID((JSONObject) eventCardObject));
        }
        return this;
    }

    private int sortByUserSelection(EventCard item1, EventCard item2) {
        return 0; // TODO: implement how to sort
    }

}
