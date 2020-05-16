package component.base.cell;

import component.base.BasicStoryComponent;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.dialog.edit.SetColorDialog;
import component.dialog.edit.SetDescriptionDialog;
import component.dialog.edit.SetTitleDialog;
import component.dialog.initialize.NewEventCardDialog;
import component.dialog.initialize.NewStorylineDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
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
            tooltip.setText("desc: " + item.getDescription());
            setTooltip(tooltip);
            setText(item.getTitle());
            setContextMenu(getCustomContextMenu());
            if (item instanceof Storyline) {
                storylineIcon.setFill(item.getColor());
                setGraphic(storylineIcon);
            } else if (item instanceof EventCard) {
                eventCardIcon.setFill(item.getColor());
                setGraphic(eventCardIcon);
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
     * Initializes event handler.
     */
    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
    }

    /**
     * Initializes context menu.
     */
    @Override
    protected void initializeContextMenu() {
        MenuItem editColorMenuItem = new MenuItem(SystemUtils.EDIT_COLOR);
        editColorMenuItem.setOnAction((ActionEvent event) -> new SetColorDialog(getItem()).show());
        MenuItem editTitleMenuItem = new MenuItem(SystemUtils.EDIT_TITLE);
        editTitleMenuItem.setOnAction((ActionEvent event) -> new SetTitleDialog(getItem()).show());
        MenuItem editDescriptionMenuItem = new MenuItem(SystemUtils.EDIT_DESCRIPTION);
        editDescriptionMenuItem.setOnAction((ActionEvent event) -> new SetDescriptionDialog(getItem()).show());
        MenuItem addEventCardMenuItem = new MenuItem(SystemUtils.NEW_EVENT_CARD_TO);
        addEventCardMenuItem.setOnAction((ActionEvent event) -> new NewEventCardDialog(getItem()).show());
        MenuItem newStoryline = new MenuItem(SystemUtils.NEW_STORYLINE);
        newStoryline.setOnAction((ActionEvent event) -> new NewStorylineDialog().show());
        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());
        getCustomContextMenu().getItems().addAll(newStoryline, addEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    /**
     * Removes component from the workspace.
     */
    @Override
    public void removeItem() {
        if (getItem() instanceof Storyline) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getStorylines().removeStoryline((Storyline) getItem());
        } else if (getItem() instanceof EventCard) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards().removeEventCard((EventCard) getItem());
        }
    }
}
