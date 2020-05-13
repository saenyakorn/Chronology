package component.layouts.sideBar.panel;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import utils.SystemUtils;

public abstract class Panel<T> extends VBox {

    protected Label header = new Label();
    protected TreeView<T> treeView = new TreeView<>();

    private ListChangeListener<T> listener = (ListChangeListener.Change<? extends T> change) -> {
        getTreeView().getRoot().getChildren().clear();
        for (T item : change.getList()) {
            addItem(item);
        }
    };


    public Panel() {
        getStylesheets().add(getClass().getResource("../SideBar.css").toExternalForm());
        treeView.setRoot(new TreeItem<>());
        treeView.setShowRoot(false);
        header.getStyleClass().add("heading");
        setMaxHeight(SystemUtils.SIDEBAR_PREF_HEIGHT);
        getChildren().addAll(header, treeView);
    }

    public Label getHeader() {
        return header;
    }

    public void setHeader(String text) {
        header.setText(text);
    }

    public TreeView<T> getTreeView() {
        return treeView;
    }

    public void binding(ObservableList<T> list) {
        list.addListener(listener);
        setAllItem(list);
    }

    public void unbinding(ObservableList<T> list) {
        treeView.getRoot().getChildren().clear();
        list.removeListener(listener);
    }

    public void addItem(T item) {
        TreeItem<T> treeItem = new TreeItem<>(item);
        treeView.getRoot().getChildren().add(treeItem);
    }

    public void setAllItem(ObservableList<T> items) {
        getTreeView().getRoot().getChildren().clear();
        for (T item : items) {
            addItem(item);
        }
    }
}