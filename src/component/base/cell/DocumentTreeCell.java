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

public final class DocumentTreeCell extends CustomTreeCell<Document> {

    private final SVGPath documentIcon = SystemUtils.getIconSVG("check_icon_24px.svg");

    public DocumentTreeCell() {
        super();
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        documentIcon.getStyleClass().add("icon-24px");
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
