package component.components.document;

import java.util.ArrayList;

public class DocumentList {
    private ArrayList<Document> documents;

    public DocumentList() {
        this.documents = new ArrayList<>();
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void deleteDocument(Document document) {
        documents.remove(document);
    }

    public void swap(Document document1, Document document2) {
        int index1 = documents.indexOf(document1);
        int index2 = documents.indexOf(document2);
        if (index1 != -1 && index2 != -1) {
            documents.set(index1, document2);
            documents.set(index2, document1);
        }
    }
}
