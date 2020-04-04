package component.components.eventCard;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.time.LocalDate;

public class EventCard extends BasicStoryComponent implements Comparable<EventCard> {
    private String characters;
    private String place;

    public EventCard() {
        super("Title", "description", Color.web(SystemConstants.RED), TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY));
        this.characters = "";
        this.place = "";
        this.loadFXML();
    }

    public EventCard(String title, String description, Color color, TimePeriod timePeriod, String characters, String place) {
        super(title, description, color, timePeriod);
        this.characters = characters;
        this.place = place;
        this.loadFXML();
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

    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EventCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
