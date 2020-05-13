package component.layouts.sideBar.demo;

import component.base.cell.EventCardTreeCell;
import component.components.eventCard.EventCard;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class EventCardDemo extends Demo<EventCard> {

    private final SVGPath eventCardIcon = SystemUtils.getIconSVG("event_card_icon_24px.svg");

    public EventCardDemo() {
        setIcon(eventCardIcon);
        setHeader(SystemUtils.EVENTCARD_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new EventCardTreeCell());
    }

}
