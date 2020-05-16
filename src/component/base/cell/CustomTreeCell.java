package component.base.cell;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;

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
}
