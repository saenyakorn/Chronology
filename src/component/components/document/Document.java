package component.components.document;

import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.Tab;

public class Document extends Tab {
    private EventCardList eventCards;
    private ChapterList chapters;
    private StorylineList storylines;

    public Document(String name) {
        this.setText(name);
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();
    }

    public EventCardList getEventCardList() {
        return eventCards;
    }

    public ChapterList getChapterList() {
        return chapters;
    }

    public StorylineList getStorylineList() {
        return storylines;
    }

    public EventCardList addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        return eventCards;
    }

    public EventCardList addAllEventCard(EventCard... args) {
        for (EventCard eventCard : args) {
            eventCards.addEventCard(eventCard);
        }
        return eventCards;
    }

    public StorylineList addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
        return storylines;
    }

    public StorylineList addAllStoryLine(Storyline... args) {
        for (Storyline storyline : args) {
            storylines.addStoryline(storyline);
        }
        return storylines;
    }

    public ChapterList addChapter(Chapter chapter) {
        chapters.addChapter(chapter);
        return chapters;
    }

    public ChapterList addAllChapter(Chapter... args) {
        for (Chapter chapter : args) {
            chapters.addChapter(chapter);
        }
        return chapters;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
