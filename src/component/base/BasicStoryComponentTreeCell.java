package component.base;

import application.ApplicationResource;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

public final class BasicStoryComponentTreeCell extends TreeCell<BasicStoryComponent> {

    TextField textField;

    public BasicStoryComponentTreeCell() {
        super();
        getStylesheets().add(getClass().getResource("BasicStoryComponentTreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
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
                setGraphic(getTreeItem().getGraphic());
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
        this.setOnDragDetected((MouseEvent event) -> {
            if (getItem() != null) {
                System.out.println("Drag Detected");
                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(getItem().getComponentId());
                dragboard.setContent(clipboardContent);
                event.consume();
            }
        });
        this.setOnDragOver((DragEvent event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        this.setOnDragDropped((DragEvent event) -> {
            String itemId = event.getDragboard().getString();
            BasicStoryComponent item = ApplicationResource.getValueFromCurrentWorkspaceHashMap(itemId);
            if (item instanceof EventCard && getItem() instanceof Chapter) {
                EventCard eventCard = (EventCard) item;
                Chapter target = (Chapter) getItem();
                ApplicationResource.getCurrentWorkspace().getCurrentDocument().removeEventCard(eventCard);
                target.addEventCard(eventCard);
                ApplicationResource.update();
            }
            event.consume();
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }
}
