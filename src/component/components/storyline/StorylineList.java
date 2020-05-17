package component.components.storyline;

import component.ability.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

/**
 * An ObservableList of Storylines.
 * @see Storyline
 */
public class StorylineList implements Iterable<Storyline>, SavableAsJSONArray<StorylineList> {

    /**
     * ObservableList of Storylines.
     */
    private final ObservableList<Storyline> storylines;
    /**
     * ObservableList of Storyline panes.
     */
    private final ObservableList<Pane> storylinePanes;

    /**
     * Constructor for StorylineList.
     */
    public StorylineList() {
        storylines = FXCollections.observableArrayList();
        storylinePanes = FXCollections.observableArrayList();
    }

    /**
     * Getter for storylinePanes.
     * @return this storyline's storylinePanes.
     */
    public ObservableList<Pane> getStorylinePanes() {
        return storylinePanes;
    }

    /**
     * Getter for storylines.
     * @return this storyline's ObservableList.
     */
    public ObservableList<Storyline> getStorylines() {
        return storylines;
    }

    /**
     * Adds a storyline to this storylineList.
     * @param storyline the storyline to be added.
     */
    public void addStoryline(Storyline storyline) {
        storylines.add(storyline);
        storylinePanes.add(storyline.getDisplay());
    }

    /**
     * Removes a storyline from this storylineList.
     * @param storyline the storyline to be removed.
     */
    public void removeStoryline(Storyline storyline) {
        if (storylines.contains(storyline)) {
            storylines.remove(storyline);
            storylinePanes.remove(storyline.getDisplay());
        } else {
            System.out.println("This storyline does not exist");
        }
    }

    /**
     * Gets ObservableList size.
     * @return list size.
     */
    public int size() {
        return storylines.size();
    }

    /**
     * Gets ObservableList's iterator.
     * @return the list's iterator.
     */
    @Override
    public Iterator<Storyline> iterator() {
        return storylines.iterator();
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
     * Converts a storyline into a JSONArray.
     * @return the passed storyline, in JSONArray form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray storylineArray = new JSONArray();
        for (Storyline storyline : storylines) {
            storylineArray.add(storyline.writeJSONObjectAsComponentID());
        }
        return storylineArray;
    }

    /**
     * Loads data in the JSONArray into a storyline.
     * @param storylineArray the JSONArray that is to be read.
     * @return a storyline with data loaded from the storylineArray parameter.
     */
    @Override
    public StorylineList readJSONArray(JSONArray storylineArray) {
        for (Object storylineObject : storylineArray) {
            this.addStoryline(Storyline.readJSONObjectAsComponentID((JSONObject) storylineObject));
        }
        return this;
    }

}
