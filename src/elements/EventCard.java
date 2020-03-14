package elements;

import java.awt.*;
import java.time.LocalDateTime;

public class EventCard extends BasicStoryElement implements Comparable{
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
        return null;
    }

    @Override
    public int compareTo(Object o) throws IllegalArgumentException {
        return timePeriod.compareTo(o);
    }
}
