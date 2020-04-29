package component.components.document;

import ablity.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;

public class DocumentList implements SavableAsJSONArray {
    private final ObservableList<Document> documents;

    public DocumentList() {
        documents = FXCollections.observableArrayList();
        documents.addListener((ListChangeListener.Change<? extends Document> change) -> {
            System.out.println("Change on documentList");
            while (change.next()) {
                System.out.println("Document -> " + change);
                if (change.wasUpdated()) {
                    System.out.println("Update detected");
                }
            }
        });
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
        JSONArray documentArray = new JSONArray();
        for(Document document : documents) {
            documentArray.add(document.getJSONObject());
        }
        return documentArray;
    }
}
