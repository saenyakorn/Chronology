package component.base.cell;

import component.base.BasicStoryComponent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

/**
 * Class for button cell of combo box.
 */
public final class ComboBoxButtonCell extends ListCell<BasicStoryComponent> {

    /**
     * Cell label.
     */
    private final Label label;

    /**
     * Constructor for ComboBoxButtonCell.
     */
    public ComboBoxButtonCell() {
        super();
        label = new Label();
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("button-cell");
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
