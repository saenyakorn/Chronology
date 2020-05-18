package component.components.eventCard;

import component.ability.SavableAsJSONArray;
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

/**
 * An ObservableList of EventCards.
 * @see EventCard
 */
public class EventCardList implements Iterable<EventCard>, SavableAsJSONArray<EventCardList> {

    /**
     * ObservableList of EventCards.
     */
    private final ObservableList<EventCard> eventCards;
    /**
     * ObservableList of sorted EventCards.
     */
    private final SortedList<EventCard> sortedEventCards;

    /**
     * Constructor for EventCardList. Adds a listener to listen for when an event card is changed, and updates the storyline every time a change is detected.
     */
    public EventCardList() {
        eventCards = FXCollections.observableArrayList(eventCard -> new Observable[]{eventCard.timePeriodProperty(), eventCard.storylineProperty(), eventCard.chapterProperty()});
        sortedEventCards = new SortedList<>(eventCards, (item1, item2) -> sortByEventCardDate(item1, item2));
        sortedEventCards.addListener((ListChangeListener.Change<? extends EventCard> change) -> update());
    }

    /**
     * Updates the visualization of event cards in viewer. Shows all event cards sorted by time period.
     */
    public void update() {
        int counter = 0;
        for (EventCard eventCard : sortedEventCards) {
            eventCard.setIndex(-1);
            if (eventCard.getStoryline() != null) {
                eventCard.setIndex(counter++);
                eventCard.setChapterAndDisplay(eventCard.getChapter());
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

    /**
     * Getter for eventCards.
     * @return this eventCardList's ObservableList.
     */
    public ObservableList<EventCard> getEventCards() {
        return eventCards;
    }

    /**
     * Adds an event card to this eventCardList.
     * @param eventCard the eventCard to be added.
     */
    public void addEventCard(EventCard eventCard) {
        eventCards.add(eventCard);
    }

    /**
     * Removes an event card from this eventCardList.
     * @param eventCard the eventCard to be removed.
     */
    public void removeEventCard(EventCard eventCard) {
        if (eventCards.contains(eventCard)) {
            if (eventCard.getStoryline() != null) {
                Storyline storyline = eventCard.getStoryline();
                storyline.getContainer().getChildren().remove(eventCard.getDisplay());
            }
            eventCards.remove(eventCard);
        } else {
            System.out.println("This event card does not exist");
        }
    }

    /**
     * Gets ObservableList size.
     * @return list size.
     */
    public int size() {
        return eventCards.size();
    }

    /**
     * Getter for sortedEventCards.
     * @return this eventCardList's SortedList.
     */
    public SortedList<EventCard> getSortedEventCards() {
        return sortedEventCards;
    }

    /**
     * Sorts two event cards by date and time.
     * @param item1 the first event card.
     * @param item2 the second event card.
     * @return item1 compared to item2.
     */
    private int sortByEventCardDate(EventCard item1, EventCard item2) {
        return item1.compareTo(item2);
    }

    /**
     * Gets ObservableList's iterator.
     * @return the list's iterator.
     */
    @Override
    public Iterator<EventCard> iterator() {
        return eventCards.iterator();
    }

    /**
     * Overrides toString method.
     * @return titles of all event cards in eventCardList.
     */
    @Override
    public String toString() {
        String content = "";
        for (EventCard eventCard : eventCards) {
            content += eventCard + " ";
        }
        return content;
    }

    /**
     * Gets the JSON array in string format.
     * @return the JSON array converted to a string.
     */
    @Override
    public String getJSONString() {
        return this.writeJSONArray().toJSONString();
    }

    /**
     * Converts an eventCardList into a JSONArray.
     * @return the passed eventCardList, in JSONArray form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray eventCardArray = new JSONArray();
        for (EventCard eventCard : eventCards) {
            eventCardArray.add(eventCard.writeJSONObjectAsComponentID());
        }
        return eventCardArray;
    }

    /**
     * Loads data in the JSONArray into an eventCardList.
     * @param eventCardArray the JSONArray that is to be read.
     * @return an eventCardList with data loaded from the eventCardArray parameter.
     */
    @Override
    public EventCardList readJSONArray(JSONArray eventCardArray) {
        for (Object eventCardObject : eventCardArray) {
            this.addEventCard(EventCard.readJSONObjectAsComponentID((JSONObject) eventCardObject));
        }
        return this;
    }

}
