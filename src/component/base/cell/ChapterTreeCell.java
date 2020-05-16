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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import utils.ApplicationUtils;
import utils.SystemUtils;

import java.util.Optional;

public class ChapterTreeCell extends CustomTreeCell<Chapter> {

    private final SVGPath chapterIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");

    public ChapterTreeCell() {
        super();
        chapterIcon.getStyleClass().add("component-icon");
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
    protected void updateItem(Chapter item, boolean empty) {
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
            chapterIcon.setFill(item.getColor());
            setTooltip(tooltip);
            setText(item.getTitle());
            setGraphic(chapterIcon);
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
                Chapter target = getItem();
                EventCard eventCard = (EventCard) item;
                ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeEventCard(eventCard);
                eventCard.setChapterAndDisplay(target);
                ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(eventCard);
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
        removeMenuItem.setOnAction((ActionEvent event) -> removeItem());
        getCustomContextMenu().getItems().addAll(newChapter, addEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }

    private void removeItem() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(SystemUtils.CONFIRM_REMOVE_TITLE);
        confirm.setHeaderText(SystemUtils.CONFIRM_REMOVE_HEADER);
        confirm.setContentText(SystemUtils.CONFIRM_REMOVE_CONTENT);
        confirm.setGraphic(null);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            ApplicationUtils.getCurrentWorkspace().getActiveDocument().getChapters().removeChapter(getItem());
        } else {
            confirm.close();
        }
    }
}
