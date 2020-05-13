package component.layouts.sideBar.demo;

import component.base.cell.ChapterTreeCell;
import component.components.chapter.Chapter;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public class ChapterDemo extends Demo<Chapter> {

    private final SVGPath chapterIcon = SystemUtils.getIconSVG("chapter_icon_24px.svg");

    public ChapterDemo() {
        setIcon(chapterIcon);
        setHeader(SystemUtils.CHAPTER_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new ChapterTreeCell());
    }
}
