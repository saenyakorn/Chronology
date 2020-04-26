package component.components.storyline;

import component.SavableAsJSONArray;
import component.components.eventCard.EventCard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StorylineList implements Iterable<Storyline>, SavableAsJSONArray {
    private final ArrayList<Storyline> storylines;

    public StorylineList() {
        this.storylines = new ArrayList<>();
    }

    public ArrayList<Storyline> getStorylines() {
        return storylines;
    }

    public void addStoryline(Storyline storyline) {
        storylines.add(storyline);
    }

    public void addAllStoryline(ArrayList<Storyline> storylines) {
        this.storylines.addAll(storylines);
    }

    public void addEventCard(EventCard eventCard) {
        int index = storylines.indexOf(eventCard.getStoryline());
        Storyline selectedStoryLine = storylines.get(index);
        selectedStoryLine.addEventCard(eventCard);
        storylines.set(index, selectedStoryLine);
    }

    public Storyline removeStoryline(Storyline storyline) {
        if (storylines.contains(storyline)) {
            storylines.remove(storyline);
            return storyline;
        } else {
            System.out.println("This storyline does not exist");
            return null;
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

    @Override @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray storylineList = new JSONArray();
        for(Storyline storyline : storylines) {
            storylineList.add(storyline.getJSONObject());
        }
        return storylineList;
    }

    @SuppressWarnings("unchecked")
    public static StorylineList parseJSONArray(JSONArray storylineArray) {
        StorylineList storylines = new StorylineList();
        for(Object storylineObject : storylineArray) {
            Storyline storyline = Storyline.parseJSONObject((JSONObject) storylineObject);
            storylines.addStoryline(storyline);
        }
        return storylines;
    }

}
