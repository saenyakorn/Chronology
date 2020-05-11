package component.base;

import ability.SavableAsJSONObject;
import application.ApplicationResource;
import colors.GlobalColor;
import colors.RandomColor;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public abstract class BasicStoryComponent implements SavableAsJSONObject<BasicStoryComponent> {
    protected final String componentId;
    protected String title;
    protected String description;
    protected Color color;
    protected TimePeriod timePeriod;

    public BasicStoryComponent() {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.putItemToCurrentHashMap(componentId, this);
        this.title = "Title";
        this.description = "Lorem ipsum dolor set amet, ego bir setaso de.";
        this.color = GlobalColor.DEFAULT_COLOR;
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String componentId) {
        this.componentId = componentId;
        //no put item because used to load a hashMap from file
        this.title = "Title";
        this.description = "Lorem ipsum dolor set amet, ego bir setaso de.";
        this.color = GlobalColor.DEFAULT_COLOR;
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String title, String description) {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.putItemToCurrentHashMap(componentId, this);
        this.title = title;
        this.description = description;
        this.color = GlobalColor.DEFAULT_COLOR;
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.putItemToCurrentHashMap(componentId, this);
        this.title = title;
        this.description = description;
        this.color = color;
        this.timePeriod = timePeriod;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        RandomColor.removeUsedColor(this.color);
        this.color = color;
        RandomColor.addUsedColor(color);
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    @Override
    public String toString() {
        return title;
    }

    public static BasicStoryComponent JSONObjectToBasicStoryComponent(String componentID, JSONObject componentObject) {
        String type = (String) componentObject.get("type");
        switch (type) {
            case "EventCard":
                return new EventCard(componentID);
            case "Storyline":
                return new Storyline(componentID);
            case "Chapter":
                return new Chapter(componentID);
            default:
                return null; //could throw an error
        }
    }

    @Override
    public String getJSONString() {
        return this.writeJSONObject().toJSONString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject componentObject = new JSONObject();
        componentObject.put("title", title);
        componentObject.put("description", description);
        componentObject.put("Color", colorToHex(color));
        componentObject.put("TimePeriod", timePeriod.toString());
        return componentObject;
    }

    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObjectAsComponentID() {
        JSONObject componentObject = new JSONObject();
        componentObject.put("componentID", componentId);
        return componentObject;
    }

    @Override
    public BasicStoryComponent readJSONObject(JSONObject componentObject) {
        this.setTitle((String) componentObject.get("title"));
        this.setDescription((String) componentObject.get("description"));
        //System.out.println((String) componentObject.get("title") + (String) componentObject.get("Color"));
        this.setColor(Color.web((String) componentObject.get("Color")));
        this.setTimePeriod(TimePeriod.stringToTimePeriod((String) componentObject.get("TimePeriod")));
        return this;
    }

    protected void loadFXML(String link) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(link));
        // fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()));
    }
}
