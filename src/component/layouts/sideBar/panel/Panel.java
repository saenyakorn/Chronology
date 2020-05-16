package component.layouts.sideBar.panel;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import utils.SystemUtils;

/**
 * An area containing a TreeView of a specific type of component.
 * @param <T> the type of Object contained within the panel.
 * @see TreeView
 */
public abstract class Panel<T> extends VBox {

    /**
     * Header of panel area.
     */
    protected Label header = new Label();
    /**
     * Add component button.
     */
    protected Button newItemButton = new Button();
    /**
     * The TreeView contained in panel.
     */
    protected TreeView<T> treeView = new TreeView<>();
    /**
     * The context menu shown in panel area.
     */
    protected ContextMenu contextMenu = new ContextMenu();
    /**
     * Listener of this panel. Used to detect changes in lists bound to this panel.
     */
    private final ListChangeListener<T> listener = (ListChangeListener.Change<? extends T> change) -> onChange(change);

    /**
     * Constructor for a Panel. The process is as follows:
     * <ol>
     *     <li>Adds css styles.</li>
     *     <li>Setups context menu.</li>
     *     <li>Sets root of TreeView and configure root.</li>
     *     <li>Configures add component button.</li>
     *     <li>Initializes a header container, then adds header and button to it.</li>
     *     <li>Adds header container and TreeView to scene graph.</li>
     * </ol>
     */
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

    /**
     * Getter for header parameter.
     * @return this panel's header.
     */
    public Label getHeader() {
        return header;
    }

    /**
     * Setter for header text.
     * @param text text to set as header text.
     */
    public void setHeader(String text) {
        header.setText(text);
    }

    /**
     * Getter for TreeView parameter.
     * @return this panel's TreeView.
     */
    public TreeView<T> getTreeView() {
        return treeView;
    }

    /**
     * Getter for add component button.
     * @return this panel's button.
     */
    public Button getButton() {
        return newItemButton;
    }

    /**
     * Binds this panel to the ObservableList specified in the list parameter.
     * @param list the list this panel is to be bound to.
     */
    public void binding(ObservableList<? extends T> list) {
        list.addListener(listener);
        setAllItem(list);
    }

    /**
     * Unbinds this panel from the ObservableList specified in the list parameter.
     * @param list the list this panel is to be unbound from.
     */
    public void unbinding(ObservableList<? extends T> list) {
        treeView.getRoot().getChildren().clear();
        list.removeListener(listener);
    }

    /**
     * Adds an item to this panel's TreeView.
     * @param item the item to be added.
     */
    public void addItem(T item) {
        TreeItem<T> treeItem = new TreeItem<>(item);
        treeView.getRoot().getChildren().add(treeItem);
    }

    /**
     * Adds all items in an ObservableList to this panel's TreeView.
     * @param items the ObservableList whose items are to be added.
     */
    public void setAllItem(ObservableList<? extends T> items) {
        getTreeView().getRoot().getChildren().clear();
        for (T item : items) {
            addItem(item);
        }
    }

    /**
     * Resets all items in TreeView every time the list bound to this panel's TreeView changes.
     * @param change the listener listening to this change.
     */
    public void onChange(ListChangeListener.Change<? extends T> change) {
        setAllItem(change.getList());
    }

    /**
     * Defines the action to happen when add component button is clicked.
     */
    public abstract void onButtonClick();

}
