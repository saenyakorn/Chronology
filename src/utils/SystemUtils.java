package utils;

import javafx.scene.Parent;
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
    public static final int DIALOG_PREF_WIDTH = 300;
    public static final int DIALOG_PREF_HEIGHT = 400;
    public static final int EVENT_CARD_PREF_WIDTH = 200;

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

    public static void loadStyleSheet(Parent parent, String path) {
        parent.getStylesheets().add(parent.getClass().getResource(path).toExternalForm());
    }
}
