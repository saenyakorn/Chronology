package component.base;

import application.ApplicationResource;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;
import javafx.scene.shape.SVGPath;

public final class BasicStoryComponentTreeCell extends TreeCell<BasicStoryComponent> {

    TextField textField;
    private final SVGPath eventCardIcon = ApplicationResource.getIconSVG("file:res/icon/event_card_icon_24px.svg");
    private final SVGPath storylineIcon = ApplicationResource.getIconSVG("file:res/icon/storyline_icon_24px.svg");
    private final SVGPath chapterIcon = ApplicationResource.getIconSVG("file:res/icon/chapter_icon_24px.svg");

    public BasicStoryComponentTreeCell() {
        super();
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
                if(item instanceof EventCard) {
                    setGraphic(eventCardIcon);
                } else if(item instanceof Storyline) {
                    setGraphic(storylineIcon);
                } else if(item instanceof Chapter) {
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
                dragboard.setDragView(snapshot(null, null));
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
            BasicStoryComponent item = ApplicationResource.getValueFromCurrentHashMap(itemId);
            if (item instanceof EventCard && getItem() instanceof Chapter) {
                EventCard eventCard = (EventCard) item;
                Chapter target = (Chapter) getItem();
                ApplicationResource.getCurrentWorkspace().getActiveDocument().removeEventCard(eventCard);
                eventCard.setChapter(target);
                ApplicationResource.getCurrentWorkspace().getActiveDocument().addEventCard(eventCard);
                ApplicationResource.update();
            }
            event.consume();
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }
}
