package component.components.chapter;

import component.ability.SavableAsJSONArray;
import component.components.eventCard.EventCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

import java.util.Iterator;

/**
 * An ObservableList of Chapters.
 * @see Chapter
 */
public class ChapterList implements Iterable<Chapter>, SavableAsJSONArray<ChapterList> {

    /**
     * ObservableList of Chapters.
     */
    private final ObservableList<Chapter> chapters;

    /**
     * Constructor for ChapterList.
     */
    public ChapterList() {
        chapters = FXCollections.observableArrayList();
    }

    /**
     * Getter for chapters.
     * @return this chapterList's ObservableList
     */
    public ObservableList<Chapter> getChapters() {
        return chapters;
    }

    /**
     * Gets ObservableList size.
     * @return list size.
     */
    public int size() {
        return chapters.size();
    }

    /**
     * Gets ObservableList's iterator.
     * @return the list's iterator.
     */
    @Override
    public Iterator<Chapter> iterator() {
        return chapters.iterator();
    }

    /**
     * Overrides toString method.
     * @return title.
     */
    @Override
    public String toString() {
        String content = "";
        for (Chapter chapter : chapters) {
            content += chapter + " ";
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
     * Adds a chapter to chapterList.
     * @param chapter the chapter to be added.
     */
    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
    }

    /**
     * Removes a chapter to chapterList.
     * @param chapter the chapter to be removed.
     */
    public void removeChapter(Chapter chapter) {
        if (chapters.contains(chapter)) {
            chapters.remove(chapter);
            for (EventCard eventCard : ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards()) {
                if (eventCard.getChapter() == chapter) {
                    System.out.println("set null");
                    eventCard.setChapterAndDisplay(null);
                }
            }
        } else {
            System.out.println("This event card does not exist");
        }
    }

    /**
     * Converts a chapterList into a JSONArray.
     * @return the passed chapterList, in JSONArray form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray chapterArray = new JSONArray();
        for (Chapter chapter : chapters) {
            chapterArray.add(chapter.writeJSONObjectAsComponentID());
        }
        return chapterArray;
    }

    /**
     * Loads data in the JSONArray into a chapterList.
     * @param chapterArray the JSONArray that is to be read.
     * @return a chapterList with data loaded from the chapterArray parameter.
     */
    @Override
    public ChapterList readJSONArray(JSONArray chapterArray) {
        for (Object chapterObject : chapterArray) {
            this.addChapter(Chapter.readJSONObjectAsComponentID((JSONObject) chapterObject));
        }
        return this;
    }

}
