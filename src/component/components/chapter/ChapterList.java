package component.components.chapter;

import component.SavableAsJSONArray;
import org.json.simple.JSONArray;

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

    @Override
    public JSONArray getJSONArray() {
        return null;
    }
}
