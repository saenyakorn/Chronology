package component.base.cell;

import colors.GlobalColor;
import component.components.document.Document;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import utils.ApplicationUtils;
import utils.SystemUtils;

public final class DocumentTreeCell extends CustomTreeCell<Document> {

    private TextField textField;
    private final SVGPath documentIcon = SystemUtils.getIconSVG("document_icon_24px.svg");

    public DocumentTreeCell() {
        super();
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
        documentIcon.getStyleClass().add("component-icon");
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
                documentIcon.setFill(GlobalColor.DEFAULT_COLOR);
                setGraphic(documentIcon);
            }
        }
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

    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        setOnMouseClicked((MouseEvent event) -> {
            if (getItem() instanceof Document) {
                ApplicationUtils.getCurrentWorkspace().setActiveDocument(getItem());
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}
