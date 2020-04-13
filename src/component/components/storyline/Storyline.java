package component.components.storyline;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import component.dialog.SetColorDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;

public class Storyline extends BasicStoryComponent {
    private EventCardList eventCards;

    @FXML
    private Pane root;
    @FXML
    private Line line;
    @FXML
    private TextField storylineTitle;
    @FXML
    private HBox storylineTitleContainer;
    @FXML
    private EventCardList eventCardList;

    public Storyline() {
        eventCards = new EventCardList();
        this.loadFXML();
    }

    public Storyline(String title, String description) {
        super(title, description);
        eventCards = new EventCardList();
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        eventCards = new EventCardList();
        this.loadFXML();
    }

    @FXML
    public void initialize() {
        this.setTitle(this.getTitle());
        storylineTitle.setDisable(true);
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if(!title.equals(storylineTitle.getText())){
                this.setTitle(storylineTitle.getText());
                System.out.println("Storyline title set to " + title);
            }
            storylineTitle.setDisable(true);
        });

        this.setColor(this.getColor());
        initializeContextMenu();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        storylineTitle.setText(title);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        line.setStroke(color);
        storylineTitle.setStyle("-fx-text-fill: " + colorToHex(color) + ";");
        for(EventCard eventCard : eventCards){
            eventCard.setColor(color);
        }
    }

    public EventCardList getEventCards() {
        return eventCards;
    }

    public void addEventCard(EventCard eventCard) {
        eventCards.addEventCard(eventCard);
        eventCard.setStoryline(this);
        eventCardList.getChildren().add(eventCard);
        line.setEndX(line.getEndX() + (SystemConstants.EVENTCARD_PREF_WIDTH + 30));
    }

    public void addAllEventCards(EventCard... args) {
        for (EventCard eventCard : args) {
            eventCards.addEventCard(eventCard);
            eventCard.setStoryline(this);
            eventCardList.getChildren().add(eventCard);
        }
        int increasedLineLength = args.length * (SystemConstants.EVENTCARD_PREF_WIDTH + 30);
        line.setEndX(line.getEndX() + increasedLineLength);
    }

    public void removeEventCard(EventCard eventCard) {
        eventCards.removeEventCard(eventCard);
        eventCardList.getChildren().remove(eventCard);
        line.setEndX(line.getEndX() - (SystemConstants.EVENTCARD_PREF_WIDTH + 30));
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    protected void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Storyline.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem setColorMenu = new MenuItem("Set storyline color");
        contextMenu.getItems().add(setColorMenu);
        setColorMenu.setOnAction((ActionEvent event) ->{
            SetColorDialog dialog = new SetColorDialog(this);
            dialog.show();
        });

        root.setOnMousePressed((MouseEvent event) -> {
            if (event.isSecondaryButtonDown() && !clickInEventCard(event)) {
                contextMenu.show(root, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private boolean clickInEventCard(MouseEvent event) {
        if(this.eventCardList.contains(eventCardList.screenToLocal(event.getScreenX(), event.getScreenY()))) {
            for(EventCard eventCard : eventCards) {
                if(eventCard.clickInEventCard(event))
                    return true;
            }
            return false;
        }
        return false;
    }
}
