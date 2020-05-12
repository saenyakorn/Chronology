package component.components.chapter;

import ability.Savable;
import application.ApplicationResource;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Chapter extends BasicStoryComponent {

    public Chapter() {

    }

    public Chapter(String componentID) {
        super(componentID);
    }

    public Chapter(String title, String description) {
        super(title, description);
    }

    public Chapter(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
    }

    public Chapter(String title, String description, Color color, TimePeriod timePeriod, EventCardList eventCards) {
        super(title, description, color, timePeriod);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        EventCardList eventCards = ApplicationResource.getCurrentWorkspace().getActiveDocument().getEventCards();
        for (EventCard eventCard : eventCards) {
            eventCard.setChapterColor(color);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject chapterObject = super.writeJSONObject();
//        chapterObject.put("eventCardList", eventCards.writeJSONArray());
        chapterObject.put("type", "Chapter");
        return chapterObject;
    }

    @Override
    public Chapter readJSONObject(JSONObject chapterObject) {
        Savable.printReadingMessage("chapter " + title);
        super.readJSONObject(chapterObject);
        JSONArray eventCardArray = (JSONArray) chapterObject.get("eventCardList");
//        eventCards.readJSONArray(eventCardArray);
        Savable.printReadingFinishedMessage("chapter " + title);
        return this;
    }
}
