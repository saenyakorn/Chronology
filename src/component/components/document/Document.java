package component.components.document;

import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.scene.control.Tab;

import java.util.ArrayList;

public class Document extends Tab {
    private String name;
    private ArrayList<EventCard> eventCards;
    private ArrayList<Chapter> chapters;
    private ArrayList<Storyline> storylines;

    public Document(String name) {
        this.setName(name);
        this.name = name;
        eventCards = new ArrayList<>();
        chapters = new ArrayList<>();
        storylines = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EventCard> getEventCards() {
        return eventCards;
    }

    public void setEventCards(ArrayList<EventCard> eventCards) {
        this.eventCards = eventCards;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<Storyline> getStorylines() {
        return storylines;
    }

    public void setStorylines(ArrayList<Storyline> storylines) {
        this.storylines = storylines;
    }
}
