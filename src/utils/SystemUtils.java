package utils;

import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.shape.SVGPath;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Provides system constants and utility functions for the system.
 */
public class SystemUtils {

    /* Application constants */
    /**
     * Name of app.
     */
    public static final String APP_NAME = "Chronology";
    /**
     * Preferred width of sidebar.
     */
    public static final int SIDEBAR_PREF_WIDTH = 200;
    /**
     * Preferred height of each panel in sidebar.
     */
    public static final int SIDEBAR_PREF_HEIGHT = 180;
    /**
     * Preferred width of a dialog.
     */
    public static final int DIALOG_PREF_WIDTH = 300;
    /**
     * Preferred height of a dialog.
     */
    public static final int DIALOG_PREF_HEIGHT = 400;
    /**
     * Preferred width of an event card.
     */
    public static final int EVENT_CARD_PREF_WIDTH = 200;
    /**
     * Duration of intro screen.
     */
    public static final double INTRO_DURATION = 650;
    /**
     * The delay between hover and tooltip display.
     */
    public static final double TOOLTIP_SHOW_DELAY = 200;

    /* Side bar header constants */
    /**
     * Event card panel header text.
     */
    public static final String EVENT_CARD_PANEL_HEADER = "Event Cards";
    /**
     * Storyline panel header text.
     */
    public static final String STORYLINE_PANEL_HEADER = "Storylines";
    /**
     * Chapter panel header text.
     */
    public static final String CHAPTER_PANEL_HEADER = "Chapters";
    /**
     * Document panel header text.
     */
    public static final String DOCUMENT_PANEL_HEADER = "Documents";

    /* context menu word constants */
    /**
     * Context menu text for <i>Add Event Card to this</i> option.
     */
    public static final String NEW_EVENT_CARD_TO = "Add Event Card to this";
    /**
     * Context menu text for <i>New Event Card</i> option.
     */
    public static final String NEW_EVENT_CARD = "New Event Card";
    /**
     * Context menu text for <i>New Storyline</i> option.
     */
    public static final String NEW_STORYLINE = "New Storyline";
    /**
     * Context menu text for <i>New Document</i> option.
     */
    public static final String NEW_DOCUMENT = "New Document";
    /**
     * Context menu text for <i>New Project</i> option.
     */
    public static final String NEW_PROJECT = "New Project";
    /**
     * Context menu text for <i>New Chapter</i> option.
     */
    public static final String NEW_CHAPTER = "New Chapter";
    /**
     * Context menu text for <i>Properties</i> option.
     */
    public static final String EDIT_PROPERTY = "Properties...";
    /**
     * Context menu text for <i>Edit Name</i> option.
     */
    public static final String EDIT_NAME = "Edit Name";
    /**
     * Context menu text for <i>Edit Title</i> option.
     */
    public static final String EDIT_TITLE = "Edit Title";
    /**
     * Context menu text for <i>Edit Description</i> option.
     */
    public static final String EDIT_DESCRIPTION = "Edit Description";
    /**
     * Context menu text for <i>Edit Date/Time</i> option.
     */
    public static final String EDIT_DATA_TIME = "Edit Date/Time";
    /**
     * Context menu text for <i>Edit Color</i> option.
     */
    public static final String EDIT_COLOR = "Edit Color";
    /**
     * Context menu text for <i>Move to Storyline</i> option.
     */
    public static final String MOVE_TO_STORYLINE = "Move to Storyline...";
    /**
     * Context menu text for <i>Move to Chapter</i> option.
     */
    public static final String MOVE_TO_CHAPTER = "Move to Chapter...";
    /**
     * Context menu text for <i>Get Info</i> option.
     */
    public static final String GET_INFO = "Get Info";
    /**
     * Context menu text for <i>Remove</i> option.
     */
    public static final String REMOVE = "Remove";

    /* Alert text */
    /**
     * Title text for remove alert.
     */
    public static final String CONFIRM_REMOVE_TITLE = "Remove";
    /**
     * Header text for remove alert.
     */
    public static final String CONFIRM_REMOVE_HEADER = "Remove Item";
    /**
     * Content text for remove alert.
     */
    public static final String CONFIRM_REMOVE_CONTENT = "This cannot be undo. Are you sure you want to remove this?";
    /**
     * Title text for saved alert.
     */
    public static final String SAVED_TITLE = "Saved";
    /**
     * Header text for saved alert.
     */
    public static final String SAVED_HEADER = "Saved!";
    /**
     * Content text for saved alert.
     */
    public static final String SAVED_CONTENT = "Your file has been saved to ";

    /* Constructor default values */
    /**
     * Default component title.
     */
    public static final String DEFAULT_TITLE = "Title";
    /**
     * Default component description.
     */
    public static final String DEFAULT_DESCRIPTION = "Lorem ipsum dolor set amet, ego bir setaso de.";
    /**
     * Default document title.
     */
    public static final String DEFAULT_DOCUMENT_TITLE = "New Document";

    /**
     * Converts .svg file to SVGPath object.
     * @param path path to .svg resource
     * @return an SVGPath with same image as the specified .svg
     * @see SVGPath
     */
    public static SVGPath getIconSVG(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SVGPath svgPath = new SVGPath();
        path = ClassLoader.getSystemResource("icon/" + path).toExternalForm();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(path);
            XPathExpression expression = XPathFactory.newInstance().newXPath().compile("//path/@d");
            String svgPathString = (String) expression.evaluate(document, XPathConstants.STRING);
            svgPath.setContent(svgPathString);
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return svgPath;
    }

    /**
     * Gets Alert dialog type confirmation with custom style
     *
     * @param title   the dialog's title
     * @param header  the dialog's header
     * @param content the dialog's content
     * @return a custom confirmation alert dialog
     */
    public static Alert getCustomConfirmationAlert(String title, String header, String content) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(header);
        confirm.setContentText(content);
        confirm.setGraphic(null);
        DialogPane dialogPane = confirm.getDialogPane();
        dialogPane.getStylesheets().add(ClassLoader.getSystemResource("component/dialog/Dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("my-dialog");
        return confirm;
    }

    /**
     * Gets Alert dialog type information with custom style
     *
     * @param title   the dialog's title
     * @param header  the dialog's header
     * @param content the dialog's content
     * @return a custom confirmation alert dialog
     */
    public static Alert getCustomInformationAlert(String title, String header, String content) {
        Alert information = new Alert(Alert.AlertType.INFORMATION);
        information.setTitle(title);
        information.setHeaderText(header);
        information.setContentText(content);
        information.setGraphic(null);
        DialogPane dialogPane = information.getDialogPane();
        dialogPane.getStylesheets().add(ClassLoader.getSystemResource("component/dialog/Dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("my-dialog");
        return information;
    }
}
