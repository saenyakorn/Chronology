package component.base;

import component.components.timeModifier.TimePeriod;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class BasicStoryComponent extends Pane {
    protected String title;
    protected String description;
    protected Color color;
    protected TimePeriod timePeriod;

    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.timePeriod = timePeriod;
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
}
