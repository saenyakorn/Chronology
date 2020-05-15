package component.base.cell;

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
import utils.SystemUtils;

public class StorylineTreeCell extends CustomTreeCell<Storyline> {

    private final SVGPath svgIcon = SystemUtils.getIconSVG("storyline_icon_24px.svg");

    public StorylineTreeCell() {
        super();
        svgIcon.getStyleClass().add("icon-24px");
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
    protected void updateItem(Storyline item, boolean empty) {
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
            svgIcon.setFill(item.getColor());
            setTooltip(tooltip);
            setText(item.getTitle());
            setGraphic(svgIcon);
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
        MenuItem newStoryline = new MenuItem(SystemUtils.NEW_STORYLINE);
        newStoryline.setOnAction((ActionEvent event) -> new NewStorylineDialog().show());
        getCustomContextMenu().getItems().addAll(newStoryline, addEventCardMenuItem, editTitleMenuItem, editDescriptionMenuItem, editColorMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
    }
}
