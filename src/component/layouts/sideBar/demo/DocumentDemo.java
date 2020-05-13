package component.layouts.sideBar.demo;

import component.base.cell.DocumentTreeCell;
import component.components.document.Document;
import utils.SystemUtils;

public class DocumentDemo extends Demo<Document> {

    public DocumentDemo() {
        setHeader(SystemUtils.DOCUMENT_DEMO_HEADER);
        getTreeView().setCellFactory(params -> new DocumentTreeCell());
    }
}
