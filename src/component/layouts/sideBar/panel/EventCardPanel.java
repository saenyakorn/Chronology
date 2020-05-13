package component.layouts.sideBar.panel;

import component.base.cell.EventCardTreeCell;
import component.components.eventCard.EventCard;
import utils.SystemUtils;

public class EventCardPanel extends Panel<EventCard> {

    public EventCardPanel() {
        setHeader(SystemUtils.EVENTCARD_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new EventCardTreeCell());
    }

}
