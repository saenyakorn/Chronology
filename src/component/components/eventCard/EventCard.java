package component.components.eventCard;

import component.base.BasicStoryComponent;
import component.components.timeModifier.TimePeriod;

import java.awt.*;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private String characters;
    private String place;

    public EventCard(String title, String description, Color color, TimePeriod timePeriod, String characters, String place) {
        super(title, description, color, timePeriod);
        this.characters = characters;
        this.place = place;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String character) {
        this.characters = character;
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
