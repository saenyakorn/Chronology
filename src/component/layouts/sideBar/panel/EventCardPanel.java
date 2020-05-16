package component.layouts.sideBar.panel;

import component.base.cell.EventCardTreeCell;
import component.components.eventCard.EventCard;
import component.dialog.initialize.NewEventCardDialog;
import utils.SystemUtils;

public class EventCardPanel extends Panel<EventCard> {

    public EventCardPanel() {
        setHeader(SystemUtils.EVENT_CARD_PANEL_HEADER);
        getTreeView().setCellFactory(param -> new EventCardTreeCell());
    }

    @Override
    public void onButtonClick() {
        new NewEventCardDialog().show();
    }

}
