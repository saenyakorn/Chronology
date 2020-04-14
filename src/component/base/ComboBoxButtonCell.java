package component.base;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public final class ComboBoxButtonCell extends ListCell<BasicStoryComponent> {

    private final Label label;

    public ComboBoxButtonCell() {
        super();
        label = new Label();
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
