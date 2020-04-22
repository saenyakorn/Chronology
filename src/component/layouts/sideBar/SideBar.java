package component.layouts.sideBar;

import application.SystemConstants;
import component.base.BasicStoryComponent;
import component.base.BasicStoryComponentTreeCell;
import component.base.OnlyBodyBasicStoryComponents;
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

    private final TreeView<BasicStoryComponent> treeView;

    public SideBar() {
        // load css file
        getStylesheets().add(getClass().getResource("SideBar.css").toExternalForm());

        // TreeView setting
        treeView = new TreeView<>();
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.getStyleClass().add("tree-view");
        treeView.setCellFactory(params -> new BasicStoryComponentTreeCell());

        // VBox container setting
        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.POSITIVE_INFINITY);
        vBox.getStyleClass().add("v-box");
        vBox.getChildren().add(treeView);

        // scroll pane setting
        this.getStyleClass().add("side-bar");
        this.setFitToWidth(true);
        this.setPrefWidth(SystemConstants.SIDEBAR_PREF_WIDTH);
        this.setContent(vBox);
    }

    public void renderSideBar(Document document) {
        EventCardList eventCardList = document.getEventCardList();
        ChapterList chapterList = document.getChapterList();
        StorylineList storylineList = document.getStorylineList();
        BasicStoryComponent documentData = new OnlyBodyBasicStoryComponents(document.getText(), "none");
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>(documentData);
        createTreeItemFromNonChapterEventCard(rootItem, eventCardList);
        createTreeItemFromChapterList(rootItem, chapterList);
        createTreeItemFromStorylineList(rootItem, storylineList);
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

    private void createTreeItemFromStorylineList(TreeItem<BasicStoryComponent> rootItem, StorylineList storylineList) {
        for (Storyline storyline : storylineList) {
            rootItem.getChildren().add(createTreeItemFromStoryline(storyline));
        }
    }

    private TreeItem<BasicStoryComponent> createTreeItemFromStoryline(Storyline storyline) {
        EventCardList eventCards = storyline.getEventCards();
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>(storyline);
        for (EventCard eventCard : eventCards) {
            TreeItem<BasicStoryComponent> item = new TreeItem<>(eventCard);
            rootItem.getChildren().add(item);
        }
        return rootItem;
    }


}
