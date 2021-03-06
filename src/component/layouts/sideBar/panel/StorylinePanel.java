package component.layouts.sideBar.panel;

import component.base.BasicStoryComponent;
import component.base.cell.StorylineTreeCell;
import component.components.eventCard.EventCard;
import component.components.eventCard.EventCardList;
import component.components.storyline.Storyline;
import component.dialog.initialize.NewStorylineDialog;
import exception.TypeNotMatchException;
import javafx.scene.control.TreeItem;
import utils.ApplicationUtils;
import utils.SystemUtils;

/**
 * An instance of panel that contains a document TreeView.
 */
public class StorylinePanel extends Panel<BasicStoryComponent> {

    /**
     * Constructor for DocumentPanel. Sets the header text and cell factory.
     */
    public StorylinePanel() {
        setHeader(SystemUtils.STORYLINE_PANEL_HEADER);
        getTreeView().setCellFactory(param -> new StorylineTreeCell());
    }

    /**
     * Shows a NewStorylineDialog when button is clicked.
     * @see NewStorylineDialog
     */
    @Override
    public void onButtonClick() {
        new NewStorylineDialog().show();
    }

    /**
     * Adds a storyline to this panel's TreeView, and renders all event cards in the storyline as leaf nodes.
     * @param item the item, preferably a storyline, to be added.
     */
    @Override
    public void addItem(BasicStoryComponent item) {
        EventCardList eventCards = ApplicationUtils.getCurrentWorkspace().getActiveDocument().getEventCards();
        TreeItem<BasicStoryComponent> treeItem = new TreeItem<>(item);
        try {
            if (item instanceof Storyline) {
                Storyline storyline = (Storyline) item;
                for (EventCard eventCard : eventCards) {
                    if (eventCard.getStoryline() == storyline) {
                        TreeItem<BasicStoryComponent> treeChild = new TreeItem<>(eventCard);
                        treeItem.getChildren().add(treeChild);
                    }
                }
                treeView.getRoot().getChildren().add(treeItem);
            } else {
                throw new TypeNotMatchException("Item should be Storyline");
            }
        } catch (TypeNotMatchException e) {
            e.printStackTrace();
        }
    }
}
