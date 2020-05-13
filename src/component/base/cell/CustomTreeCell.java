package component.base.cell;

import javafx.scene.control.TreeCell;
import javafx.scene.input.ContextMenuEvent;

public class CustomTreeCell<T> extends TreeCell<T> {

    public CustomTreeCell() {
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
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
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
    }

    @Override
    public String toString() {
        return getItem() == null ? "" : getItem().toString();
    }

    protected void initializeEventHandler() {
        setOnContextMenuRequested((ContextMenuEvent event) -> {
            System.out.println("wow");
            event.consume();
        });
    }
}
