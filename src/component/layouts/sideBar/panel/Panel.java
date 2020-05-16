package component.layouts.sideBar.panel;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

public abstract class Panel<T> extends VBox {

    protected Label header = new Label();
    protected Button newItemButton = new Button();
    protected TreeView<T> treeView = new TreeView<>();
    protected ContextMenu contextMenu = new ContextMenu();
    private final ListChangeListener<T> listener = (ListChangeListener.Change<? extends T> change) -> onChange(change);

    public Panel() {
        getStylesheets().add(getClass().getResource("../SideBar.css").toExternalForm());
        header.getStyleClass().add("heading");
        contextMenu.setAutoHide(true);
        contextMenu.setHideOnEscape(true);
        treeView.setRoot(new TreeItem<>());
        treeView.setShowRoot(false);

        SVGPath newItemButtonIcon = SystemUtils.getIconSVG("plus_icon_24px.svg");
        newItemButtonIcon.getStyleClass().add("icon");
        newItemButton.setGraphic(newItemButtonIcon);
        newItemButton.getStyleClass().add("new-item-button");
        newItemButton.setPrefHeight(header.getPrefHeight());
        newItemButton.setPrefWidth(header.getPrefHeight());
        newItemButton.setOnAction((ActionEvent event) -> onButtonClick());

        HBox hBox = new HBox();
        hBox.getStyleClass().add("header-container");
        hBox.getChildren().addAll(header, newItemButton);
        getChildren().addAll(hBox, treeView);
        setMaxHeight(SystemUtils.SIDEBAR_PREF_HEIGHT);
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

    public Button getButton() {
        return newItemButton;
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

    public void onChange(ListChangeListener.Change<? extends T> change) {
        ObservableList<T> list = (ObservableList<T>) change.getList();
        setAllItem(list);
    }

    public abstract void onButtonClick();

}
