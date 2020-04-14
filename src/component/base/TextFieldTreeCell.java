package component.base;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

public final class TextFieldTreeCell extends TreeCell<BasicStoryComponent> {

    TextField textField;

    public TextFieldTreeCell() {
        super();
        addEventListenerToThisNode();
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

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased((KeyEvent event) -> {
            System.out.println("On KeyPress");
            if (event.getCode() == KeyCode.ENTER) {
                BasicStoryComponent currentItem = getItem();
                currentItem.setTitle(textField.getText());
                commitEdit(currentItem);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        textField.setOnMouseClicked((MouseEvent event) -> {

        });
    }

    private void addEventListenerToThisNode() {
        this.setOnDragDone((DragEvent event) -> {
            System.out.println("Drag Done");
        });
        this.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Drag Detected");
            Dragboard dragboard = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(getItem().getComponentId());
            dragboard.setContent(clipboardContent);
            event.consume();
        });
        this.setOnDragEntered((DragEvent event) -> {
            System.out.println("Drag Enter");
            event.consume();
        });
        this.setOnDragExited((DragEvent event) -> {
            System.out.println("Drag Exited");
            event.consume();
        });
        this.setOnDragOver((DragEvent event) -> {
            System.out.println("Drag Over");
            event.consume();
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }
}
