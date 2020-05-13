package component.layouts.sideBar.demo;

import component.base.cell.StorylineTreeCell;
import component.components.storyline.Storyline;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class StorylineDemo extends Demo<Storyline> {

    private final SVGPath storylineIcon = SystemUtils.getIconSVG("storyline_icon_24px.svg");

    public StorylineDemo() {
        setIcon(storylineIcon);
        setHeader(SystemUtils.STORYLINE_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new StorylineTreeCell());
    }
}
