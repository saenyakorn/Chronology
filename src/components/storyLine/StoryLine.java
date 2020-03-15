package components;

import elements.BasicStoryElement;
import elements.TimePeriod;

import java.awt.*;
import java.time.LocalDateTime;

public class StoryLine extends BasicStoryElement {
    LocalDateTime period;

    public StoryLine(String title, String description, Color color, TimePeriod timePeriod, LocalDateTime period) {
        super(title, description, color, timePeriod);
        this.period = period;
    }

    @Override
    public String toString() {
        return null;
    }
}
