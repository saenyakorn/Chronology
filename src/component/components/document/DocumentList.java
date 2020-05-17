package component.components.document;

import component.ability.SavableAsJSONArray;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationUtils;
import utils.SystemUtils;

import java.util.Iterator;
import java.util.Optional;

/**
 * An ObservableList of Documents.
 *
 * @see Document
 */
public class DocumentList implements Iterable<Document>, SavableAsJSONArray<DocumentList> {

    /**
     * ObservableList of Documents.
     */
    private final ObservableList<Document> documents = FXCollections.observableArrayList(document -> new Observable[]{document.nameProperty()});
    /**
     * ObservableList of DocumentCustomTabs for each document.
     */
    private final ObservableList<DocumentCustomTab> documentCustomTabs = FXCollections.observableArrayList();
    /**
     * The currently active tab.
     */
    private DocumentCustomTab activeTab = null;

    /**
     * Constructor for DocumentList.
     */
    public DocumentList() {
        super();
        documents.addListener((ListChangeListener.Change<? extends Document> change) -> {
            for (int i = 0; i < change.getList().size(); i++) {
                documentCustomTabs.get(i).setTabText(documents.get(i).getName());
            }
        });
    }

    /**
     * Getter for this documentList's ObservableList of documents.
     *
     * @return the property of this documentList's ObservableList of documents.
     */
    public ObservableList<Document> listProperty() {
        return documents;
    }

    /**
     * Getter for this documentList's ObservableList of tabs.
     *
     * @return the property of this documentList's ObservableList of tabs.
     */
    public ObservableList<DocumentCustomTab> tabsProperty() {
        return documentCustomTabs;
    }

    /**
     * Gets index of document in list.
     *
     * @param document the document to be found.
     * @return index of specified document.
     */
    public int indexOf(Document document) {
        return documents.indexOf(document);
    }

    /**
     * Gets a document from documentList. An invalid index returns null.
     *
     * @param index index of document to get.
     * @return document at specified index.
     */
    public Document get(int index) {
        return (0 <= index && index < documents.size()) ? documents.get(index) : null;
    }

    /**
     * Adds a document to this documentList, creates a tab for the document, and sets the document as active.
     *
     * @param document the document to be added.
     */
    public void addDocument(Document document) {
        DocumentCustomTab tab = new DocumentCustomTab(document.getName());
        tab.setOnMouseClicked((MouseEvent event) -> ApplicationUtils.getCurrentWorkspace().setActiveDocument(document));
        tab.setOnCloseRequest(() -> {
            Alert confirm = SystemUtils.getCustomConfirmation(SystemUtils.CONFIRM_REMOVE_TITLE, SystemUtils.CONFIRM_REMOVE_CONTENT, SystemUtils.CONFIRM_REMOVE_CONTENT);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                removeDocument(document);
            } else {
                confirm.close();
            }
        });
        documentCustomTabs.add(tab);
        documents.add(document);
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(document);
    }

    /**
     * Removes a document from this documentList.
     *
     * @param document the document to be removed.
     */
    public void removeDocument(Document document) {
        if (documents.contains(document)) {
            if (document == getDocumentFromTab(activeTab)) {
                int index = documentCustomTabs.indexOf(activeTab);
                if (index == 0 && documentCustomTabs.size() >= 2) {
                    ApplicationUtils.getCurrentWorkspace().setActiveDocument(documents.get(1));
                } else if (index == 0) {
                    ApplicationUtils.getCurrentWorkspace().setActiveDocument(null);
                } else {
                    ApplicationUtils.getCurrentWorkspace().setActiveDocument(documents.get(index - 1));
                }
            }
            documentCustomTabs.remove(getTabFromDocument(document));
            documents.remove(document);
            document = null;
        } else {
            System.out.println("This document does not exist");
        }

    }

    /**
     * Removes all documents from this documentList.
     */
    public void removeAllDocuments() {
        while (documents.size() > 0) {
            removeDocument(documents.get(0));
        }
    }

    /**
     * Gets the size of this documentList.
     *
     * @return this documentList's size.
     */
    public int getSize() {
        return documents.size();
    }

    /**
     * Gets the active document.
     *
     * @return the active document.
     */
    public Document getActiveDocument() {
        if (activeTab == null) {
            return null;
        }
        return getDocumentFromTab(activeTab);
    }

    /**
     * Sets the active tab.
     *
     * @param tab the tab to be set as active.
     */
    public void setActiveTab(DocumentCustomTab tab) {
        if (tab != null) {
            tab.setActive(true);
            if (activeTab != null) {
                activeTab.setActive(false);
            }
        }
        activeTab = tab;
    }

    /**
     * Gets the corresponding tab of a specified document.
     *
     * @param document the document whose tab is requested.
     * @return the tab corresponding to the specified document.
     */
    public DocumentCustomTab getTabFromDocument(Document document) {
        return documentCustomTabs.get(documents.indexOf(document));
    }

    /**
     * Gets the corresponding document of a specified tab.
     *
     * @param tab the tab whose document is requested.
     * @return the document corresponding to the specified tab.
     */
    public Document getDocumentFromTab(DocumentCustomTab tab) {
        return documents.get(documentCustomTabs.indexOf(tab));
    }

    /**
     * Gets the JSON array in string format.
     *
     * @return the JSON array converted to a string.
     */
    @Override
    public String getJSONString() {
        return this.writeJSONArray().toJSONString();
    }

    /**
     * Converts a documentList into a JSONArray.
     *
     * @return the passed documentList, in JSONArray form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONArray writeJSONArray() {
        JSONArray documentArray = new JSONArray();
        for (Document document : documents) {
            documentArray.add(document.writeJSONObject());
        }
        return documentArray;
    }

    /**
     * Loads data in the JSONArray into a documentList.
     *
     * @param documentArray the JSONArray that is to be read.
     * @return a documentList with data loaded from the documentArray parameter.
     */
    @Override
    public DocumentList readJSONArray(JSONArray documentArray) {
        for (Object documentObject : documentArray) {
            String name = (String) ((JSONObject) documentObject).get("name");
            Document readDocument = new Document(name);
            ApplicationUtils.getCurrentWorkspace().getSideBar().initBindings(readDocument);
            addDocument(readDocument);
            readDocument.readJSONObject((JSONObject) documentObject);
        }
        return this;
    }

    /**
     * Gets ObservableList's iterator.
     *
     * @return the list's iterator.
     */
    @Override
    public Iterator<Document> iterator() {
        return documents.iterator();
    }
}
