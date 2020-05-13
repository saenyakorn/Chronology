package component.layouts.sideBar.panel;

import component.base.cell.StorylineTreeCell;
import component.components.storyline.Storyline;
import utils.SystemUtils;

public class StorylinePanel extends Panel<Storyline> {

    public StorylinePanel() {
        setHeader(SystemUtils.STORYLINE_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new StorylineTreeCell());
    }
}
