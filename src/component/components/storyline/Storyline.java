package component.components.storyline;

import colors.GlobalColor;
import colors.RandomColor;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import component.dialog.edit.SetColorDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class Storyline extends BasicStoryComponent {
    private int minIndex = Integer.MAX_VALUE;
    private int maxIndex = Integer.MIN_VALUE;
    private final ContextMenu contextMenu = new ContextMenu();

    @FXML
    private Pane root;
    @FXML
    private Line line;
    @FXML
    private TextField storylineTitle;
    @FXML
    private HBox storylineTitleContainer;
    @FXML
    private GridPane eventCardContainer;

    public Storyline() {
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        setColorAndDisplay(RandomColor.getColor());
    }

    public Storyline(String componentID) {
        super(componentID);
    }

    public Storyline(String title, String description) {
        super(title, description);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        setColorAndDisplay(RandomColor.getColor());
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
    }

    public Pane getDisplay() {
        return root;
    }

    public GridPane getContainer() {
        return eventCardContainer;
    }

    public int getMinIndex() {
        return minIndex == Integer.MAX_VALUE ? 0 : minIndex;
    }

    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex < this.minIndex ? minIndex : this.minIndex;
    }

    public int getMaxIndex() {
        return maxIndex == Integer.MIN_VALUE ? 0 : maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex > this.maxIndex ? maxIndex : this.maxIndex;
    }

    @Override
    public void setTitleAndDisplay(String title) {
        setTitle(title);
        storylineTitle.setText(title);
    }

    @Override
    public void setColorAndDisplay(Color color) {
        setColor(color);
        line.setStroke(color);
        storylineTitle.setStyle("-fx-text-fill: " + GlobalColor.colorToHex(color) + ";");
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        for (EventCard eventCard : eventCards) {
            if (eventCard.getStoryline() == this) {
                eventCard.setColorAndDisplay(color);
            }
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public void resetMinMaxIndex() {
        minIndex = Integer.MAX_VALUE;
        maxIndex = Integer.MIN_VALUE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject storylineObject = super.writeJSONObject();
        storylineObject.put("type", "Storyline");
        return storylineObject;
    }

    public static Storyline readJSONObjectAsComponentID(JSONObject componentObject) {
        //if not correct type, throw error?
        Storyline readStoryline = (Storyline) ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
        readStoryline.initializeDisplayAfterRead();
        return readStoryline;
    }

    private void initializeDisplayAfterRead() {
        loadFXML("Storyline.fxml");
        initializeContextMenu();
    }

    @FXML
    public void initialize() {
        // toggle override setter
        setTitleAndDisplay(getTitle());
        setColorAndDisplay(getColor());

        // When Storyline get right click
        root.setOnContextMenuRequested((ContextMenuEvent event) -> {
            contextMenu.hide();
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        // When click the storyline title to change text
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!title.equals(storylineTitle.getText())) {
                setTitleAndDisplay(storylineTitle.getText());
                System.out.println("Storyline title set to " + title);
            }
            storylineTitle.setDisable(true);
        });
        modifyStorylineStructure();

        // When Storyline get drag over
        root.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // When dragged item (Event Card) is dropped to storyline
        root.setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
            if (item instanceof EventCard) {
                EventCard eventCard = (EventCard) item;
                eventCard.setStorylineAndDisplay(this);
                ApplicationUtils.updateWorkspace();
            }
            event.consume();
        });
    }

    public void modifyStorylineStructure() {
        // setup all column constraint
        int gapSize = 30;
        int columnSize = SystemUtils.EVENT_CARD_PREF_WIDTH + gapSize;
        ObservableList<ColumnConstraints> columnConstraints = FXCollections.observableArrayList();
        for (int i = 0; i < getMaxIndex(); i++) {
            columnConstraints.add(new ColumnConstraints(columnSize));
        }
        eventCardContainer.getColumnConstraints().setAll(columnConstraints);

        // setup line width
        if (eventCardContainer.getChildren().size() > 0) {
            // line.setStartX(columnSize * getMinIndex() + columnSize - gapSize);
            line.setEndX(columnSize * getMaxIndex() + columnSize * 2);
        } else {
            line.setStartX(0);
            line.setEndX(columnSize);
        }

    }

    private void initializeContextMenu() {
        contextMenu.setAutoHide(true);
        contextMenu.setConsumeAutoHidingEvents(true);
        MenuItem colorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        colorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(this).show());
        MenuItem eventCardMenuItem = new MenuItem(SystemUtils.NEW_EVENT_CARD);
        eventCardMenuItem.setOnAction((ActionEvent event) -> {
            EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
            EventCard newEventCard = new EventCard();
            newEventCard.setStorylineAndDisplay(this);
            eventCards.addEventCard(newEventCard);
            ApplicationUtils.updateWorkspace();
        });
        contextMenu.getItems().addAll(colorMenuItem, eventCardMenuItem);
    }

}
