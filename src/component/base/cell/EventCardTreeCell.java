package component.base.cell;

import colors.GlobalColor;
import component.components.eventCard.EventCard;
import component.dialog.edit.SetDescriptionDialog;
import component.dialog.edit.SetTitleDialog;
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

public class EventCardTreeCell extends CustomTreeCell<EventCard> {

    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");

    public EventCardTreeCell() {
        super();
        eventCardIcon.getStyleClass().add("icon-24px");
    }

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

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
            tooltip.setText("desc: " + item.getDescription());
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

    @Override
    public String toString() {
        return super.toString();
    }

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

    @Override
    protected void initializeContextMenu() {
        MenuItem editDateTimeMenuItem = new MenuItem(SystemUtils.EDIT_DATA_TIME);
        editDateTimeMenuItem.setOnAction((ActionEvent event) -> new NewEventCardDialog().show());
        MenuItem editChapter = new MenuItem(SystemUtils.MOVE_TO_STORYLINE);
        editChapter.setOnAction((ActionEvent event) -> new SetTitleDialog(getItem()).show());
        MenuItem editStoryline = new MenuItem(SystemUtils.MOVE_TO_CHAPTER);
        editStoryline.setOnAction((ActionEvent event) -> new SetDescriptionDialog(getItem()).show());
        MenuItem newEventCardMenuItem = new MenuItem(SystemUtils.NEW_EVENT_CARD);
        newEventCardMenuItem.setOnAction((ActionEvent event) -> new NewEventCardDialog().show());
        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());
        getCustomContextMenu().getItems().addAll(newEventCardMenuItem, editDateTimeMenuItem, editChapter, editStoryline, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    @Override
    public void removeItem() {
        ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards().removeEventCard(getItem());
    }
}
