package component.components.storyline;

import component.ability.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class StorylineList implements Iterable<Storyline>, SavableAsJSONArray<StorylineList> {
    private final ObservableList<Storyline> storylines;
    private final ObservableList<Pane> storylinePanes;

    public StorylineList() {
        storylines = FXCollections.observableArrayList();
        storylinePanes = FXCollections.observableArrayList();
    }

    public ObservableList<Pane> getStorylinePanes() {
        return storylinePanes;
    }

    public ObservableList<Storyline> getStorylines() {
        return storylines;
    }

    public void addStoryline(Storyline storyline) {
        storylines.add(storyline);
        storylinePanes.add(storyline.getDisplay());
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

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray storylineArray = new JSONArray();
        for (Storyline storyline : storylines) {
            storylineArray.add(storyline.writeJSONObjectAsComponentID());
        }
        return storylineArray;
    }

    @Override
    public StorylineList readJSONArray(JSONArray storylineArray) {
        for (Object storylineObject : storylineArray) {
            this.addStoryline(Storyline.readJSONObjectAsComponentID((JSONObject) storylineObject));
        }
        return this;
    }

}
