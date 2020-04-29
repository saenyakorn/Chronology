package component.components.chapter;

import component.SavableAsJSONArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChapterList implements Iterable<Chapter>, SavableAsJSONArray {
    private final ArrayList<Chapter> chapters;

    public ChapterList() {
        this.chapters = new ArrayList<>();
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
    }

    public Chapter removeChapter(Chapter chapter) {
        if (chapters.contains(chapter)) {
            chapters.remove(chapter);
            return chapter;
        } else {
            System.out.println("This event card does not exist");
            return null;
        }
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

    @Override @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray chapterArray = new JSONArray();
        for(Chapter chapter : chapters) {
            chapterArray.add(chapter.getJSONObject());
        }
        return chapterArray;
    }

    @SuppressWarnings("unchecked")
    public static ChapterList parseJSONArray(JSONArray chapterArray) {
        ChapterList chapters = new ChapterList();
        for(Object chapterObject : chapterArray) {
            Chapter chapter = Chapter.parseJSONObject((JSONObject) chapterObject);
            chapters.addChapter(chapter);
        }
        return chapters;
    }

}
