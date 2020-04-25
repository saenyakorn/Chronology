package component.components.document;

import component.SavableAsJSONArray;
import org.json.simple.JSONArray;

import java.util.ArrayList;

public class DocumentList implements SavableAsJSONArray {
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
    public String getJSONString() {
        return this.getJSONArray().toJSONString();
    }

    @Override @SuppressWarnings("unchecked")
    public JSONArray getJSONArray() {
        JSONArray documentList = new JSONArray();
        for(Document document : documents) {
            documentList.add(document.getJSONObject());
        }
        return documentList;
    }
}
