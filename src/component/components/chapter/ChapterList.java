package component.components.chapter;

import ablity.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class ChapterList implements Iterable<Chapter>, SavableAsJSONArray {
    private final ObservableList<Chapter> chapters;

    public ChapterList() {
        chapters = FXCollections.observableArrayList();
        chapters.addListener((ListChangeListener.Change<? extends Chapter> change) -> {
            System.out.println("Change on chapterList");
            while (change.next()) {
                System.out.println("Chapter -> " + change);
                if (change.wasUpdated()) {
                    System.out.println("Update detected");
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static ChapterList parseJSONArray(JSONArray chapterArray) {
        ChapterList chapters = new ChapterList();
        for (Object chapterObject : chapterArray) {
            Chapter chapter = Chapter.parseJSONObject((JSONObject) chapterObject);
            chapters.addChapter(chapter);
        }
        return chapters;
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
        return this.getJSONArray().toJSONString();
    }

    public void removeChapter(Chapter chapter) {
        if (chapters.contains(chapter)) {
            chapters.remove(chapter);
        } else {
            System.out.println("This event card does not exist");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray chapterArray = new JSONArray();
        for (Chapter chapter : chapters) {
            chapterArray.add(chapter.getJSONObject());
        }
        return chapterArray;
    }

}
