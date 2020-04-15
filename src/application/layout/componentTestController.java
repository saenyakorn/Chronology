package application.layout;

import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.components.timeModifier.TimePeriod;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class componentTestController {
    @FXML private Button addEventCardButton;
    @FXML private Button addDefaultEventCardButton;
    @FXML private Storyline storyline;

    @FXML
    public void handleAddDefaultEventCard() {
        storyline.addEventCard(new EventCard());
    }

    @FXML
    public void handleAddEventCard() {
        EventCard e = new EventCard("Name","Description desu");
        e.setTimePeriod(new TimePeriod(LocalDateTime.of(2020,2,1,13,2),LocalDateTime.of(2020,2,1,14,0)));
        storyline.addEventCard(e);
    }
}
