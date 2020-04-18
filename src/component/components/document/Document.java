package component.components.document;

import application.ApplicationResource;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.Tab;

public class Document extends Tab {
    private final EventCardList eventCards;
    private final ChapterList chapters;
    private final StorylineList storylines;


    public Document(String name) {
        this.setText(name);
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();

        this.setOnCloseRequest(event -> ApplicationResource.getCurrentWorkspace().removeDocument(this));
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

    public StorylineList addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
        return storylines;
    }

    public ChapterList addChapter(Chapter chapter) {
        chapters.addChapter(chapter);
        return chapters;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
