package utils;

import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
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
    public static final String CONFIRM_REMOVE_TITLE = "remove";
    /**
     * Header text for remove alert.
     */
    public static final String CONFIRM_REMOVE_HEADER = "remove";
    /**
     * Content text for remove alert.
     */
    public static final String CONFIRM_REMOVE_CONTENT = "remove";

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
     * Default component timePeriod.
     */
    public static final TimePeriod DEFAULT_TIME_PERIOD = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
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
        path = "file:res/icon/" + path;
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
}
