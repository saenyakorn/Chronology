package component.base.cell;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import utils.SystemUtils;

import java.util.Optional;

public class CustomTreeCell<T> extends TreeCell<T> {

    protected final ContextMenu contextMenu = new ContextMenu();

    public CustomTreeCell() {
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
        initializeContextMenu();
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
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
    }

    @Override
    public String toString() {
        return getItem() == null ? "" : getItem().toString();
    }

    public ContextMenu getCustomContextMenu() {
        return contextMenu;
    }

    protected void initializeEventHandler() {
    }

    protected void initializeContextMenu() {
    }

    public void onRemoveItem() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(SystemUtils.CONFIRM_REMOVE_TITLE);
        confirm.setHeaderText(SystemUtils.CONFIRM_REMOVE_HEADER);
        confirm.setContentText(SystemUtils.CONFIRM_REMOVE_CONTENT);
        confirm.setGraphic(null);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            removeItem();
        } else {
            confirm.close();
        }
    }

    public void removeItem() {
    }
}
