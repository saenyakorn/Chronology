package component.components.document;

import component.ability.SavableAsJSONArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;

import java.util.Iterator;

public class DocumentList implements Iterable<Document>, SavableAsJSONArray<DocumentList> {
    private final ObservableList<DocumentCustomTab> documentCustomTabs;
    private final ObservableList<Document> documents;
    private DocumentCustomTab activeTab;

    public DocumentList() {
        activeTab = new DocumentCustomTab();
        documents = FXCollections.observableArrayList();
        documentCustomTabs = FXCollections.observableArrayList();
    }

    public ObservableList<Document> getDocuments() {
        return documents;
    }

    public ObservableList<DocumentCustomTab> getDocumentCustomTabs() {
        return documentCustomTabs;
    }

    public int indexOf(Document document) {
        return documents.indexOf(document);
    }

    public Document get(int index) {
        return (0 <= index && index < documents.size()) ? documents.get(index) : null;
    }

    public void addDocument(Document document) {
        documents.add(document);

        // Creating Custom Document Tab
        DocumentCustomTab tab = new DocumentCustomTab(document.getName());
        tab.getButton().setOnMouseClicked((MouseEvent event) -> removeDocument(document));
        tab.setOnMouseClicked((MouseEvent event) -> setActiveTab(tab));
        documentCustomTabs.add(tab);
        setActiveTab(tab);
    }

    public void removeDocument(Document document) {
        documentCustomTabs.remove(documents.indexOf(document));
        documents.remove(document);
        ApplicationUtils.getCurrentWorkspace().getSideBar().renderDocumentTreeItem();
    }

    public int getSize() {
        return documents.size();
    }

    public Document getActiveDocument() {
        return getDocumentFromTab(activeTab);
    }

    private void setActiveTab(DocumentCustomTab tab) {
        tab.setActive(true);
        activeTab.setActive(false);
        activeTab = tab;
        ApplicationUtils.getCurrentWorkspace().getViewer().setDocument(getActiveDocument());
    }

    public Document getDocumentFromTab(DocumentCustomTab tab) {
        return documents.get(documentCustomTabs.indexOf(tab));
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
            addDocument((new Document()).readJSONObject((JSONObject) documentObject));
        }
        return this;
    }

    @Override
    public Iterator<Document> iterator() {
        return documents.iterator();
    }
}
