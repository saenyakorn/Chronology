package component.components.chapter;

import java.util.ArrayList;

public class ChapterList {
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

    public void addAllChapter(ArrayList<Chapter> chapters) {
        this.chapters.addAll(chapters);
    }

    public int removeChapter(Chapter chapter) {
        if (chapters.contains(chapter)) {
            chapters.remove(chapter);
            return 1;
        } else {
            System.out.println("This event card does not exist");
            return 0;
        }
    }
}
