package component.base.cell;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.dialog.edit.SetColorDialog;
import component.dialog.edit.SetDescriptionDialog;
import component.dialog.edit.SetTitleDialog;
import component.dialog.initialize.NewEventCardDialog;
import component.dialog.initialize.NewStorylineDialog;
import exception.TypeNotMatchException;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * An instance of CustomTreeCell that contains a storyline.
 */
public class StorylineTreeCell extends CustomTreeCell<BasicStoryComponent> {

    /**
     * Event card icon SVG.
     */
    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");
    /**
     * Storyline icon SVG.
     */
    private final SVGPath storylineIcon = SystemUtils.getIconSVG("storyline_icon_24px.svg");

    /**
     * Constructor for StorylineTreeCell.
     */
    public StorylineTreeCell() {
        super();
        eventCardIcon.getStyleClass().add("icon-24px");
        storylineIcon.getStyleClass().add("icon-24px");
    }

    /**
     * Defines the look of the cell on update. Icon is set to an icon of corresponding type, and color of icon will depend on the component's color.
     * A tooltip showing the component's description is also initialized.
     * @param item the item contained within cell.
     * @param empty whether or not cell is empty.
     */
    @Override
    public void updateItem(BasicStoryComponent item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        } else {
            Tooltip tooltip = new Tooltip();
            tooltip.setShowDelay(new Duration(SystemUtils.TOOLTIP_SHOW_DELAY));
            tooltip.setHideDelay(new Duration(0));
            tooltip.setText("Desc: " + item.getDescription());
            setTooltip(tooltip);
            setText(item.getTitle());
            setContextMenu(getCustomContextMenu());
            if (item instanceof Storyline) {
                storylineIcon.setFill(item.getColor());
                setGraphic(storylineIcon);
            } else if (item instanceof EventCard) {
                eventCardIcon.setFill(item.getColor());
                setGraphic(eventCardIcon);
            } else {
                try {
                    throw new TypeNotMatchException("Item should be Storyline or EventCard");
                } catch (TypeNotMatchException e) {
                    e.printStackTrace();
                }
            }
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
     * Initializes event handler. Storyline will be able to be receive dropped event cards and set their storyline to this.
     */
    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        setOnDragOver((DragEvent event) -> {
            if (getItem() != null && getItem() instanceof Storyline) {
                if (event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });
        setOnDragDropped((DragEvent event) -> {
            if (getItem() != null && getItem() instanceof Storyline) {
                String itemId = event.getDragboard().getString();
                BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
                if (item instanceof EventCard) {
                    Storyline target = (Storyline) getItem();
                    EventCard eventCard = (EventCard) item;
                    eventCard.setStorylineAndDisplay(target);
                    ApplicationUtils.updateWorkspace();
                } else {
                    try {
                        throw new TypeNotMatchException("Dropped item should be EventCard");
                    } catch (TypeNotMatchException e) {
                        e.printStackTrace();
                    }
                }
                event.consume();
            }
        });
    }

    /**
     * Initializes context menu. There are 6 context menus.
     * <ol>
     *     <li><i>New Storyline Menu</i> to create new a storyline.</li>
     *     <li><i>New Event Card Menu</i> to create new a event card, then set storyline to caller's getItem(), which is the Storyline.</li>
     *     <li><i>Edit Title Menu</i> to edit the storyline's title.</li>
     *     <li><i>Edit Description Menu</i> to edit the storyline's description.</li>
     *     <li><i>Edit Color Menu</i> to edit the storyline's color.</li>
     *     <li><i>Remove Menu</i> to remove this storyline.</li>
     * </ol>
     */
    @Override
    protected void initializeContextMenu() {
        MenuItem newStoryline = new MenuItem(SystemUtils.NEW_STORYLINE);
        newStoryline.setOnAction((ActionEvent event) -> new NewStorylineDialog().show());

        MenuItem addEventCardMenuItem = new MenuItem(SystemUtils.NEW_EVENT_CARD_TO);
        addEventCardMenuItem.setOnAction((ActionEvent event) -> new NewEventCardDialog(getItem()).show());

        MenuItem editTitleMenuItem = new MenuItem(SystemUtils.EDIT_TITLE);
        editTitleMenuItem.setOnAction((ActionEvent event) -> new SetTitleDialog(getItem()).show());

        MenuItem editDescriptionMenuItem = new MenuItem(SystemUtils.EDIT_DESCRIPTION);
        editDescriptionMenuItem.setOnAction((ActionEvent event) -> new SetDescriptionDialog(getItem()).show());

        MenuItem editColorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        editColorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(getItem()).show());

        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());

        getCustomContextMenu().getItems().addAll(newStoryline, addEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    /**
     * Removes component, which is Storylines or EventCards, from the workspace.
     */
    @Override
    public void removeItem() {
        if (getItem() instanceof Storyline) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeStoryline((Storyline) getItem());
        } else if (getItem() instanceof EventCard) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeEventCard((EventCard) getItem());
        } else {
            try {
                throw new TypeNotMatchException("Removed item should be Storyline or EventCard");
            } catch (TypeNotMatchException e) {
                e.printStackTrace();
            }
        }
    }
}
