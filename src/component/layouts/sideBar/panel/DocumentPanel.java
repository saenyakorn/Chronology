package component.layouts.sideBar.panel;

import component.base.cell.DocumentTreeCell;
import component.components.document.Document;
import component.dialog.initialize.NewDocumentDialog;
import utils.SystemUtils;

public class DocumentPanel extends Panel<Document> {

    public DocumentPanel() {
        setHeader(SystemUtils.DOCUMENT_PANEL_HEADER);
        getTreeView().setCellFactory(params -> new DocumentTreeCell());
    }

    @Override
    public void onButtonClick() {
        new NewDocumentDialog().show();
    }
}
