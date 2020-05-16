package component.layouts.sideBar;

import component.components.document.Document;
import component.layouts.sideBar.panel.ChapterPanel;
import component.layouts.sideBar.panel.DocumentPanel;
import component.layouts.sideBar.panel.EventCardPanel;
import component.layouts.sideBar.panel.StorylinePanel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import utils.ApplicationUtils;
import utils.SystemUtils;

public class SideBar extends ScrollPane {

    private final DocumentPanel documentPanel = new DocumentPanel();
    private final EventCardPanel eventCardPanel = new EventCardPanel();
    private final ChapterPanel chapterPanel = new ChapterPanel();
    private final StorylinePanel storylinePanel = new StorylinePanel();

    public SideBar() {
        // load css file
        getStylesheets().add(getClass().getResource("SideBar.css").toExternalForm());
        getStyleClass().add("body");

        // VBox container setting
        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.POSITIVE_INFINITY);
        vBox.getStyleClass().add("v-box");
        vBox.getChildren().addAll(documentPanel, eventCardPanel, chapterPanel, storylinePanel);

        // scroll pane setting
        getStyleClass().add("side-bar");
        setFitToWidth(true);
        setPrefWidth(SystemUtils.SIDEBAR_PREF_WIDTH);
        setContent(vBox);
    }

    public void initBindings(Document activeDocument) {
        documentPanel.binding(ApplicationUtils.getCurrentWorkspace().getDocumentList().listProperty());
        eventCardPanel.binding(activeDocument.getEventCards().getEventCards());
        storylinePanel.binding(activeDocument.getStorylines().getStorylines());
        chapterPanel.binding(activeDocument.getChapters().getChapters());
    }

    public void clear(Document document) {
        documentPanel.unbinding(ApplicationUtils.getCurrentWorkspace().getDocumentList().listProperty());
        eventCardPanel.unbinding(document.getEventCards().getEventCards());
        storylinePanel.unbinding(document.getStorylines().getStorylines());
        chapterPanel.unbinding(document.getChapters().getChapters());
    }

}
