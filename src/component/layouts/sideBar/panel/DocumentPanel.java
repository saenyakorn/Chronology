package component.layouts.sideBar.panel;

import component.base.cell.DocumentTreeCell;
import component.components.document.Document;
import utils.SystemUtils;

public class DocumentPanel extends Panel<Document> {

    public DocumentPanel() {
        setHeader(SystemUtils.DOCUMENT_DEMO_HEADER);
        getTreeView().setCellFactory(params -> new DocumentTreeCell());
    }
}
