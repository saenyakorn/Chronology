package component.base.cell;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.SVGPath;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class ChapterTreeCell extends CustomTreeCell<Chapter> {

    private final TextField textField = new TextField();
    private final SVGPath svgIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");

    public ChapterTreeCell() {
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
    protected void updateItem(Chapter item, boolean empty) {
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
                if (item instanceof Chapter) {
                    svgIcon.setFill(item.getColor());
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
        setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
            if (item instanceof EventCard) {
                Chapter target = getItem();
                EventCard eventCard = (EventCard) item;
                ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeEventCard(eventCard);
                eventCard.setChapter(target);
                ApplicationUtils.getCurrentWorkspace().getActiveDocument().addEventCard(eventCard);
                ApplicationUtils.updateWorkspace();
            }
            event.consume();
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }
}
