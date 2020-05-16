package component.base.cell;

import colors.GlobalColor;
import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import utils.ApplicationUtils;
import utils.SystemUtils;

public final class BasicStoryComponentTreeCell extends TreeCell<BasicStoryComponent> {

    private TextField textField;
    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");
    private final SVGPath storylineIcon = SystemUtils.getIconSVG("storyline_icon_24px.svg");
    private final SVGPath chapterIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");

    public BasicStoryComponentTreeCell() {
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
        eventCardIcon.getStyleClass().add("icon-24px");
        storylineIcon.getStyleClass().add("icon-24px");
        chapterIcon.getStyleClass().add("icon-24px");
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getTitle());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    protected void updateItem(BasicStoryComponent item, boolean empty) {
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
                    if (((EventCard) item).getStoryline() != null) {
                        eventCardIcon.setFill(((EventCard) item).getStoryline().getColor());
                    } else {
                        eventCardIcon.setFill(GlobalColor.DEFAULT_COLOR);
                    }
                    setGraphic(eventCardIcon);
                } else if (item instanceof Storyline) {
                    storylineIcon.setFill(item.getColor());
                    setGraphic(storylineIcon);
                } else if (item instanceof Chapter) {
                    chapterIcon.setFill(item.getColor());
                    setGraphic(chapterIcon);
                }
            }
        }
    }

    private boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null) {
            return true;
        }
        while (node != null) {
            if (node == potentialHierarchyElement) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                BasicStoryComponent currentItem = getItem();
                currentItem.setTitle(textField.getText());
                commitEdit(currentItem);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private void initializeEventHandler() {
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
        setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationUtils.getValueFromCurrentHashMap(itemId);
            if (item instanceof EventCard && getItem() instanceof Chapter) {
                EventCard eventCard = (EventCard) item;
                Chapter target = (Chapter) getItem();
                ApplicationUtils.getCurrentWorkspace().getActiveDocument().removeEventCard(eventCard);
                eventCard.setChapterAndDisplay(target);
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
