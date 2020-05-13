package component.base;

import colors.GlobalColor;
import colors.RandomColor;
import component.ability.SavableAsJSONObject;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public abstract class BasicStoryComponent implements SavableAsJSONObject<BasicStoryComponent> {
    protected final String componentId;
    protected SimpleStringProperty title = new SimpleStringProperty();
    protected SimpleStringProperty description = new SimpleStringProperty();
    protected Property<Color> color = new SimpleObjectProperty<>();
    protected Property<TimePeriod> timePeriod = new SimpleObjectProperty<>();

    @SuppressWarnings("unchecked")
    public BasicStoryComponent() {
        componentId = UUID.randomUUID().toString();
        ApplicationUtils.putItemToCurrentHashMap(componentId, this);
        title.set("Title");
        description.set("Lorem ipsum dolor set amet, ego bir setaso de.");
        color.setValue(GlobalColor.DEFAULT_COLOR);
        timePeriod.setValue(TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY));
    }

    @SuppressWarnings("unchecked")
    public BasicStoryComponent(String componentId) {
        this.componentId = componentId;
        //no put item because used to load a hashMap from file
        title.set("Title");
        description.set("Lorem ipsum dolor set amet, ego bir setaso de.");
        color.setValue(GlobalColor.DEFAULT_COLOR);
        timePeriod.setValue(TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY));
    }

    @SuppressWarnings("unchecked")
    public BasicStoryComponent(String title, String description) {
        componentId = UUID.randomUUID().toString();
        ApplicationUtils.putItemToCurrentHashMap(componentId, this);
        this.title.set(title);
        this.description.set(description);
        color.setValue(GlobalColor.DEFAULT_COLOR);
        timePeriod.setValue(TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY));
    }

    @SuppressWarnings("unchecked")
    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        componentId = UUID.randomUUID().toString();
        ApplicationUtils.putItemToCurrentHashMap(componentId, this);
        this.title.set(title);
        this.description.set(description);
        this.color.setValue(color);
        this.timePeriod.setValue(timePeriod);
    }

    public String getComponentId() {
        return componentId;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Property<Color> colorProperty() {
        return color;
    }

    public Color getColor() {
        return color.getValue();
    }

    public void setColor(Color color) {
        RandomColor.removeUsedColor(getColor());
        this.color.setValue(color);
        RandomColor.addUsedColor(color);
    }

    public Property<TimePeriod> timePeriodProperty() {
        return timePeriod;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod.getValue();
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod.setValue(timePeriod);
    }

    @Override
    public String toString() {
        return getTitle();
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
        componentObject.put("title", getTitle());
        componentObject.put("description", getDescription());
        componentObject.put("Color", GlobalColor.colorToHex(getColor()));
        componentObject.put("TimePeriod", getTimePeriod().toString());
        return componentObject;
    }

    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObjectAsComponentID() {
        JSONObject componentObject = new JSONObject();
        componentObject.put("componentID", getComponentId());
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

    public static BasicStoryComponent readJSONObjectAsComponentID(JSONObject componentObject) {
        return ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
    }

    protected void loadFXML(String link) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(link));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
