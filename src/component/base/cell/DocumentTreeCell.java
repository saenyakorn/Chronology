package component.base.cell;

import colors.GlobalColor;
import component.components.document.Document;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import utils.ApplicationUtils;
import utils.SystemUtils;

public final class DocumentTreeCell extends CustomTreeCell<Document> {

    private final SVGPath documentIcon = SystemUtils.getIconSVG("document_icon_24px.svg");

    public DocumentTreeCell() {
        super();
        getStylesheets().add(getClass().getResource("TreeCell.css").toExternalForm());
        getStyleClass().add("tree-cell");
        initializeEventHandler();
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
    protected void updateItem(Document item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem().getName());
            documentIcon.setFill(GlobalColor.DEFAULT_COLOR);
            setGraphic(documentIcon);
        }
    }

    @Override
    protected void initializeEventHandler() {
        super.initializeEventHandler();
        setOnMouseClicked((MouseEvent event) -> {
            if (getItem() != ApplicationUtils.getCurrentWorkspace().getActiveDocument()) {
                ApplicationUtils.getCurrentWorkspace().setActiveDocument(getItem());
            }
        });
    }

}
