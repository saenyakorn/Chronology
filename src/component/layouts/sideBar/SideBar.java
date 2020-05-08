package component.layouts.sideBar;

import application.ApplicationResource;
import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.base.BasicStoryComponentTreeCell;
import component.base.BlankBasicStoryComponent;
import component.base.DocumentTreeCell;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.document.Document;
import component.components.document.DocumentList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SideBar extends ScrollPane {

    // class flied
    private final TreeView<Document> documentTreeView;
    private final TreeView<BasicStoryComponent> basicStoryComponentTreeView;

    // Icon for each item

    private final Image eventCardIcon = new Image("file:res/icon/event_card_icon_24px.svg");
    private final Image storylineIcon = new Image("file:res/icon/storyline_icon_24px.svg");
    private final Image chapterIcon = new Image("file:res/icon/chapter_icon_24px.svg");
    private final Image documentIcon = new Image("file:res/icon/document_icon_24px.svg");

    public SideBar() {
        // load css file
        getStylesheets().add(getClass().getResource("SideBar.css").toExternalForm());

        // basicStoryComponentTreeView setting
        basicStoryComponentTreeView = new TreeView<>();
        basicStoryComponentTreeView.setShowRoot(false);
        basicStoryComponentTreeView.setEditable(true);
        basicStoryComponentTreeView.getStyleClass().add("tree-view");
        basicStoryComponentTreeView.setCellFactory(params -> new BasicStoryComponentTreeCell());

        // documentTreeView setting
        documentTreeView = new TreeView<>();
        documentTreeView.setShowRoot(false);
        documentTreeView.setEditable(true);
        documentTreeView.getStyleClass().add("tree-view");
        documentTreeView.setCellFactory(params -> new DocumentTreeCell());

        // VBox container setting
        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.POSITIVE_INFINITY);
        vBox.getStyleClass().add("v-box");
        vBox.getChildren().add(basicStoryComponentTreeView);
        vBox.getChildren().add(documentTreeView);

        // scroll pane setting
        getStyleClass().add("side-bar");
        setFitToWidth(true);
        setPrefWidth(SystemConstants.SIDEBAR_PREF_WIDTH);
        setContent(vBox);

        // initialize TreeView
        initializeBasicStoryComponentTreeView();
        initializeTreeViewDocument();
    }

    private void initializeBasicStoryComponentTreeView() {
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>();

        // Event Card Tree Cell Construct -> index 0
        TreeItem<BasicStoryComponent> eventCardTreeItemRoot = new TreeItem<>(new BlankBasicStoryComponent("Event Card", "None"), new ImageView(eventCardIcon));

        // Storyline Tree Cell Construct -> index 1
        TreeItem<BasicStoryComponent> storylineTreeItemRoot = new TreeItem<>(new BlankBasicStoryComponent("Storyline", "None"), new ImageView(storylineIcon));

        // Chapter Tree Cell Construct -> index 2
        TreeItem<BasicStoryComponent> chapterTreeItemRoot = new TreeItem<>(new BlankBasicStoryComponent("Chapter", "None"), new ImageView(chapterIcon));

        rootItem.getChildren().addAll(eventCardTreeItemRoot, storylineTreeItemRoot, chapterTreeItemRoot);
        basicStoryComponentTreeView.setRoot(rootItem);
    }

    private void initializeTreeViewDocument() {
        TreeItem<Document> rootItem = new TreeItem<>();
        documentTreeView.setRoot(rootItem);
    }

    public void renderEventCardTreeItem(Document activeDocument) {
        EventCardList eventCardList = activeDocument.getEventCardList();
        TreeItem<BasicStoryComponent> eventCardTreeItemRoot = basicStoryComponentTreeView.getRoot().getChildren().get(0);
        eventCardTreeItemRoot.getChildren().clear();
        for (EventCard eventCard : eventCardList) {
            if (eventCard.getStoryline() == null && eventCard.getChapter() == null) {
                TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(eventCard, new ImageView(eventCardIcon));
                eventCardTreeItemRoot.getChildren().add(treeItem);
            }
        }
    }

    public void renderStorylineTreeItem(Document activeDocument) {
        EventCardList eventCardList = activeDocument.getEventCardList();
        StorylineList storylineList = activeDocument.getStorylineList();
        TreeItem<BasicStoryComponent> storylineTreeItemRoot = basicStoryComponentTreeView.getRoot().getChildren().get(1);
        storylineTreeItemRoot.getChildren().clear();
        for (Storyline storyline : storylineList) {
            TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(storyline, new ImageView(eventCardIcon));
            for (EventCard eventCard : eventCardList) {
                if (eventCard.getStoryline() == storyline) {
                    TreeItem<BasicStoryComponent> childTreeItem = new TreeItem<>(eventCard, new ImageView(eventCardIcon));
                    treeItem.getChildren().add(childTreeItem);
                }
            }
            storylineTreeItemRoot.getChildren().add(treeItem);
        }
    }

    public void renderChapterTreeItem(Document activeDocument) {
        EventCardList eventCardList = activeDocument.getEventCardList();
        ChapterList chapterList = activeDocument.getChapterList();
        TreeItem<BasicStoryComponent> chapterTreeItemRoot = basicStoryComponentTreeView.getRoot().getChildren().get(2);
        chapterTreeItemRoot.getChildren().clear();
        for (Chapter chapter : chapterList) {
            TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(chapter, new ImageView(eventCardIcon));
            for (EventCard eventCard : eventCardList) {
                if (eventCard.getChapter() == chapter) {
                    TreeItem<BasicStoryComponent> childTreeItem = new TreeItem<>(eventCard, new ImageView(eventCardIcon));
                    treeItem.getChildren().add(childTreeItem);
                }
            }
            chapterTreeItemRoot.getChildren().add(treeItem);
        }
    }

    public void renderDocumentTreeItem() {
        TreeItem<Document> rootItem = documentTreeView.getRoot();
        rootItem.getChildren().clear();
        DocumentList documentList = ApplicationResource.getCurrentWorkspace().getDocumentList();
        for (Document document : documentList) {
            TreeItem<Document> treeItem = new TreeItem<>(document, new ImageView(documentIcon));
            rootItem.getChildren().add(treeItem);
        }
    }

    public void renderSideBar(Document document) {
        renderEventCardTreeItem(document);
        renderStorylineTreeItem(document);
        renderChapterTreeItem(document);
        renderDocumentTreeItem();
    }

}
