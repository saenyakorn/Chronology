package component.components.sideBar;

import application.SystemConstants;
import javafx.scene.control.TreeView;

public class SideBar extends TreeView<Character> {

    public SideBar() {
        this.setPrefWidth(SystemConstants.SIDEBAR_PREF_WIDTH);
    }

    public void addDocument() {

    }
}
