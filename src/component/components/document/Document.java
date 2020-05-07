package component.components.document;

import ablity.SavableAsJSONObject;
import application.ApplicationResource;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.Tab;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Document extends Tab implements SavableAsJSONObject {
    private String name;
    private final EventCardList eventCards;
    private final ChapterList chapters;
    private final StorylineList storylines;

    public Document(String name) {
        this.name = name;
        setText(name);
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();

        setOnCloseRequest(event -> ApplicationResource.getCurrentWorkspace().removeDocument(this));
    }

    public Document(String name, EventCardList eventCards, ChapterList chapters, StorylineList storylines) {
        this.name = name;
        setText(name);
        this.eventCards = eventCards;
        this.chapters = chapters;
        this.storylines = storylines;

        setOnCloseRequest(event -> ApplicationResource.getCurrentWorkspace().removeDocument(this));
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
        setText(name);
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
    }

    public void addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
        ApplicationResource.getCurrentWorkspace().getViewer().addStorylineToCanvas(storyline);
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

    public void removeStoryline(Storyline storyline) {
        storylines.removeStoryline(storyline);
    }

    public void removeChapter(Chapter chapter) {
        chapters.removeChapter(chapter);
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public String getJSONString() {
        return getJSONObject().toJSONString();
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject document = new JSONObject();
        document.put("name", name);
        document.put("eventCardList", eventCards.getJSONArray());
        document.put("chapterList", chapters.getJSONArray());
        document.put("storylineList", storylines.getJSONArray());
        return document;
    }

    @SuppressWarnings("unchecked")
    public static Document parseJSONObject(JSONObject documentObject) {
        String name = (String) documentObject.get("name");
        JSONArray eventCardArray = (JSONArray) documentObject.get("eventCardList");
        JSONArray chapterArray = (JSONArray) documentObject.get("chapterList");
        JSONArray storylineArray = (JSONArray) documentObject.get("storylineList");

        EventCardList eventCards = EventCardList.parseJSONArray(eventCardArray);
        ChapterList chapters = ChapterList.parseJSONArray(chapterArray);
        StorylineList storylines = StorylineList.parseJSONArray(storylineArray);

        return new Document(name, eventCards, chapters, storylines);
    }
}
