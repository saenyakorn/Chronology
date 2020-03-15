package components.storyLine;

import elements.BasicStoryElement;
import elements.basic.TimePeriod;

import java.awt.*;

public class StoryLine extends BasicStoryElement {

    public StoryLine(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
    }

    @Override
    public String toString() {
        return null;
    }
}
