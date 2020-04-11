package application.layout;

import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class componentTestController {
    @FXML private Button addEventCardButton;
    @FXML private Storyline storyline;

    @FXML
    public void handleAddEventCard() {
        storyline.addEventCard(new EventCard());
    }
}
