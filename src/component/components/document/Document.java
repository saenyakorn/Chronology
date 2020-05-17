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
import utils.SystemUtils;

/**
 * A document within a project, displayed as tabs in the document tab bar.
 * @see DocumentList
 */
public class Document extends VBox implements SavableAsJSONObject<Document> {
    /**
     * Name of document. Wrapped with SimpleStringProperty.
     */
    private final SimpleStringProperty name = new SimpleStringProperty(SystemUtils.DEFAULT_DOCUMENT_TITLE);
    /**
     * List of all event cards in this document.
     */
    private EventCardList eventCards = new EventCardList();
    /**
     * List of all chapters in this document.
     */
    private ChapterList chapters = new ChapterList();
    /**
     * List of all storylines in this document.
     */
    private StorylineList storylines = new StorylineList();

    /**
     * No-arg constructor for Document. Binds the storylineList to this documentVBox.
     */
    public Document() {
        getStylesheets().add(ClassLoader.getSystemResource("component/components/document/Document.css").toExternalForm());
        getStyleClass().add("document");
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    /**
     * Constructor for Document that requires name. Binds the storylineList to this documentVBox.
     * @param name this document's name.
     */
    public Document(String name) {
        getStylesheets().add(ClassLoader.getSystemResource("component/components/document/Document.css").toExternalForm());
        getStyleClass().add("document");
        setName(name);
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    /**
     * Constructor for Document that requires all fields. Binds the storylineList to this documentVBox.
     * @param name this document's name.
     * @param eventCards this document's eventCardList.
     * @param chapters this document's chapterList.
     * @param storylines this document's storylineList.
     */
    public Document(String name, EventCardList eventCards, ChapterList chapters, StorylineList storylines) {
        getStylesheets().add(ClassLoader.getSystemResource("component/components/document/Document.css").toExternalForm());
        getStyleClass().add("document");
        setName(name);
        this.eventCards = eventCards;
        this.chapters = chapters;
        this.storylines = storylines;
        Bindings.bindContent(getChildren(), storylines.getStorylinePanes());
    }

    /**
     * Adds an event card to this document.
     * @param eventCard the eventCard to be added.
     */
    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
    }

    /**
     * Adds a storyline to this document.
     * @param storyline the storyline to be added.
     */
    public void addStoryLine(Storyline storyline) {
        storylines.addStoryline(storyline);
    }

    /**
     * Adds a chapter to this document.
     * @param chapter the chapter to be added.
     */
    public void addChapter(Chapter chapter) {
        chapters.addChapter(chapter);
    }

    /**
     * Removes an event card from this document.
     * @param eventCard the eventCard to be removed.
     */
    public void removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
    }

    /**
     * Removes a storyline from this document.
     * @param storyline the storyline to be removed.
     */
    public void removeStoryline(Storyline storyline) {
        storylines.removeStoryline(storyline);
    }

    /**
     * Removes a chapter from this document.
     * @param chapter the chapter to be removed.
     */
    public void removeChapter(Chapter chapter) {
        chapters.removeChapter(chapter);
    }

    /**
     * Getter for name.
     * @return this document's name.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Setter for name.
     * @param name the name to be set.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Getter for nameProperty.
     * @return this document's nameProperty.
     */
    public SimpleStringProperty nameProperty() {
        return name;
    }

    /**
     * Getter for eventCards.
     * @return this document's eventCardList.
     */
    public EventCardList getEventCards() {
        return eventCards;
    }

    /**
     * Getter for chapters.
     * @return this document's chapterList.
     */
    public ChapterList getChapters() {
        return chapters;
    }

    /**
     * Getter for storylines.
     * @return this document's storylineList.
     */
    public StorylineList getStorylines() {
        return storylines;
    }

    /**
     * Overrides toString method.
     * @return name.
     */
    @Override
    public String toString() {
        return name.get();
    }

    /**
     * Gets the JSON object in string format.
     * @return the JSON object converted to a string.
     */
    @Override
    public String getJSONString() {
        return writeJSONObject().toJSONString();
    }

    /**
     * Converts a document into a JSONObject.
     * @return the passed document, in JSONObject form.
     */
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

    /**
     * Loads data in the JSONObject into a document
     * @param documentObject the JSONObject that is to be read.
     * @return a document with data loaded from the documentObject parameter.
     */
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
