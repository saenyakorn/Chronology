package component.components.chapter;

import colors.RandomColor;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

/**
 * An instance of BasicStoryComponent. A chapter is similar to a marker that can be set to any event card, one chapter per event card.
 */
public class Chapter extends BasicStoryComponent {

    /**
     * No-arg constructor of Chapter.
     */
    public Chapter() {
        setColorAndDisplay(RandomColor.getColor());
    }

    /**
     * Constructor for Chapter that requires componentID. All parameters are set to default values. Used to populate HashMap during file opening process.
     * @param componentID this chapter's unique ID.
     */
    public Chapter(String componentID) {
        super(componentID);
    }

    /**
     * Constructor for Chapter that requires title and description. Remaining parameters are set to default values.
     * @param title this chapter's title.
     * @param description this chapter's description.
     */
    public Chapter(String title, String description) {
        super(title, description);
        setColorAndDisplay(RandomColor.getColor());
    }

    /**
     * Constructor for BasicStoryComponent that requires all parameters.
     * @param title this chapter's title.
     * @param description this chapter's description.
     * @param color this chapter's Color.
     * @param timePeriod this chapter's TimePeriod.
     */
    public Chapter(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        setColorAndDisplay(RandomColor.getColor());
    }

    /**
     * Overrides toString method.
     * @return title.
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * Sets color value and everything related to display of chapter's color. This consists of:
     * <ul>
     *     <li>The color of the chapterTitleContainer shown on every event card this chapter contains.</li>
     * </ul>
     * @param color the color to be set.
     */
    @Override
    public void setColorAndDisplay(Color color) {
        setColor(color);
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        for (EventCard eventCard : eventCards) {
            if (eventCard.getChapter() == this) {
                eventCard.setChapterColor(color);
            }
        }
    }

    /**
     * Counts number of event cards in the component.
     * @return number of event cards in component
     */
    @Override
    public int eventCardsInComponent() {
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        int count = 0;
        for (EventCard eventCard : eventCards) {
            if (eventCard.getChapter() == this) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Converts a chapter into a JSONObject.
     * @return the passed chapter, in JSONObject form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject chapterObject = super.writeJSONObject();
        chapterObject.put("type", "Chapter");
        return chapterObject;
    }

    /**
     * Accepts a JSONObject with a componentID field, and gets a chapter from that componentID.
     * @param componentObject the JSONObject that is to be read.
     * @return a chapter with data loaded from the chapter referenced by the JSONObject's componentID.
     */
    public static Chapter readJSONObjectAsComponentID(JSONObject componentObject) {
        Chapter readChapter = (Chapter) ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
        readChapter.initializeDisplayAfterRead();
        return readChapter;
    }

    /**
     * Initializes everything related to this chapter's display, after chapter data is completely read from opened file.
     */
    private void initializeDisplayAfterRead() {
        setColorAndDisplay(getColor());
    }
}