package component.layouts.sideBar.demo;

import component.base.cell.DocumentTreeCell;
import component.components.document.Document;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class DocumentDemo extends Demo<Document> {

    private final SVGPath documentIcon = SystemUtils.getIconSVG("check_icon_24px.svg");

    public DocumentDemo() {
        setIcon(documentIcon);
        setHeader(SystemUtils.DOCUMENT_DEMO_HEADER);
        getTreeView().setCellFactory(params -> new DocumentTreeCell());
    }
}
