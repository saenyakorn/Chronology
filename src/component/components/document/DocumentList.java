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

    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final ObservableList<DocumentCustomTab> documentCustomTabs = FXCollections.observableArrayList();
    private DocumentCustomTab activeTab = null;

    public DocumentList() {
        super();
    }

    public ObservableList<Document> listProperty() {
        return documents;
    }

    public ObservableList<DocumentCustomTab> tabsProperty() {
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
        tab.setOnMouseClicked((MouseEvent event) -> setActiveTab(tab));
        tab.setOnCloseRequest(() -> removeDocument(document));
        documentCustomTabs.add(tab);
        setActiveTab(tab);
    }

    public void removeDocument(Document document) {
        if (document == getDocumentFromTab(activeTab)) {
            int index = documentCustomTabs.indexOf(activeTab);
            System.out.println("index: " + index);
            if (index == 0 && documentCustomTabs.size() >= 2) {
                setActiveTab(documentCustomTabs.get(1));
            } else if (index == 0) {
                System.out.println("wowza");
                ApplicationUtils.newProject();
            } else {
                setActiveTab(documentCustomTabs.get(index - 1));
            }
        }
        documentCustomTabs.remove(documents.indexOf(document));
        documents.remove(document);
    }

    public int getSize() {
        return documents.size();
    }

    public Document getActiveDocument() {
        if (activeTab == null) {
            return null;
        }
        return getDocumentFromTab(activeTab);
    }

    private void setActiveTab(DocumentCustomTab tab) {
        if (tab != null) {
            tab.setActive(true);
            if (activeTab != null) {
                activeTab.setActive(false);
            }
            ApplicationUtils.getCurrentWorkspace().setActiveDocument(getDocumentFromTab(tab));
            ApplicationUtils.getCurrentWorkspace().getViewer().setDocument(getDocumentFromTab(tab));
        }
        activeTab = tab;
    }

    private Document getDocumentFromTab(DocumentCustomTab tab) {
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
