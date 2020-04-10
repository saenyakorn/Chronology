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
        this.treeView = new TreeView<>();
        this.setPrefWidth(SystemConstants.SIDEBAR_PREF_WIDTH);
        VBox vbox = new VBox();
        vbox.getChildren().add(treeView);
        this.getChildren().add(vbox);
    }

    public void setTreeView(TreeView<BasicStoryComponent> treeView) {
        this.treeView = treeView;
    }

    public void setActiveDocument(Document document) {
        ChapterList chapters = document.getChapters();
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>();
        for (Chapter chapter : chapters) {
            TreeItem<BasicStoryComponent> item = createTreeItem(chapter);
            rootItem.getChildren().add(item);
        }
        TreeView<BasicStoryComponent> treeView = new TreeView<>(rootItem);
        treeView.setShowRoot(false);
        this.setTreeView(treeView);
    }

    private TreeItem<BasicStoryComponent> createTreeItem(Chapter chapter) {
        EventCardList eventCards = chapter.getEventCards();
        TreeItem<BasicStoryComponent> rootItem = new TreeItem<>();
        for (EventCard eventCard : eventCards) {
            TreeItem<BasicStoryComponent> item = new TreeItem<>(eventCard);
            rootItem.getChildren().add(item);
        }
        return rootItem;
    }
}
