package component.base;

import colors.GlobalColor;
import colors.RandomColor;
import component.ability.SavableAsJSONObject;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;
import utils.SystemUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * Base class of story components.
 *
 * @see EventCard
 * @see Storyline
 * @see Chapter
 */
public abstract class BasicStoryComponent implements SavableAsJSONObject<BasicStoryComponent> {
    /**
     * A unique ID used to store component in HashMap.
     */
    protected final String componentId;
    /**
     * Title of component. Wrapped with SimpleStringProperty.
     */
    protected SimpleStringProperty title = new SimpleStringProperty();
    /**
     * Description of component. Wrapped with SimpleStringProperty.
     */
    protected SimpleStringProperty description = new SimpleStringProperty();
    /**
     * Color of component. Wrapped with SimpleObjectProperty.
     */
    protected Property<Color> color = new SimpleObjectProperty<>();
    /**
     * TimePeriod of component. Wrapped with SimpleObjectProperty.
     */
    protected Property<TimePeriod> timePeriod = new SimpleObjectProperty<>();

    /**
     * No-arg constructor for BasicStoryComponent. All fields are set to default values.
     */
    @SuppressWarnings("unchecked")
    public BasicStoryComponent() {
        componentId = UUID.randomUUID().toString();
        ApplicationUtils.putItemToCurrentHashMap(componentId, this);
        setTitle(SystemUtils.DEFAULT_TITLE);
        setDescription(SystemUtils.DEFAULT_DESCRIPTION);
        setColor(GlobalColor.DEFAULT_COLOR);
        setTimePeriod(SystemUtils.DEFAULT_TIME_PERIOD);
    }

    /**
     * Constructor for BasicStoryComponent that requires componentID. All fields are set to default values. Used to populate HashMap during file opening process.
     *
     * @param componentID this component's unique ID.
     */
    public BasicStoryComponent(String componentID) {
        this.componentId = componentID;
        setTitle(SystemUtils.DEFAULT_TITLE);
        setDescription(SystemUtils.DEFAULT_DESCRIPTION);
        setColor(GlobalColor.DEFAULT_COLOR);
        setTimePeriod(SystemUtils.DEFAULT_TIME_PERIOD);
    }

    /**
     * Constructor for BasicStoryComponent that requires title and description. Remaining fields are set to default values.
     *
     * @param title       this component's title.
     * @param description this component's description.
     */
    @SuppressWarnings("unchecked")
    public BasicStoryComponent(String title, String description) {
        componentId = UUID.randomUUID().toString();
        ApplicationUtils.putItemToCurrentHashMap(componentId, this);
        setTitle(title);
        setDescription(description);
        setColor(GlobalColor.DEFAULT_COLOR);
        setTimePeriod(SystemUtils.DEFAULT_TIME_PERIOD);
    }

    /**
     * Constructor for BasicStoryComponent that requires all fields.
     *
     * @param title       this component's title.
     * @param description this component's description.
     * @param color       this component's Color.
     * @param timePeriod  this component's TimePeriod.
     */
    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        componentId = UUID.randomUUID().toString();
        ApplicationUtils.putItemToCurrentHashMap(componentId, this);
        setTitle(title);
        setDescription(description);
        setColor(color);
        setTimePeriod(timePeriod);
    }

    /**
     * Converts JSONObject to a BasicStoryComponent of the correct type, with a componentID specified by componentID parameter. Used to populate HashMap during file open.
     *
     * @param componentID     ID of th component to be created.
     * @param componentObject JSONObject to be converted into component.
     * @return A BasicStoryComponent of the correct type.
     */
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

    /**
     * Getter for componentID.
     *
     * @return this component's unique ID.
     */
    public String getComponentId() {
        return componentId;
    }

    /**
     * Getter for titleProperty.
     *
     * @return this component's titleProperty.
     */
    public SimpleStringProperty titleProperty() {
        return title;
    }

    /**
     * Getter for title.
     *
     * @return this component's title.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Setter for title.
     *
     * @param title the title to be set.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Sets title value and everything related to display of component's title.
     *
     * @param title the title to be set.
     */
    public void setTitleAndDisplay(String title) {
        setTitle(title);
    }

    /**
     * Getter for descriptionProperty.
     *
     * @return this component's descriptionProperty.
     */
    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    /**
     * Getter for description.
     *
     * @return this component's description.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Setter for description.
     *
     * @param description the description to be set.
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Sets description value and everything related to display of component's description.
     *
     * @param description the description to be set.
     */
    public void setDescriptionAndDisplay(String description) {
        setDescription(description);
    }

    /**
     * Getter for colorProperty.
     *
     * @return this component's colorProperty.
     */
    public Property<Color> colorProperty() {
        return color;
    }

    /**
     * Getter for color.
     *
     * @return this component's Color.
     */
    public Color getColor() {
        return color.getValue();
    }

    /**
     * Setter for color. The list of used colors will be updated accordingly.
     *
     * @param color the color to be set.
     */
    public void setColor(Color color) {
        RandomColor.removeUsedColor(getColor());
        this.color.setValue(color);
        RandomColor.addUsedColor(color);
    }

    /**
     * Sets color value and everything related to display of component's color.
     *
     * @param color the color to be set.
     */
    public void setColorAndDisplay(Color color) {
        setColor(color);
    }

    /**
     * Getter for timePeriodProperty.
     *
     * @return this component's timePeriodProperty.
     */
    public Property<TimePeriod> timePeriodProperty() {
        return timePeriod;
    }

    /**
     * Getter for timePeriod.
     *
     * @return this component's TimePeriod.
     */
    public TimePeriod getTimePeriod() {
        return timePeriod.getValue();
    }

    /**
     * Setter for timePeriod.
     *
     * @param timePeriod the timePeriod to be set.
     */
    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod.setValue(timePeriod);
    }

    /**
     * Sets timePeriod value and everything related to display of component's color.
     *
     * @param timePeriod the timePeriod to be set.
     */
    public void setTimePeriodAndDisplay(TimePeriod timePeriod) {
        setTimePeriod(timePeriod);
    }

    /**
     * Overrides toString method.
     *
     * @return title.
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * Gets the JSON object in string format.
     *
     * @return the JSON object converted to a string.
     */
    @Override
    public String getJSONString() {
        return this.writeJSONObject().toJSONString();
    }

    /**
     * Converts a BasicStoryComponent into a JSONObject containing all.
     *
     * @return the passed BasicStoryComponent, in JSONObject form.
     */
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

    /**
     * Converts a BasicStoryComponent into a JSONObject.
     *
     * @return the passed BasicStoryComponent, in JSONObject form.
     */
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObjectAsComponentID() {
        JSONObject componentObject = new JSONObject();
        componentObject.put("componentID", getComponentId());
        return componentObject;
    }

    /**
     * Loads data in the JSONObject into a BasicStoryComponent.
     *
     * @param componentObject the JSONObject that is to be read.
     * @return a BasicStoryComponent with data loaded from the componentObject parameter.
     */
    @Override
    public BasicStoryComponent readJSONObject(JSONObject componentObject) {
        setTitle((String) componentObject.get("title"));
        setDescription((String) componentObject.get("description"));
        setColor(Color.web((String) componentObject.get("Color")));
        setTimePeriod(TimePeriod.stringToTimePeriod((String) componentObject.get("TimePeriod")));
        return this;
    }

    /**
     * Loads the FXML of the component from the link specified.
     *
     * @param link link to FXML file.
     */
    protected void loadFXML(String link) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(link));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Defines what happens when component is removed. An alert prompt user to confirm permanent remove.
     */
    public void onRemoveItem() {
        Alert confirm = SystemUtils.getCustomConfirmationAlert(SystemUtils.CONFIRM_REMOVE_TITLE, SystemUtils.CONFIRM_REMOVE_HEADER, SystemUtils.CONFIRM_REMOVE_CONTENT);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            removeItem();
        } else {
            confirm.close();
        }
    }

    /**
     * Removes a component. Implementation is done be subclasses.
     */
    public void removeItem() {
    }

    /**
     * Counts number of event cards in the component.
     *
     * @return number of event cards in component
     */
    public int eventCardsInComponent() {
        return 1;
    }

}
