package component.layouts.sideBar.demo;

import component.base.cell.EventCardTreeCell;
import component.components.eventCard.EventCard;
import utils.SystemUtils;

public class EventCardDemo extends Demo<EventCard> {

    public EventCardDemo() {
        setHeader(SystemUtils.EVENTCARD_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new EventCardTreeCell());
    }

}
