package application;

import component.components.document.DocumentList;

public class ApplicationResource {
    private static DocumentList documentList;

    public static void initialize() {
        ApplicationResource.documentList = new DocumentList();
    }

    public static DocumentList getDocumentList() {
        return documentList;
    }

    public static void setDocumentList(DocumentList documentList) {
        ApplicationResource.documentList = documentList;
    }
}
