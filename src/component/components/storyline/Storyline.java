package component.components.storyline;

import component.base.BasicStoryComponent;
import component.components.timeModifier.TimePeriod;

import java.awt.*;

public class Storyline extends BasicStoryComponent {

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
    }

    @Override
    public String toString() {
        return null;
    }
}
