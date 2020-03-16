package component.components.storyLine;

import component.base.BasicStoryComponent;
import component.components.timeModifier.TimePeriod;

import java.awt.*;

public class StoryLine extends BasicStoryComponent {

    public StoryLine(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
    }

    @Override
    public String toString() {
        return null;
    }
}
