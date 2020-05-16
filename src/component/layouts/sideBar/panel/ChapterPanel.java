package component.layouts.sideBar.panel;

import component.base.BasicStoryComponent;
import component.base.cell.ChapterTreeCell;
import component.components.chapter.Chapter;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.dialog.initialize.NewChapterDialog;
import exception.TypeNotMatchException;
import javafx.scene.control.TreeItem;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * An instance of panel that contains a document TreeView.
 */
public class ChapterPanel extends Panel<BasicStoryComponent> {

    /**
     * Constructor for DocumentPanel. Sets the header text and cell factory.
     */
    public ChapterPanel() {
        setHeader(SystemUtils.CHAPTER_PANEL_HEADER);
        getTreeView().setCellFactory(param -> new ChapterTreeCell());
    }

    /**
     * Shows a NewChapterDialog when button is clicked.
     * @see NewChapterDialog
     */
    @Override
    public void onButtonClick() {
        new NewChapterDialog().show();
    }

    /**
     * Adds a chapter to this panel's TreeView, and renders all event cards in the chapter as leaf nodes.
     * @param item the item, preferably a chapter, to be added.
     */
    @Override
    public void addItem(BasicStoryComponent item) {
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(item);
        try {
            if (item instanceof Chapter) {
                Chapter chapter = (Chapter) item;
                for (EventCard eventCard : eventCards) {
                    if (eventCard.getChapter() == chapter) {
                        TreeItem<BasicStoryComponent> treeChild = new TreeItem<>(eventCard);
                        treeItem.getChildren().add(treeChild);
                    }
                }
                treeView.getRoot().getChildren().add(treeItem);
            } else {
                throw new TypeNotMatchException("Item should be Chapter");
            }
        } catch (TypeNotMatchException e) {
            e.printStackTrace();
        }
    }
}
