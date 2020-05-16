package component.layouts.sideBar.panel;

import component.base.cell.EventCardTreeCell;
import component.components.eventCard.EventCard;
import component.dialog.initialize.NewEventCardDialog;
import utils.SystemUtils;

/**
 * An instance of panel that contains a event card TreeView.
 */
public class EventCardPanel extends Panel<EventCard> {

    /**
     * Constructor for EventCardPanel. Sets the header text and cell factory.
     */
    public EventCardPanel() {
        setHeader(SystemUtils.EVENT_CARD_PANEL_HEADER);
        getTreeView().setCellFactory(param -> new EventCardTreeCell());
    }

    /**
     * Shows a NewEventCardDialog when button is clicked.
     * @see NewEventCardDialog
     */
    @Override
    public void onButtonClick() {
        new NewEventCardDialog().show();
    }

}
