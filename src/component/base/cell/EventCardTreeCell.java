package component.base.cell;

import colors.GlobalColor;
import component.components.eventCard.EventCard;
import component.dialog.edit.*;
import component.dialog.initialize.NewEventCardDialog;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * An instance of CustomTreeCell that contains an event card.
 */
public class EventCardTreeCell extends CustomTreeCell<EventCard> {

    /**
     * Event card icon SVG.
     */
    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");

    /**
     * Constructor for EventCardTreeCell.
     */
    public EventCardTreeCell() {
        super();
        eventCardIcon.getStyleClass().add("icon-24px");
    }

    /**
     * Defines the look of the cell on update. Icon is set to eventCardIcon, and color of icon will depend on its storyline color.
     * A tooltip showing the event card description is also initialized.
     * @param item the item contained within cell.
     * @param empty whether or not cell is empty.
     */
    @Override
    public void updateItem(EventCard item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        } else {
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(new Duration(SystemUtils.TOOLTIP_SHOW_DELAY));
            tooltip.setHideDelay(new Duration(0));
            tooltip.setText("Desc: " + item.getDescription() + "\n" +
                    "Chapter: " + (getItem().getChapter() == null ? "none" : getItem().getChapter()) + "\n" +
                    "Storyline: " + (getItem().getStoryline() == null ? "none" : getItem().getStoryline()));
            if (item.getStoryline() != null) {
                eventCardIcon.setFill(item.getStoryline().getColor());
            } else {
                eventCardIcon.setFill(GlobalColor.DEFAULT_COLOR);
            }
            setTooltip(tooltip);
            setText(item.getTitle());
            setGraphic(eventCardIcon);
            setContextMenu(getCustomContextMenu());
        }
    }

    /**
     * Overrides toString method.
     * @return title.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Initializes event handler. Event cards will be able to be dragged onto the viewer.
     */
    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        setOnDragDetected((MouseEvent event) -> {
            if (getItem() != null) {
                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(parameters, null));
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(getItem().getComponentId());
                dragboard.setContent(clipboardContent);
                event.consume();
            }
        });
    }

    /**
     * Initializes context menu. There are 7 context menus.
     * <ol>
     *     <li><i>New Event Card</i> to create a new event card.</li>
     *     <li><i>Edit Title Menu</i> to edit the event card's title.</li>
     *     <li><i>Edit Description Menu</i> to edit the event card's description.</li>
     *     <li><i>Edit Date and Time Menu</i> to edit the event card's time period.</li>
     *     <li><i>Move to Chapter</i> to edit the event card's time period.</li>
     *     <li><i>Move to Storyline</i> to edit the event card's time period.</li>
     *     <li><i>Remove</i> to remove this event card.</li>
     * </ol>
     */
    @Override
    protected void initializeContextMenu() {
        MenuItem newEventCardMenuItem = new MenuItem(SystemUtils.NEW_EVENT_CARD);
        newEventCardMenuItem.setOnAction((ActionEvent event) -> new NewEventCardDialog().show());

        MenuItem editTitleMenuItem = new MenuItem(SystemUtils.EDIT_TITLE);
        editTitleMenuItem.setOnAction((ActionEvent event) -> new SetTitleDialog(getItem()).show());

        MenuItem editDescriptionMenuItem = new MenuItem(SystemUtils.EDIT_DESCRIPTION);
        editDescriptionMenuItem.setOnAction((ActionEvent event) -> new SetDescriptionDialog(getItem()).show());

        MenuItem editDateTimeMenuItem = new MenuItem(SystemUtils.EDIT_DATA_TIME);
        editDateTimeMenuItem.setOnAction((ActionEvent event) -> new SetTimePeriodDialog(getItem()).show());

        MenuItem editStoryline = new MenuItem(SystemUtils.MOVE_TO_CHAPTER);
        editStoryline.setOnAction((ActionEvent event) -> new SetChapterDialog(getItem()).show());

        MenuItem editChapter = new MenuItem(SystemUtils.MOVE_TO_STORYLINE);
        editChapter.setOnAction((ActionEvent event) -> new SetStorylineDialog(getItem()).show());

        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());

        getCustomContextMenu().getItems().addAll(newEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editDateTimeMenuItem, editChapter, editStoryline, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    /**
     * Removes event card from the workspace.
     */
    @Override
    public void removeItem() {
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeEventCard(getItem());
    }
}
