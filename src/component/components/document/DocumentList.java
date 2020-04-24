package component.components.document;

import component.Savable;

import java.util.ArrayList;

public class DocumentList implements Savable {
    private final ArrayList<Document> documents;

    public DocumentList() {
        this.documents = new ArrayList<>();
    }

    public int indexOf(Document document) {
        return documents.indexOf(document);
    }

    public Document get(int index) {
        return (0 <= index && index < documents.size()) ? documents.get(index) : null;
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
    }

    public int getSize() {
        return documents.size();
    }

    @Override
    public void getJSON() {

    }
}
