package component.components.eventCard;

import component.base.BasicStoryComponent;
import component.components.timeModifier.TimePeriod;

import java.awt.*;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private String character;
    private String place;

    public EventCard(String title, String description, Color color, TimePeriod timePeriod, String character, String place) {
        super(title, description, color, timePeriod);
        this.character = character;
        this.place = place;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int compareTo(EventCard o) throws IllegalArgumentException {
        return timePeriod.compareTo(o.timePeriod);
    }
}
