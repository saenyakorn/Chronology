package component.layouts.sideBar;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.base.TextFieldTreeCell;
import component.components.chapter.Chapter;
import component.components.chapter.ChapterList;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.components.storyline.StorylineList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class SideBar extends ScrollPane {

    private TreeView<BasicStoryComponent> treeView;

    public SideBar() {
        // TreeView setting
        treeView = new TreeView<>();
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.setCellFactory(params -> new TextFieldTreeCell());
        this.setFitToWidth(true);
        this.setPrefWidth(SystemConstants.SIDEBAR_PREF_WIDTH);
        VBox vBox = new VBox();
        vBox.getChildren().add(treeView);
        this.setContent(treeView);
    }

    private BasicStoryComponent createBasicStoryComponentFromDocument(Document document) {
        return new BasicStoryComponent(document.getText(), document.getText()) {
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
        StorylineList storylineList = document.getStorylineList();
        BasicStoryComponent documentData = createBasicStoryComponentFromDocument(document);
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>(documentData);
        createTreeItemFromNonChapterEventCard(rootItem, eventCardList);
        createTreeItemFromChapterList(rootItem, chapterList);
        createtreeItemFromStoryListList(rootItem, storylineList);
        treeView.setRoot(rootItem);
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

    private void createtreeItemFromStoryListList(TreeItem<BasicStoryComponent> rootItem, StorylineList storylineList) {
        for (Storyline storyline : storylineList) {
            rootItem.getChildren().add(createTreeItemFromStoryLine(storyline));
        }
    }

    private TreeItem<BasicStoryComponent> createTreeItemFromStoryLine(Storyline storyline) {
        EventCardList eventCards = storyline.getEventCards();
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>(storyline);
        for (EventCard eventCard : eventCards) {
            TreeItem<BasicStoryComponent> item = new TreeItem<>(eventCard);
            rootItem.getChildren().add(item);
        }
        return rootItem;
    }


}
