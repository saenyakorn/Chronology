package component.components.document;

import component.components.chapter.ChapterList;
import component.components.eventCard.EventCardList;
import component.components.storyline.StorylineList;
import javafx.scene.control.Tab;

public class Document extends Tab {
    private String name;
    private EventCardList eventCards;
    private ChapterList chapters;
    private StorylineList storylines;

    public Document(String name) {
        this.setText(name);
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public void setEventCards(EventCardList eventCards) {
        this.eventCards = eventCards;
    }

    public ChapterList getChapters() {
        return chapters;
    }

    public void setChapters(ChapterList chapters) {
        this.chapters = chapters;
    }

    public StorylineList getStorylines() {
        return storylines;
    }

    public void setStorylines(StorylineList storylines) {
        this.storylines = storylines;
    }

    @Override
    public String toString() {
        return name;
    }
}
