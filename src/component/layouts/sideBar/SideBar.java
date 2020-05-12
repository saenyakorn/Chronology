package component.layouts.sideBar;

import component.base.BasicStoryComponent;
import component.base.BlankBasicStoryComponent;
import component.base.cell.BasicStoryComponentTreeCell;
import component.base.cell.DocumentTreeCell;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.document.Document;
import component.components.document.DocumentList;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class SideBar extends ScrollPane {

    // class files
    private final TreeView<Document> documentTreeView;
    private final TreeView<BasicStoryComponent> basicStoryComponentTreeView;

    // Icon for each item
    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");
    private final SVGPath storylineIcon = SystemUtils.getIconSVG("storyline_icon_24px.svg");
    private final SVGPath chapterIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");
    private final SVGPath documentIcon = SystemUtils.getIconSVG("check_icon_24px.svg");

    public SideBar() {
        // load css file
        getStylesheets().add(getClass().getResource("SideBar.css").toExternalForm());
        getStyleClass().add("body");

        // basicStoryComponentTreeView setting
        Label componentSectionHeading = new Label("Story Components");
        componentSectionHeading.getStyleClass().add("heading");
        basicStoryComponentTreeView = new TreeView<>();
        basicStoryComponentTreeView.setShowRoot(false);
        basicStoryComponentTreeView.setEditable(true);
        basicStoryComponentTreeView.getStyleClass().add("tree-view");
        basicStoryComponentTreeView.setCellFactory(params -> new BasicStoryComponentTreeCell());

        // documentTreeView setting
        Label documentSectionHeading = new Label("Documents");
        documentSectionHeading.getStyleClass().add("heading");
        documentTreeView = new TreeView<>();
        documentTreeView.setShowRoot(false);
        documentTreeView.setEditable(true);
        documentTreeView.getStyleClass().add("tree-view");
        documentTreeView.setCellFactory(params -> new DocumentTreeCell());

        // VBox container setting
        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.POSITIVE_INFINITY);
        vBox.getStyleClass().add("v-box");
        vBox.getChildren().addAll(componentSectionHeading, basicStoryComponentTreeView, documentSectionHeading, documentTreeView);

        // scroll pane setting
        getStyleClass().add("side-bar");
        setFitToWidth(true);
        setPrefWidth(SystemUtils.SIDEBAR_PREF_WIDTH);
        setContent(vBox);

        // initialize TreeView
        initializeBasicStoryComponentTreeView();
        initializeDocumentTreeView();

        //set icon styles
        eventCardIcon.getStyleClass().add("icon-24px");
        storylineIcon.getStyleClass().add("icon-24px");
        chapterIcon.getStyleClass().add("icon-24px");
        documentIcon.getStyleClass().add("icon-24px");
    }

    private void initializeBasicStoryComponentTreeView() {
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>();

        // Event Card Tree Cell Construct -> index 0
        TreeItem<BasicStoryComponent> eventCardTreeItemRoot = new TreeItem<>(new BlankBasicStoryComponent("Event Card", "None"), eventCardIcon);

        // Storyline Tree Cell Construct -> index 1
        TreeItem<BasicStoryComponent> storylineTreeItemRoot = new TreeItem<>(new BlankBasicStoryComponent("Storyline", "None"), storylineIcon);

        // Chapter Tree Cell Construct -> index 2
        TreeItem<BasicStoryComponent> chapterTreeItemRoot = new TreeItem<>(new BlankBasicStoryComponent("Chapter", "None"), chapterIcon);

        rootItem.getChildren().addAll(eventCardTreeItemRoot, storylineTreeItemRoot, chapterTreeItemRoot);
        basicStoryComponentTreeView.setRoot(rootItem);
    }

    private void initializeDocumentTreeView() {
        TreeItem<Document> rootItem = new TreeItem<>();
        documentTreeView.setRoot(rootItem);
    }

    public void renderEventCardTreeItem(Document activeDocument) {
        EventCardList eventCardList = activeDocument.getEventCards();
        TreeItem<BasicStoryComponent> eventCardTreeItemRoot = basicStoryComponentTreeView.getRoot().getChildren().get(0);
        eventCardTreeItemRoot.getChildren().clear();
        for (EventCard eventCard : eventCardList) {
            if (eventCard.getStoryline() == null && eventCard.getChapter() == null) {
                TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(eventCard, eventCardIcon);
                eventCardTreeItemRoot.getChildren().add(treeItem);
            }
        }
    }

    public void renderStorylineTreeItem(Document activeDocument) {
        EventCardList eventCardList = activeDocument.getEventCards();
        StorylineList storylineList = activeDocument.getStorylines();
        TreeItem<BasicStoryComponent> storylineTreeItemRoot = basicStoryComponentTreeView.getRoot().getChildren().get(1);
        storylineTreeItemRoot.getChildren().clear();
        for (Storyline storyline : storylineList) {
            TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(storyline, storylineIcon);
            for (EventCard eventCard : eventCardList) {
                if (eventCard.getStoryline() == storyline) {
                    TreeItem<BasicStoryComponent> childTreeItem = new TreeItem<>(eventCard, eventCardIcon);
                    treeItem.getChildren().add(childTreeItem);
                }
            }
            storylineTreeItemRoot.getChildren().add(treeItem);
        }
    }

    public void renderChapterTreeItem(Document activeDocument) {
        EventCardList eventCardList = activeDocument.getEventCards();
        ChapterList chapterList = activeDocument.getChapters();
        TreeItem<BasicStoryComponent> chapterTreeItemRoot = basicStoryComponentTreeView.getRoot().getChildren().get(2);
        chapterTreeItemRoot.getChildren().clear();
        for (Chapter chapter : chapterList) {
            TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(chapter, chapterIcon);
            for (EventCard eventCard : eventCardList) {
                if (eventCard.getChapter() == chapter) {
                    TreeItem<BasicStoryComponent> childTreeItem = new TreeItem<>(eventCard, eventCardIcon);
                    treeItem.getChildren().add(childTreeItem);
                }
            }
            chapterTreeItemRoot.getChildren().add(treeItem);
        }
    }

    public void renderDocumentTreeItem() {
        TreeItem<Document> rootItem = documentTreeView.getRoot();
        rootItem.getChildren().clear();
        DocumentList documentList = ApplicationUtils.getCurrentWorkspace().getDocumentList();
        for (Document document : documentList) {
            TreeItem<Document> treeItem = new TreeItem<>(document, documentIcon);
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
