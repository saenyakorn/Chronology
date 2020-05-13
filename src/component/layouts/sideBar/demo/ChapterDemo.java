package component.layouts.sideBar.demo;

import component.base.cell.ChapterTreeCell;
import component.components.chapter.Chapter;
import utils.SystemUtils;

public class ChapterDemo extends Demo<Chapter> {

    public ChapterDemo() {
        setHeader(SystemUtils.CHAPTER_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new ChapterTreeCell());
    }
}
