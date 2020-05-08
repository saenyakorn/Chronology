package component.components.document;

import ability.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class DocumentList implements Iterable<Document>, SavableAsJSONArray<DocumentList> {
    private final ObservableList<Document> documents;

    public DocumentList() {
        documents = FXCollections.observableArrayList();
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
        return this.writeJSONArray().toJSONString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray documentArray = new JSONArray();
        for (Document document : documents) {
            documentArray.add(document.writeJSONObject());
        }
        return documentArray;
    }

    @Override
    public DocumentList readJSONArray(JSONArray documentArray) {
        for (Object documentObject : documentArray) {
            documents.add((new Document()).readJSONObject((JSONObject) documentObject));
        }
        return this;
    }

    @Override
    public Iterator<Document> iterator() {
        return documents.iterator();
    }
}
