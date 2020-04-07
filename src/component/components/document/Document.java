package component.components.document;

import component.components.chapter.ChapterList;
import component.components.eventCard.EventCardList;
import component.components.storyline.StorylineList;

public class Document {
    private String name;
    private EventCardList eventCards;
    private ChapterList chapters;
    private StorylineList storylines;

    public Document(String name) {
        this.name = name;
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();
    }
}
