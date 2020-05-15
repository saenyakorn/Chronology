package component.layouts.sideBar.panel;

import component.base.cell.ChapterTreeCell;
import component.components.chapter.Chapter;
import component.dialog.initialize.NewChapterDialog;
import utils.SystemUtils;

public class ChapterPanel extends Panel<Chapter> {

    public ChapterPanel() {
        setHeader(SystemUtils.CHAPTER_DEMO_HEADER);
        getTreeView().setCellFactory(param -> new ChapterTreeCell());
    }

    @Override
    public void onButtonClick() {
        new NewChapterDialog().show();
    }
}
