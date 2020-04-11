package component.layouts.sideBar;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class SideBar extends ScrollPane {

    private TreeView<BasicStoryComponent> treeView;

    public SideBar() {
        treeView = new TreeView<>();
        treeView.setShowRoot(false);
        this.setPrefWidth(SystemConstants.SIDEBAR_PREF_WIDTH);
        VBox vBox = new VBox();
        vBox.getChildren().add(treeView);
        this.setContent(treeView);
    }

    private BasicStoryComponent createBasicStoryComponentFromDocument(Document document) {
        return new BasicStoryComponent(document.getText()) {
            @Override
            public String toString() {
                return title;
            }

            @Override
            protected void loadFXML() {
            }
        };
    }

    public void setActiveDocument(Document document) {
        System.out.println("SETTING ACTIVE DOCUMENT");
        EventCardList eventCardList = document.getEventCardList();
        ChapterList chapterList = document.getChapterList();
        BasicStoryComponent documentData = createBasicStoryComponentFromDocument(document);
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>(documentData);
        createTreeItemFromNonChapterEventCard(rootItem, eventCardList);
        createTreeItemFromChapterList(rootItem, chapterList);
        treeView.setRoot(rootItem);
        treeView.setCellFactory(params -> new TextFieldTreeCellForBasicStoryComponent());
    }

    private void createTreeItemFromNonChapterEventCard(TreeItem<BasicStoryComponent> rootItem, EventCardList eventCardList) {
        for (EventCard eventCard : eventCardList) {
            Chapter currentChapter = eventCard.getChapter();
            if (currentChapter == null) {
                TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(eventCard);
                rootItem.getChildren().add(treeItem);
            }
        }
    }

    private void createTreeItemFromChapterList(TreeItem<BasicStoryComponent> rootItem, ChapterList chapterList) {
        for (Chapter chapter : chapterList) {
            rootItem.getChildren().add(createTreeItemFromChapter(chapter));
        }
    }

    private TreeItem<BasicStoryComponent> createTreeItemFromChapter(Chapter chapter) {
        EventCardList eventCards = chapter.getEventCards();
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>(chapter);
        for (EventCard eventCard : eventCards) {
            TreeItem<BasicStoryComponent> item = new TreeItem<>(eventCard);
            rootItem.getChildren().add(item);
        }
        return rootItem;
    }
}
