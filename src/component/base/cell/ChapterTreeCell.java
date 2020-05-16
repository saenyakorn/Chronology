package component.base.cell;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.dialog.edit.SetColorDialog;
import component.dialog.edit.SetDescriptionDialog;
import component.dialog.edit.SetTitleDialog;
import component.dialog.initialize.NewChapterDialog;
import component.dialog.initialize.NewEventCardDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class ChapterTreeCell extends CustomTreeCell<BasicStoryComponent> {

    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");
    private final SVGPath chapterIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");

    public ChapterTreeCell() {
        super();
        chapterIcon.getStyleClass().add("icon-24px");
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
            if (item instanceof Chapter) {
                chapterIcon.setFill(item.getColor());
                setGraphic(chapterIcon);
            } else if (item instanceof EventCard) {
                eventCardIcon.setFill(item.getColor());
                setGraphic(eventCardIcon);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
            if (item instanceof EventCard) {
                Chapter target = (Chapter) getItem();
                EventCard eventCard = (EventCard) item;
                eventCard.setChapterAndDisplay(target);
                ApplicationUtils.updateWorkspace();
            }
            event.consume();
        });
    }

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
        MenuItem newChapter = new MenuItem(SystemUtils.NEW_CHAPTER);
        newChapter.setOnAction((ActionEvent event) -> new NewChapterDialog().show());
        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());
        getCustomContextMenu().getItems().addAll(newChapter, addEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    @Override
    public void removeItem() {
        if (getItem() instanceof Chapter) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getChapters().removeChapter((Chapter) getItem());
        } else if (getItem() instanceof EventCard) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards().removeEventCard((EventCard) getItem());
        }
    }
}
