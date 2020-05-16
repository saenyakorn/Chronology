package component.components.chapter;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

public class Chapter extends BasicStoryComponent {

    public Chapter() {
        super();
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

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public void setColorAndDisplay(Color color) {
        setColor(color);
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        for (EventCard eventCard : eventCards) {
            if(eventCard.getChapter().equals(this)){
                eventCard.setChapterColor(color);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject chapterObject = super.writeJSONObject();
        chapterObject.put("type", "Chapter");
        return chapterObject;
    }

    public static Chapter readJSONObjectAsComponentID(JSONObject componentObject) {
        Chapter readChapter = (Chapter) ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
        readChapter.initializeDisplayAfterRead();
        return readChapter;
    }

    private void initializeDisplayAfterRead() {
        setColorAndDisplay(getColor());
    }
}