package component.components.chapter;

import ability.Savable;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Chapter extends BasicStoryComponent {
    private final EventCardList eventCards;

    public Chapter() {
        eventCards = new EventCardList();
    }

    public Chapter(String componentID) {
        super(componentID);
        eventCards = new EventCardList();
    }

    public Chapter(String title, String description) {
        super(title, description);
        eventCards = new EventCardList();
    }

    public Chapter(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        this.eventCards = new EventCardList();
    }

    public Chapter(String title, String description, Color color, TimePeriod timePeriod, EventCardList eventCards) {
        super(title, description, color, timePeriod);
        this.eventCards = eventCards;
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        eventCard.setChapter(this);
    }

    public void removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject chapterObject = super.writeJSONObject();
        chapterObject.put("eventCardList", eventCards.writeJSONArray());
        chapterObject.put("type","Chapter");
        return chapterObject;
    }

    @Override
    public Chapter readJSONObject(JSONObject chapterObject) {
        Savable.printReadingMessage("chapter " + title);
        super.readJSONObject(chapterObject);
        JSONArray eventCardArray = (JSONArray) chapterObject.get("eventCardList");
        eventCards.readJSONArray(eventCardArray);
        Savable.printReadingFinishedMessage("chapter " + title);
        return this;
    }
}
