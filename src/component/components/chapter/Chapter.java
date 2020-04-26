package component.components.chapter;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;

public class Chapter extends BasicStoryComponent {
    private final EventCardList eventCards;

    public Chapter() {
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
    public JSONObject getJSONObject() {
        JSONObject chapter = super.getJSONObject();
        chapter.put("eventCardList", eventCards.getJSONArray());
        return chapter;
    }

    public static Chapter parseJSONObject(JSONObject chapterObject) {
        String name = (String) chapterObject.get("name");
        String description = (String) chapterObject.get("description");
        Color color = Color.web((String) chapterObject.get("Color"));
        TimePeriod timePeriod = TimePeriod.stringToTimePeriod((String) chapterObject.get("TimePeriod"));

        Chapter chapter = new Chapter(name, description, color, timePeriod);
        //TODO : add EventCardList - new constructor? like Document.java

        return chapter;
    }
}
