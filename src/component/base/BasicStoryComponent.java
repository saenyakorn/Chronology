package component.base;

import application.SystemConstants;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalDate;

public abstract class BasicStoryComponent extends Pane {
    protected String name;
    protected String title;
    protected String description;
    protected Color color;
    protected TimePeriod timePeriod;

    public BasicStoryComponent() {
        this.name = "Untitled";
        this.title = "Title";
        this.description = "Lorem ipsum dolor set amet, ego bir setaso de.";
        this.color = Color.web(SystemConstants.RED);
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String name) {
        this.name = name;
    }

    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.timePeriod = timePeriod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
