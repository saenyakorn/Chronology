package component.components.document;

import component.ability.SavableAsJSONObject;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Document extends VBox implements SavableAsJSONObject<Document> {
    private final SimpleStringProperty name = new SimpleStringProperty("New Document");
    private EventCardList eventCards = new EventCardList();
    private ChapterList chapters = new ChapterList();
    private StorylineList storylines = new StorylineList();

    public Document() {
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        getStyleClass().add("document");
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    public Document(String name) {
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        getStyleClass().add("document");
        setName(name);
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    public Document(String name, EventCardList eventCards, ChapterList chapters, StorylineList storylines) {
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        getStyleClass().add("document");
        setName(name);
        this.eventCards = eventCards;
        this.chapters = chapters;
        this.storylines = storylines;
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
    }

    public void addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
    }

    public void addChapter(Chapter chapter) {
        chapters.addChapter(chapter);
    }

    public void removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
    }

    public void removeStoryline(Storyline storyline) {
        storylines.removeStoryline(storyline);
    }

    public void removeChapter(Chapter chapter) {
        chapters.removeChapter(chapter);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public ChapterList getChapters() {
        return chapters;
    }

    public StorylineList getStorylines() {
        return storylines;
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public String getJSONString() {
        return writeJSONObject().toJSONString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject documentObject = new JSONObject();
        documentObject.put("name", getName());
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

        storylines.readJSONArray(storylineArray);
        eventCards.readJSONArray(eventCardArray);
        chapters.readJSONArray(chapterArray);

        return this;
    }
}
