package component.base.cell;

import colors.GlobalColor;
import component.components.eventCard.EventCard;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class EventCardTreeCell extends CustomTreeCell<EventCard> {

    private final TextField textField = new TextField();
    private final SVGPath svgIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");

    public EventCardTreeCell() {
        super();
        svgIcon.getStyleClass().add("icon-24px");
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
    protected void updateItem(EventCard item, boolean empty) {
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
                if (item instanceof EventCard) {
                    if (item.getStoryline() != null) {
                        svgIcon.setFill(item.getStoryline().getColor());
                    } else {
                        svgIcon.setFill(GlobalColor.DEFAULT_COLOR);
                    }
                    setGraphic(svgIcon);
                }
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
        setOnDragDetected((MouseEvent event) -> {
            if (getItem() != null) {
                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(parameters, null));
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(getItem().getComponentId());
                dragboard.setContent(clipboardContent);
                event.consume();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }
}
