package component.components.storyline;

import ablity.SavableAsJSONArray;
import component.components.eventCard.EventCard;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StorylineList implements Iterable<Storyline>, SavableAsJSONArray {
    private final ObservableList<Storyline> storylines;
    private final ObservableList<EventCard> allEventCard;
    private final SortedList<EventCard> allSortedEventCard;

    public StorylineList() {
        storylines = FXCollections.observableArrayList();
        allEventCard = FXCollections.observableArrayList(eventCard -> new Observable[]{eventCard.getTimePeriod()});
        allSortedEventCard = new SortedList<>(allEventCard, (item1, item2) -> sortByEventCardDate(item1, item2));

        // On change event
        allSortedEventCard.addListener((ListChangeListener.Change<? extends EventCard> change) -> {
            for (int i = 0; i < allSortedEventCard.size(); i++) {
                allSortedEventCard.get(i).setIndex(i);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static StorylineList parseJSONArray(JSONArray storylineArray) {
        StorylineList storylines = new StorylineList();
        for (Object storylineObject : storylineArray) {
            Storyline storyline = Storyline.parseJSONObject((JSONObject) storylineObject);
            storylines.addStoryline(storyline);
        }
        return storylines;
    }

    public ObservableList<Storyline> getStorylines() {
        return storylines;
    }

    public void addStoryline(Storyline storyline) {
        storylines.add(storyline);
    }

    public void addAllStoryline(ArrayList<Storyline> storylines) {
        this.storylines.addAll(storylines);
    }

    public void removeStoryline(Storyline storyline) {
        if (storylines.contains(storyline)) {
            storylines.remove(storyline);
        } else {
            System.out.println("This storyline does not exist");
        }
    }

    public int size() {
        return storylines.size();
    }

    @Override
    public Iterator<Storyline> iterator() {
        return storylines.iterator();
    }

    @Override
    public String getJSONString() {
        return this.getJSONArray().toJSONString();
    }

    private int sortByEventCardDate(EventCard item1, EventCard item2) {
        return item1.compareTo(item2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray storylineArray = new JSONArray();
        for (Storyline storyline : storylines) {
            storylineArray.add(storyline.getJSONObject());
        }
        return storylineArray;
    }

}
