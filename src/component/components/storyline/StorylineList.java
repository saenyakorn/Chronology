package component.components.storyline;

import ablity.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class StorylineList implements Iterable<Storyline>, SavableAsJSONArray {
    private final ObservableList<Storyline> storylines;

    public StorylineList() {
        storylines = FXCollections.observableArrayList();
        storylines.addListener((ListChangeListener.Change<? extends Storyline> change) -> {
            System.out.println("Change on storylineList");
            while (change.next()) {
                System.out.println("Storyline -> " + change);
                if (change.wasUpdated()) {
                    System.out.println("Update detected");
                }
            }
        });
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
        return this.writeJSONArray().toJSONString();
    }

    @Override @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray storylineArray = new JSONArray();
        for(Storyline storyline : storylines) {
            storylineArray.add(storyline.getJSONObjectAsComponentID());
        }
        return storylineArray;
    }

    public static StorylineList readJSONArray(JSONArray storylineArray) {
        StorylineList storylines = new StorylineList();
        for(Object storylineObject : storylineArray) {
            Storyline storyline = Storyline.readJSONObject((JSONObject) storylineObject);
            storylines.addStoryline(storyline);
        }
        return storylines;
    }

}
