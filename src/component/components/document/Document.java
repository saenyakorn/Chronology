package component.components.document;

import ability.SavableAsJSONObject;
import application.ApplicationResource;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Document extends VBox implements SavableAsJSONObject<Document> {
    private String name;
    private final EventCardList eventCards;
    private final ChapterList chapters;
    private final StorylineList storylines;

    public Document() {
        name = "";
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    public Document(String name) {
        this.name = name;
        eventCards = new EventCardList();
        chapters = new ChapterList();
        storylines = new StorylineList();
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    public Document(String name, EventCardList eventCards, ChapterList chapters, StorylineList storylines) {
        this.name = name;
        this.eventCards = eventCards;
        this.chapters = chapters;
        this.storylines = storylines;
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
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
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        ApplicationResource.getCurrentWorkspace().getSideBar().renderEventCardTreeItem(this);
    }

    public void addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
        ApplicationResource.getCurrentWorkspace().getViewer().setDocument(this);
        ApplicationResource.getCurrentWorkspace().getSideBar().renderStorylineTreeItem(this);
    }

    public void addChapter(Chapter chapter) {
        chapters.addChapter(chapter);
        ApplicationResource.getCurrentWorkspace().getSideBar().renderChapterTreeItem(this);
    }

    public void removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
        ApplicationResource.getCurrentWorkspace().getSideBar().renderEventCardTreeItem(this);
    }

    public void removeStoryline(Storyline storyline) {
        storylines.removeStoryline(storyline);
        ApplicationResource.getCurrentWorkspace().getViewer().setDocument(this);
        ApplicationResource.getCurrentWorkspace().getSideBar().renderStorylineTreeItem(this);
    }

    public void removeChapter(Chapter chapter) {
        chapters.removeChapter(chapter);
        ApplicationResource.getCurrentWorkspace().getSideBar().renderChapterTreeItem(this);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getJSONString() {
        return writeJSONObject().toJSONString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject documentObject = new JSONObject();
        documentObject.put("name", name);
        documentObject.put("eventCardList", eventCards.writeJSONArray());
        documentObject.put("chapterList", chapters.writeJSONArray());
        documentObject.put("storylineList", storylines.writeJSONArray());
        return documentObject;
    }

    public Document readJSONObject(JSONObject documentObject) {
        this.setName((String) documentObject.get("name"));
        JSONArray eventCardArray = (JSONArray) documentObject.get("eventCardList");
        JSONArray chapterArray = (JSONArray) documentObject.get("chapterList");
        JSONArray storylineArray = (JSONArray) documentObject.get("storylineList");

        eventCards.readJSONArray(eventCardArray);
        chapters.readJSONArray(chapterArray);
        storylines.readJSONArray(storylineArray);

        return this;
    }
}
