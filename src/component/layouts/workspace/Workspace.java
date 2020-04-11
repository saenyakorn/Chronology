package component.layouts.workspace;

import component.components.chapter.Chapter;
import component.components.document.Document;
import component.components.document.DocumentList;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.layouts.sideBar.SideBar;
import component.layouts.viewer.Viewer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Workspace extends HBox {
    private DocumentList documents;
    private Viewer viewer;
    private SideBar sideBar;

    public Workspace() {
        // Construct components
        documents = new DocumentList();
        viewer = new Viewer();
        sideBar = new SideBar();

        // Set Style
        HBox.setHgrow(viewer, Priority.ALWAYS);

        // Add initial example document
        Document doc1 = new Document("New Document");
        Storyline storyline_ex1 = new Storyline();
        EventCard eventCard_ex1 = new EventCard("Event 1", "Desc 1");
        EventCard eventCard_ex2 = new EventCard("Event 2", "Desc 2");
        EventCard eventCard_ex3 = new EventCard("Event 3", "Desc 3");
        Chapter chapter_ex1 = new Chapter("Chapter 1", "description for chapter 1");
        EventCard eventCard_ex4 = new EventCard("Event 4", "Desc 4");
        EventCard eventCard_ex5 = new EventCard("Event 5", "Desc 5");
        chapter_ex1.addAllEventCards(eventCard_ex3, eventCard_ex4, eventCard_ex5);
        storyline_ex1.addAllEventCards(eventCard_ex1, eventCard_ex2, eventCard_ex3, eventCard_ex4, eventCard_ex5);
        doc1.addStoryLine(storyline_ex1);
        doc1.addChapter(chapter_ex1);
        doc1.addAllEventCard(eventCard_ex1, eventCard_ex2);
        Document doc2 = new Document("New Document 2");
        Document doc3 = new Document("New Document 3");
        this.addAllDocument(doc1, doc2, doc3);

        // Changing tab event
        this.viewer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {
                Document selectedDocument = (Document) t1;
                sideBar.setActiveDocument(selectedDocument);
            }
        });

        // Added all components into HBox
        this.getChildren().addAll(sideBar, viewer);
    }

    public DocumentList getDocumentList() {
        return documents;
    }

    public Document getCurrentDocument() {
        return documents.get(viewer.getSelectionModel().getSelectedIndex());
    }

    public void addDocument(Document document) {
        System.out.println(document.getText());
        documents.addDocument(document);
        viewer.addDocument(document);
        Document currentDocument = this.getCurrentDocument();
        sideBar.setActiveDocument(currentDocument);
    }

    public void addAllDocument(Document... args) {
        for (Document document : args) {
            documents.addDocument(document);
            viewer.addDocument(document);
        }
        Document currentDocument = this.getCurrentDocument();
        sideBar.setActiveDocument(currentDocument);
    }

    public void removeDocument(Document document) {
        documents.removeDocument(document);
        viewer.removeDocument(document);
    }
}
