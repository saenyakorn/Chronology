package component.base;

import application.ApplicationResource;
import application.SystemConstants;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.Objects;

public abstract class BasicStoryComponent extends Pane {
    protected final String componentId;
    protected String title;
    protected String description;
    protected Color color;
    protected TimePeriod timePeriod;

    public BasicStoryComponent() {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.getHashMapBasicStoryComponents().put(componentId, this);
        this.title = "Title";
        this.description = "Lorem ipsum dolor set amet, ego bir setaso de.";
        this.color = Color.web(SystemConstants.RED);
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String title, String description) {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.getHashMapBasicStoryComponents().put(componentId, this);
        this.title = title;
        this.description = description;
        this.color = Color.web(SystemConstants.RED);
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.getHashMapBasicStoryComponents().put(componentId, this);
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
        this.color = color;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    @Override
    abstract public String toString();

    protected abstract void loadFXML();
}
