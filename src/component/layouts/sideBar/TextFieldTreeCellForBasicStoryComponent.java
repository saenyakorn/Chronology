package component.layouts.sideBar;

import component.base.BasicStoryComponent;
import javafx.scene.control.TreeCell;

public final class TextFieldTreeCellForBasicStoryComponent extends TreeCell<BasicStoryComponent> {

    public TextFieldTreeCellForBasicStoryComponent() {
        super();
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
    protected void updateItem(BasicStoryComponent item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.toString());
        }
    }

    @Override
    public String toString() {
        return getItem() == null ? "null" : getItem().toString();
    }
}
