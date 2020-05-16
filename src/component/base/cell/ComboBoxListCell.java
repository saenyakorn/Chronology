package component.base.cell;

import component.base.BasicStoryComponent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public final class ComboBoxListCell extends ListCell<BasicStoryComponent> {

    private final Label label;

    public ComboBoxListCell() {
        super();
        label = new Label();
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("list-cell");
    }

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
