package component.layouts.sideBar;

import component.components.document.Document;
import component.layouts.sideBar.demo.ChapterDemo;
import component.layouts.sideBar.demo.DocumentDemo;
import component.layouts.sideBar.demo.EventCardDemo;
import component.layouts.sideBar.demo.StorylineDemo;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class SideBar extends ScrollPane {

    private final DocumentDemo documentDemo = new DocumentDemo();
    private final EventCardDemo eventCardDemo = new EventCardDemo();
    private final ChapterDemo chapterDemo = new ChapterDemo();
    private final StorylineDemo storylineDemo = new StorylineDemo();

    public SideBar() {
        // load css file
        getStylesheets().add(getClass().getResource("SideBar.css").toExternalForm());
        getStyleClass().add("body");

        // VBox container setting
        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.POSITIVE_INFINITY);
        vBox.getStyleClass().add("v-box");
        vBox.getChildren().addAll(documentDemo, eventCardDemo, chapterDemo, storylineDemo);

        // scroll pane setting
        getStyleClass().add("side-bar");
        setFitToWidth(true);
        setPrefWidth(SystemUtils.SIDEBAR_PREF_WIDTH);
        setContent(vBox);
    }

    public void initBindings(Document activeDocument) {
        documentDemo.binding(ApplicationUtils.getCurrentWorkspace().getDocumentList().listProperty());
        eventCardDemo.binding(activeDocument.getEventCards().getEventCards());
        storylineDemo.binding(activeDocument.getStorylines().getStorylines());
        chapterDemo.binding(activeDocument.getChapters().getChapters());
    }

    public void clear(Document document) {
        documentDemo.unbinding(ApplicationUtils.getCurrentWorkspace().getDocumentList().listProperty());
        eventCardDemo.unbinding(document.getEventCards().getEventCards());
        storylineDemo.unbinding(document.getStorylines().getStorylines());
        chapterDemo.unbinding(document.getChapters().getChapters());
    }

}
