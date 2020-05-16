package component.layouts.sideBar.panel;

import component.base.cell.DocumentTreeCell;
import component.components.document.Document;
import component.dialog.initialize.NewDocumentDialog;
import utils.SystemUtils;

/**
 * An instance of panel that contains a document TreeView.
 */
public class DocumentPanel extends Panel<Document> {

    /**
     * Constructor for DocumentPanel. Sets the header text and cell factory.
     */
    public DocumentPanel() {
        setHeader(SystemUtils.DOCUMENT_PANEL_HEADER);
        getTreeView().setCellFactory(params -> new DocumentTreeCell());
    }

    /**
     * Shows a NewDocumentDialog when button is clicked.
     * @see NewDocumentDialog
     */
    @Override
    public void onButtonClick() {
        new NewDocumentDialog().show();
    }
}
