package component.base.cell;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import utils.SystemUtils;

import java.util.Optional;

/**
 * A customized TreeCell class for the sidebar.
 * @param <T> the type of item the TreeCell will contain.
 */
public abstract class CustomTreeCell<T> extends TreeCell<T> {

    /**
     * The context menu shown in TreeCell area
     */
    protected final ContextMenu contextMenu = new ContextMenu();

    /**
     * Constructor for CustomTreeCell.
     */
    public CustomTreeCell() {
        getStylesheets().add(ClassLoader.getSystemResource("component/base/cell/TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
        initializeContextMenu();
    }

    /**
     * Defines the look of the cell on update.
     * @param item the item contained within cell.
     * @param empty whether or not cell is empty.
     */
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
    }

    /**
     * Overrides toString method.
     * @return title.
     */
    @Override
    public String toString() {
        return getItem() == null ? "" : getItem().toString();
    }

    /**
     * Getter for context menu.
     * @return this TreeCell's context menu.
     */
    public ContextMenu getCustomContextMenu() {
        return contextMenu;
    }

    /**
     * Initializes event handler. Implementation is done be subclasses.
     */
    protected void initializeEventHandler() {
    }

    /**
     * Initializes context menu. Implementation is done be subclasses.
     */
    protected void initializeContextMenu() {
    }

    /**
     * Defines what happens when cell is removed. An alert prompt user to confirm permanent remove.
     */
    public void onRemoveItem() {
        Alert confirm = SystemUtils.getCustomConfirmationAlert(SystemUtils.CONFIRM_REMOVE_TITLE, SystemUtils.CONFIRM_REMOVE_HEADER, SystemUtils.CONFIRM_REMOVE_CONTENT);
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            removeItem();
        } else {
            confirm.close();
        }
    }

    /**
     * Removes TreeCell. Implementation is done be subclasses.
     */
    public void removeItem() {
    }
}
