package utils;

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
    public static final String NEW_EVENT_CARD_TO = "add event card to this";
    public static final String NEW_EVENT_CARD = "new event card";
    public static final String NEW_STORYLINE = "new storyline";
    public static final String NEW_DOCUMENT = "new document";
    public static final String NEW_CHAPTER = "new chapter";
    public static final String EDIT_PROPERTY = "properties...";
    public static final String EDIT_TITLE = "edit title";
    public static final String EDIT_DESCRIPTION = "edit description";
    public static final String EDIT_DATA_TIME = "edit date/time";
    public static final String EDIT_COLOR = "edit color";
    public static final String MOVE_TO_STORYLINE = "move to storyline...";
    public static final String MOVE_TO_CHAPTER = "move to chapter...";
    public static final String GET_INFO = "get Info";
    public static final String REMOVE = "remove";

    /* Alert text */
    public static final String CONFIRM_REMOVE_TITLE = "remove";
    public static final String CONFIRM_REMOVE_HEADER = "remove";
    public static final String CONFIRM_REMOVE_CONTENT = "remove";


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
