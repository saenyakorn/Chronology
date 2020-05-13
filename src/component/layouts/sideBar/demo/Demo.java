package component.layouts.sideBar.demo;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public abstract class Demo<T> extends VBox {

    protected Label header = new Label();
    protected TreeView<T> treeView = new TreeView<>();
    protected SVGPath icon = new SVGPath();

    ListChangeListener<T> listener = new ListChangeListener<T>() {
        @Override
        public void onChanged(Change<? extends T> change) {
        }
    };

    public Demo() {
        getStylesheets().add(getClass().getResource("../SideBar.css").toExternalForm());
        treeView.setRoot(new TreeItem<>());
        treeView.setShowRoot(false);
        icon.getStyleClass().add("icon-24px");
        header.getStyleClass().add("heading");
        setMaxHeight(200);
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

    public SVGPath getIcon() {
        return icon;
    }

    public void setIcon(SVGPath icon) {
        this.icon.setContent(icon.getContent());
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
        SVGPath cloneIcon = new SVGPath();
        cloneIcon.getStyleClass().add("icon-24px");
        cloneIcon.setContent(icon.getContent());
        TreeItem<T> treeItem = new TreeItem<>(item, cloneIcon);
        treeView.getRoot().getChildren().add(treeItem);
    }

    public void setAllItem(ObservableList<T> items) {
        getTreeView().getRoot().getChildren().clear();
        for (T item : items) {
            addItem(item);
        }
    }
}
