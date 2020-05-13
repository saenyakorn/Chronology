package component.base.cell;

import component.components.storyline.Storyline;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class StorylineTreeCell extends CustomTreeCell<Storyline> {

    private final TextField textField = new TextField();
    private final SVGPath svgIcon = SystemUtils.getIconSVG("storyline_icon_24px.svg");

    public StorylineTreeCell() {
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
    protected void updateItem(Storyline item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                svgIcon.setFill(item.getColor());
                setGraphic(svgIcon);
            }
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

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }
}
