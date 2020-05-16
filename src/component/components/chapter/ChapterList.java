package component.components.chapter;

import component.ability.SavableAsJSONArray;
import component.components.eventCard.EventCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

import java.util.Iterator;

public class ChapterList implements Iterable<Chapter>, SavableAsJSONArray<ChapterList> {

    private final ObservableList<Chapter> chapters;

    public ChapterList() {
        chapters = FXCollections.observableArrayList();
    }

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
    }

    public ObservableList<Chapter> getChapters() {
        return chapters;
    }

    public int size() {
        return chapters.size();
    }

    @Override
    public Iterator<Chapter> iterator() {
        return chapters.iterator();
    }

    @Override
    public String toString() {
        String content = "";
        for (Chapter chapter : chapters) {
            content += chapter + " ";
        }
        return content;
    }

    @Override
    public String getJSONString() {
        return this.writeJSONArray().toJSONString();
    }

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

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray chapterArray = new JSONArray();
        for (Chapter chapter : chapters) {
            chapterArray.add(chapter.writeJSONObjectAsComponentID());
        }
        return chapterArray;
    }

    @Override
    public ChapterList readJSONArray(JSONArray chapterArray) {
        for (Object chapterObject : chapterArray) {
            this.addChapter(Chapter.readJSONObjectAsComponentID((JSONObject) chapterObject));
        }
        return this;
    }

}
