package component.components.storyline;

import colors.GlobalColor;
import colors.RandomColor;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import component.dialog.SetColorDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
    private ContextMenu contextMenu;

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
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String componentID) {
        super(componentID);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String title, String description) {
        super(title, description);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    public Storyline(String title, String description, Color color, TimePeriod timePeriod, EventCardList eventCards) {
        super(title, description, color, timePeriod);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        initializeEventHandler();
        setColor(RandomColor.getColor());
    }

    @FXML
    public void initialize() {
        setTitle(getTitle());
        setColor(getColor());
        storylineTitle.setDisable(true);
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!title.equals(storylineTitle.getText())) {
                setTitle(storylineTitle.getText());
                System.out.println("Storyline title set to " + title);
            }
            storylineTitle.setDisable(true);
        });
        modifyStorylineStructure();
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

    public void initializedMinMaxIndex() {
        minIndex = Integer.MAX_VALUE;
        maxIndex = Integer.MIN_VALUE;
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
        storylineTitle.setStyle("-fx-text-fill: " + GlobalColor.colorToHex(color) + ";");
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        for (EventCard eventCard : eventCards) {
            if (eventCard.getStoryline() == this) {
                eventCard.setColor(color);
            }
        }
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject storylineObject = super.writeJSONObject();
        storylineObject.put("type", "Storyline");
        return storylineObject;
    }

    public void initializeEventHandler() {
        root.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        root.setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
            if (item instanceof EventCard) {
                EventCard eventCard = (EventCard) item;
                eventCard.setStoryline(this);
                ApplicationUtils.update();
            }
            event.consume();
        });
        root.setOnMousePressed((MouseEvent event) -> rightClickContextMenu(event));
    }

    private void rightClickContextMenu(MouseEvent event) {
        contextMenu.hide();
        if (event.isSecondaryButtonDown()) {
            System.out.println("Storyline: " + event.getTarget());
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
        }
        event.consume();
    }

    private void initializeContextMenu() {
        contextMenu = new ContextMenu();
        MenuItem colorMenuItem = new MenuItem("Edit storyline color");
        colorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(this).show());
        contextMenu.getItems().add(colorMenuItem);
    }

}
