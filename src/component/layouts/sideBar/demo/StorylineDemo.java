package component.layouts.sideBar.demo;

import component.base.cell.StorylineTreeCell;
import component.components.storyline.Storyline;
import utils.SystemUtils;

public class StorylineDemo extends Demo<Storyline> {

    public StorylineDemo() {
        setHeader(SystemUtils.STORYLINE_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new StorylineTreeCell());
    }
}
