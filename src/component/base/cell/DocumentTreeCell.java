package component.base.cell;

import colors.GlobalColor;
import component.components.document.Document;
import component.dialog.edit.SetNameDialog;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * An instance of CustomTreeCell that contains a document.
 */
public final class DocumentTreeCell extends CustomTreeCell<Document> {

    /**
     * Document icon SVG.
     */
    private final SVGPath documentIcon = SystemUtils.getIconSVG("check_icon_24px.svg");

    /**
     * Constructor for DocumentTreeCell.
     */
    public DocumentTreeCell() {
        super();
        getStylesheets().add(ClassLoader.getSystemResource("component/base/cell/TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        documentIcon.getStyleClass().add("icon-24px");
    }

    /**
     * Defines the look of the cell on update. Icon is set to documentIcon, and will only be visible when the document is active.
     * @param item the item contained within cell.
     * @param empty whether or not cell is empty.
     */
    @Override
    public void updateItem(Document item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        } else {
            documentIcon.setFill(GlobalColor.DEFAULT_COLOR);
            documentIcon.setVisible(item == ApplicationUtils.getCurrentWorkspace().getActiveDocument());
            setGraphic(documentIcon);
            setText(getItem().getName());
            setContextMenu(getCustomContextMenu());
        }
    }

    /**
     * Initializes event handler. Active document will be set by clicking on a corresponding DocumentTreeCell.
     */
    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        MenuItem editNameMenuItem = new MenuItem(SystemUtils.EDIT_NAME);
        editNameMenuItem.setOnAction((ActionEvent event) -> new SetNameDialog(getItem()).show());
        MenuItem removeMenuItem = new MenuItem(SystemUtils.REMOVE);
        removeMenuItem.setOnAction((ActionEvent event) -> onRemoveItem());
        getCustomContextMenu().getItems().addAll(editNameMenuItem, removeMenuItem);
        if (getItem() != null) {
            setContextMenu(getCustomContextMenu());
        }
        setOnMouseClicked((MouseEvent event) -> {
            if (getItem() != null && getItem() != ApplicationUtils.getCurrentWorkspace().getActiveDocument()) {
                ApplicationUtils.getCurrentWorkspace().setActiveDocument(getItem());
            }
        });
    }

    @Override
    public void removeItem() {
        ApplicationUtils.getCurrentWorkspace().removeDocument(getItem());
    }
}
