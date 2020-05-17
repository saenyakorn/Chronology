package component.base.cell;

import component.base.BasicStoryComponent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

/**
 * Class for list cell of combo box.
 */
public final class ComboBoxListCell extends ListCell<BasicStoryComponent> {

    /**
     * Cell label.
     */
    private final Label label;

    /**
     * Constructor for ComboBoxListCell.
     */
    public ComboBoxListCell() {
        super();
        label = new Label();
        getStylesheets().add(ClassLoader.getSystemResource("component/base/cell/TreeCell.css").toExternalForm());
        getStyleClass().add("list-cell");
    }

    /**
     * Defines the look of the cell on update.
     * @param item the item contained within cell.
     * @param empty whether or not cell is empty.
     */
    @Override
    protected void updateItem(BasicStoryComponent item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getTitle());
            setGraphic(label);
        }
    }
}
