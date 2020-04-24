package component.components.document;

import application.ApplicationResource;
import component.Savable;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.Tab;

public class Document extends Tab implements Savable {
    private String name;
    private final EventCardList eventCards;
    private final ChapterList chapters;
    private final StorylineList storylines;

    public Document(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setText(name);
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
    }

    public void addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
        ApplicationResource.getCurrentWorkspace().getViewer().addStoryline(storyline);
    }

    public void addChapter(Chapter chapter) {
        chapters.addChapter(chapter);
    }

    public void removeEventCard(EventCard eventCard) {
        if (eventCard.getChapter() == null && eventCard.getStoryline() == null) {
            eventCards.removeEventCard(eventCard);
        } else {
            if (eventCard.getChapter() != null) {
                eventCard.getChapter().removeEventCard(eventCard);
            }
            if (eventCard.getStoryline() != null) {
                eventCard.getStoryline().removeEventCard(eventCard);
            } else {
                System.out.println("E-Chapter: " + eventCard.getChapter());
                System.out.println("E-Storyline: " + eventCard.getStoryline());
                System.out.println(eventCard + " -> This event card is not exist");
            }
        }
    }

    public Storyline removeStoryline(Storyline storyline) {
        return storylines.removeStoryline(storyline);
    }

    public Chapter removeChapter(Chapter chapter) {
        return chapters.removeChapter(chapter);
    }

    @Override
    public String toString() {
        return this.getText();
    }

    @Override
    public void getJSON() {

    }
}
