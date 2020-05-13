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
    public static final int SIDEBAR_PREF_HEIGHT = 200;
    public static final int DIALOG_PREF_WIDTH = 300;
    public static final int DIALOG_PREF_HEIGHT = 400;
    public static final int EVENT_CARD_PREF_WIDTH = 200;
    public static final double INTRO_DURATION = 650;

    /* Side bar header constants */
    public static final String EVENTCARD_DEMO_HEADER = "Event Cards";
    public static final String STORYLINE_DEMO_HEADER = "Storylines";
    public static final String CHAPTER_DEMO_HEADER = "Chapters";
    public static final String DOCUMENT_DEMO_HEADER = "Documents";

    /* context menu word constants */
    public static final String NEW_EVENT_CARD = "New event card";
    public static final String NEW_STORYLINE = "New storyline";
    public static final String NEW_DOCUMENT = "New document";
    public static final String NEW_CHAPTER = "New chapter";
    public static final String EDIT_DATA_TIME = "Edit date/time";
    public static final String EDIT_COLOR = "Edit color";


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
