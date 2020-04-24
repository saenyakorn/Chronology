package component.base;

import application.ApplicationResource;
import application.SystemConstants;
import colors.RandomColor;
import component.Savable;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public abstract class BasicStoryComponent extends Pane implements Savable {
    protected final String componentId;
    protected String title;
    protected String description;
    protected Color color;
    protected TimePeriod timePeriod;

    public BasicStoryComponent() {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.putItemToCurrentWorkspaceHashMap(componentId, this);
        this.title = "Title";
        this.description = "Lorem ipsum dolor set amet, ego bir setaso de.";
        this.color = SystemConstants.DEFAULT_COLOR;
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String title, String description) {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.putItemToCurrentWorkspaceHashMap(componentId, this);
        this.title = title;
        this.description = description;
        this.color = SystemConstants.DEFAULT_COLOR;
        this.timePeriod = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    }

    public BasicStoryComponent(String title, String description, Color color, TimePeriod timePeriod) {
        this.componentId = Integer.toString(Objects.hashCode(this));
        ApplicationResource.putItemToCurrentWorkspaceHashMap(componentId, this);
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
    abstract public String toString();

    protected void loadFXML(String link) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(link));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()));
    }
}
