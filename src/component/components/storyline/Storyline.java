package component.components.storyline;

import colors.GlobalColor;
import colors.RandomColor;
import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.timeModifier.TimePeriod;
import component.dialog.edit.SetColorDialog;
import component.dialog.edit.SetDescriptionDialog;
import component.dialog.edit.SetTimePeriodDialog;
import component.dialog.edit.SetTitleDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

/**
 * An instance of BasicStoryComponent. A storyline is a timeline containing event cards.
 *
 * @see StorylineList
 */
public class Storyline extends BasicStoryComponent {
    /**
     * The context menu shown in storyline area.
     */
    private final ContextMenu contextMenu = new ContextMenu();
    /**
     * The min index of event cards in this storyline.
     */
    private int minIndex = Integer.MAX_VALUE;
    /**
     * The max index of event cards in this storyline.
     */
    private int maxIndex = Integer.MIN_VALUE;
    /**
     * Root node.
     */
    @FXML
    private Pane root;
    /**
     * Displayed line.
     */
    @FXML
    private Line line;
    /**
     * Where storyline's title is displayed.
     */
    @FXML
    private TextField storylineTitle;
    /**
     * Area containing storyline's title.
     */
    @FXML
    private HBox storylineTitleContainer;
    /**
     * Area containing event cards in this storyline.
     */
    @FXML
    private GridPane eventCardContainer;

    /**
     * No-arg constructor of Storyline. All fields are set to default values.
     */
    public Storyline() {
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        setColorAndDisplay(RandomColor.getColor());
    }

    /**
     * Constructor for Storyline that requires componentID. All fields are set to default values. Used to populate HashMap during file opening process.
     *
     * @param componentID this storyline's unique ID.
     */
    public Storyline(String componentID) {
        super(componentID);
    }

    /**
     * Constructor for Storyline that requires title and description. Remaining fields are set to default values.
     *
     * @param title       this storyline's title.
     * @param description this storyline's description.
     */
    public Storyline(String title, String description) {
        super(title, description);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
        setColorAndDisplay(RandomColor.getColor());
    }

    /**
     * Constructor for Storyline that requires all fields.
     *
     * @param title       this storyline's title.
     * @param description this storyline's description.
     * @param color       this storyline's Color.
     * @param timePeriod  this storyline's TimePeriod.
     */
    public Storyline(String title, String description, Color color, TimePeriod timePeriod) {
        super(title, description, color, timePeriod);
        loadFXML("Storyline.fxml");
        initializeContextMenu();
    }

    /**
     * Loads data in the JSONObject into a storyline.
     *
     * @param componentObject the JSONObject that is to be read.
     * @return a storyline with data loaded from the componentObject parameter.
     */
    public static Storyline readJSONObjectAsComponentID(JSONObject componentObject) {
        //if not correct type, throw error?
        Storyline readStoryline = (Storyline) ApplicationUtils.getValueFromCurrentHashMap((String) componentObject.get("componentID"));
        readStoryline.initializeDisplayAfterRead();
        return readStoryline;
    }

    /**
     * Gets the root node of this storyline.
     *
     * @return the root node.
     */
    public Pane getDisplay() {
        return root;
    }

    /**
     * Getter for eventCardContainer.
     *
     * @return this storyline's eventCardContainer.
     */
    public GridPane getContainer() {
        return eventCardContainer;
    }

    /**
     * Getter for minIndex.
     *
     * @return this storyline's minIndex.
     */
    public int getMinIndex() {
        return minIndex == Integer.MAX_VALUE ? 0 : minIndex;
    }

    /**
     * Setter for minIndex.
     *
     * @param minIndex the index to be set as min.
     */
    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex < this.minIndex ? minIndex : this.minIndex;
    }

    /**
     * Getter for maxIndex.
     *
     * @return this storyline's maxIndex.
     */
    public int getMaxIndex() {
        return maxIndex == Integer.MIN_VALUE ? 0 : maxIndex;
    }

    /**
     * Setter for maxIndex.
     *
     * @param maxIndex the index to be set as max.
     */
    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex > this.maxIndex ? maxIndex : this.maxIndex;
    }

    /**
     * Sets title value and everything related to display of storyline's title. This consists of:
     * <ul>
     *     <li>Text on storylineTitle.</li>
     * </ul>
     *
     * @param title the title to be set.
     */
    @Override
    public void setTitleAndDisplay(String title) {
        setTitle(title);
        storylineTitle.setText(title);
        ApplicationUtils.updateWorkspace();
    }

    /**
     * Sets color value and everything related to display of storyline's color. This consists of:
     * <ul>
     *     <li>Color of line.</li>
     *     <li>Color of title text.</li>
     *     <li>Color of all event cards in this storyline.</li>
     * </ul>
     *
     * @param color the color to be set.
     */
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

    /**
     * Overrides toString method.
     *
     * @return title.
     */
    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * Resets maxIndex and minIndex to beginning values.
     */
    public void resetMinMaxIndex() {
        minIndex = Integer.MAX_VALUE;
        maxIndex = Integer.MIN_VALUE;
    }

    /**
     * Counts number of event cards in this storyline.
     *
     * @return number of event cards this storyline.
     */
    @Override
    public int eventCardsInComponent() {
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        int count = 0;
        for (EventCard eventCard : eventCards) {
            if (eventCard.getStoryline() == this) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Converts a storyline into a JSONObject.
     *
     * @return the passed storyline, in JSONObject form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject writeJSONObject() {
        JSONObject storylineObject = super.writeJSONObject();
        storylineObject.put("type", "Storyline");
        return storylineObject;
    }

    /**
     * Initializes everything related to this storyline's display, after storyline data is completely read from opened file.
     */
    private void initializeDisplayAfterRead() {
        loadFXML("Storyline.fxml");
        initializeContextMenu();
    }

    /**
     * FXML initialize method, called after Storyline.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Sets display of all fields.</li>
     *     <li>Setups context menu to display when right clicked, and hide when clicked elsewhere.</li>
     *     <li>Setups event card to be able to be added to this storyline by drag-drop.</li>
     *     <li>Setups title to be editable when clicked.</li>
     *     <li>Setups column constraints of the eventCardContainer.</li>
     *     <li>Setups length of line.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        setTitleAndDisplay(getTitle());
        setColorAndDisplay(getColor());

        root.setOnContextMenuRequested((ContextMenuEvent event) -> {
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        root.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            }
            event.consume();
        });

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

        storylineTitle.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
        storylineTitleContainer.setOnMouseClicked((MouseEvent event) -> storylineTitle.setDisable(false));
        storylineTitleContainer.setOnMouseExited((MouseEvent event) -> {
            if (!title.equals(storylineTitle.getText())) {
                setTitleAndDisplay(storylineTitle.getText());
                System.out.println("Storyline title set to " + title);
            }
            storylineTitle.setDisable(true);
        });

        root.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        modifyStorylineStructure();
    }

    /**
     * Setups column constraints of the eventCardContainer, and length of line.
     */
    public void modifyStorylineStructure() {
        int gapSize = 30;
        int columnSize = SystemUtils.EVENT_CARD_PREF_WIDTH + gapSize;
        ObservableList<ColumnConstraints> columnConstraints = FXCollections.observableArrayList();
        for (int i = 0; i < getMaxIndex(); i++) {
            columnConstraints.add(new ColumnConstraints(columnSize));
        }
        eventCardContainer.getColumnConstraints().setAll(columnConstraints);

        if (eventCardContainer.getChildren().size() > 0) {
            line.setEndX(columnSize * getMaxIndex() + columnSize * 2);
        } else {
            line.setStartX(0);
            line.setEndX(columnSize);
        }

    }

    /**
     * Initializes context menu. There are 5 context menus.
     * <ol>
     *     <li><i>New Event Card Menu</i> to create new a event card, then set storyline to caller's getItem(), which is the Storyline.</li>
     *     <li><i>Edit Title Menu</i> to edit the storyline's title.</li>
     *     <li><i>Edit Description Menu</i> to edit the storyline's description.</li>
     *     <li><i>Edit Color Menu</i> to edit the storyline's color.</li>
     *     <li><i>Remove Menu</i> to remove this storyline.</li>
     * </ol>
     */
    private void initializeContextMenu() {
        contextMenu.setAutoHide(true);
        contextMenu.setConsumeAutoHidingEvents(true);

        MenuItem newEventCardMenuItem = new MenuItem(SystemUtils.NEW_EVENT_CARD);
        newEventCardMenuItem.setOnAction((ActionEvent event) -> {
            EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
            EventCard newEventCard = new EventCard();
            newEventCard.setStorylineAndDisplay(this);
            eventCards.addEventCard(newEventCard);
            ApplicationUtils.updateWorkspace();
        });

        MenuItem editTitleMenuItem = new MenuItem(SystemUtils.EDIT_TITLE);
        editTitleMenuItem.setOnAction((ActionEvent event) -> new SetTitleDialog(this).show());

        MenuItem editDescriptionMenuItem = new MenuItem(SystemUtils.EDIT_DESCRIPTION);
        editDescriptionMenuItem.setOnAction((ActionEvent event) -> new SetDescriptionDialog(this).show());

        MenuItem editColorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        editColorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(this).show());

        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());

        contextMenu.getItems().addAll(newEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem, removeMenuItem);
    }

    /**
     * Removes this storyline from the document.
     */
    @Override
    public void removeItem() {
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().getStorylines().removeStoryline(this);
    }
}
