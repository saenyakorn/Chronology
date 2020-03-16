package components.storyLine;

import elements.BasicStoryElement;
import elements.TimeOfDay;

import java.awt.*;

public class StoryLine extends BasicStoryElement {

    public StoryLine(String title, String description, Color color, TimeOfDay.TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
    }

    @Override
    public String toString() {
        return null;
    }
}
