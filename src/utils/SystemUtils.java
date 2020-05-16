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

public class SystemUtils {

    /* Application constants */
    public static final String APP_NAME = "Chronology";
    public static final int SIDEBAR_PREF_WIDTH = 200;
    public static final int SIDEBAR_PREF_HEIGHT = 180;
    public static final int DIALOG_PREF_WIDTH = 300;
    public static final int DIALOG_PREF_HEIGHT = 400;
    public static final int EVENT_CARD_PREF_WIDTH = 200;
    public static final double INTRO_DURATION = 650;
    public static final double TOOLTIP_SHOW_DELAY = 200;

    /* Side bar header constants */
    public static final String EVENT_CARD_DEMO_HEADER = "Event Cards";
    public static final String STORYLINE_DEMO_HEADER = "Storylines";
    public static final String CHAPTER_DEMO_HEADER = "Chapters";
    public static final String DOCUMENT_DEMO_HEADER = "Documents";

    /* context menu word constants */
    public static final String NEW_EVENT_CARD_TO = "Add Event Card to this";
    public static final String NEW_EVENT_CARD = "New Event Card";
    public static final String NEW_STORYLINE = "New Storyline";
    public static final String NEW_DOCUMENT = "New Document";
    public static final String NEW_CHAPTER = "New Chapter";
    public static final String EDIT_PROPERTY = "Properties...";
    public static final String EDIT_TITLE = "Edit Title";
    public static final String EDIT_DESCRIPTION = "Edit Description";
    public static final String EDIT_DATA_TIME = "Edit Date/Time";
    public static final String EDIT_COLOR = "Edit Color";
    public static final String MOVE_TO_STORYLINE = "Move to Storyline...";
    public static final String MOVE_TO_CHAPTER = "Move to Chapter...";
    public static final String GET_INFO = "Get Info";
    public static final String REMOVE = "Remove";

    /* Alert text */
    public static final String CONFIRM_REMOVE_TITLE = "remove";
    public static final String CONFIRM_REMOVE_HEADER = "remove";
    public static final String CONFIRM_REMOVE_CONTENT = "remove";

    /* Constructor default values */
    public static final String DEFAULT_TITLE = "Title";
    public static final String DEFAULT_DESCRIPTION = "Lorem ipsum dolor set amet, ego bir setaso de.";
    public static final TimePeriod DEFAULT_TIME_PERIOD = TimePeriodGenerator.getTimePeriodFromPeriod(LocalDate.EPOCH, PredefinedTimePeriod.MIDDAY);
    public static final String DEFAULT_DOCUMENT_TITLE = "New Document";

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
