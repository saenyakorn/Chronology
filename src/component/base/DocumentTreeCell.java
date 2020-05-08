package component.base;

import application.ApplicationResource;
import component.components.document.Document;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;

public final class DocumentTreeCell extends TreeCell<Document> {

    TextField textField;
    private final SVGPath documentIcon = ApplicationResource.getIconSVG("file:res/icon/document_icon_24px.svg");

    public DocumentTreeCell() {
        super();
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
        documentIcon.getStyleClass().add("icon-24px");
        this.getStyleClass().add("heading");
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
        setText(getItem().getName());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    protected void updateItem(Document item, boolean empty) {
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
                setGraphic(documentIcon);
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
                Document currentItem = getItem();
                currentItem.setName(textField.getText());
                commitEdit(currentItem);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private void initializeEventHandler() {
        setOnMouseClicked((MouseEvent event) -> {
            if (getItem() instanceof Document) {
                ApplicationResource.getCurrentWorkspace().setActiveDocument(getItem());
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}
