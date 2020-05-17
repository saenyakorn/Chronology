package component.base.cell;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.dialog.edit.SetColorDialog;
import component.dialog.edit.SetDescriptionDialog;
import component.dialog.edit.SetTitleDialog;
import component.dialog.initialize.NewChapterDialog;
import component.dialog.initialize.NewEventCardDialog;
import exception.TypeNotMatchException;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * An instance of CustomTreeCell that contains a chapter.
 */
public class ChapterTreeCell extends CustomTreeCell<BasicStoryComponent> {

    /**
     * Event card icon SVG.
     */
    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");
    /**
     * Chapter icon SVG.
     */
    private final SVGPath chapterIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");

    /**
     * Constructor for ChapterTreeCell.
     */
    public ChapterTreeCell() {
        super();
        chapterIcon.getStyleClass().add("icon-24px");
        eventCardIcon.getStyleClass().add("icon-24px");
    }

    /**
     * Defines the look of the cell on update. Icon is set to an icon of corresponding type, and color of icon will depend on the component's color.
     * A tooltip showing the component's description is also initialized.
     *
     * @param item  the item contained within cell.
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
            if (item instanceof Chapter) {
                chapterIcon.setFill(item.getColor());
                setGraphic(chapterIcon);
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
     *
     * @return title.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Initializes event handler. Chapter will be able to be dragged onto the viewer.
     * Furthermore, It will be able to be receive dropped event cards and chapter their storyline to this.
     */
    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        setOnDragDetected((MouseEvent event) -> {
            if (getItem() != null && getItem() instanceof Chapter) {
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
        setOnDragOver((DragEvent event) -> {
            if (getItem() != null && getItem() instanceof Chapter) {
                if (event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });
        setOnDragDropped((DragEvent event) -> {
            if (getItem() != null && getItem() instanceof Chapter) {
                String itemId = event.getDragboard().getString();
                BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
                if (item instanceof EventCard) {
                    Chapter target = (Chapter) getItem();
                    EventCard eventCard = (EventCard) item;
                    eventCard.setChapterAndDisplay(target);
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
     *     <li><i>New Chapter Menu</i> to create new a chapter.</li>
     *     <li><i>New Event Card Menu</i> to create new a event card, then set chapter to caller's getItem(), which is the Chapter.</li>
     *     <li><i>Edit Title Menu</i> to edit the chapter's title.</li>
     *     <li><i>Edit Description Menu</i> to edit the chapter's description.</li>
     *     <li><i>Edit Color Menu</i> to edit the chapter's color.</li>
     *     <li><i>Remove Menu</i> to remove this chapter.</li>
     * </ol>
     */
    @Override
    protected void initializeContextMenu() {
        MenuItem newChapter = new MenuItem(SystemUtils.NEW_CHAPTER);
        newChapter.setOnAction((ActionEvent event) -> new NewChapterDialog().show());

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

        getCustomContextMenu().getItems().addAll(newChapter, addEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    /**
     * Removes component, which is Chapters or EventCards, from the workspace.
     */
    @Override
    public void removeItem() {
        if (getItem() instanceof Chapter) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getChapters().removeChapter((Chapter) getItem());
        } else if (getItem() instanceof EventCard) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards().removeEventCard((EventCard) getItem());
        } else {
            try {
                throw new TypeNotMatchException("Removed item should be Chapter or EventCard");
            } catch (TypeNotMatchException e) {
                e.printStackTrace();
            }
        }
    }
}
