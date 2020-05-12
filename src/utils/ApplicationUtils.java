package utils;

import component.base.BasicStoryComponent;
import component.components.chapter.Chapter;
import component.components.document.Document;
import component.components.eventCard.EventCard;
import component.components.storyline.Storyline;
import component.layouts.workspace.Workspace;
import javafx.scene.Parent;
import javafx.scene.shape.SVGPath;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ApplicationUtils {

    private static final HashMap<String, BasicStoryComponent> hashMap = new HashMap<>(); //contains all basic story components
    private static File savedFile = null;
    private static Workspace currentWorkspace = null;

    public static void initialize() {
        savedFile = null;
        Workspace workspace = new Workspace();
        setCurrentWorkspace(workspace);
    }

    public static Workspace getCurrentWorkspace() {
        return ApplicationUtils.currentWorkspace;
    }

    public static File getSavedFile() {
        return savedFile;
    }

    public static void setSavedFile(File savedFile) {
        ApplicationUtils.savedFile = savedFile;
    }

    public static void update() {
        Document currentDocument = ApplicationUtils.getCurrentWorkspace().getActiveDocument();
        ApplicationUtils.getCurrentWorkspace().setActiveDocument(currentDocument);
    }

    public static void setCurrentWorkspace(Workspace currentWorkspace) {
        ApplicationUtils.currentWorkspace = currentWorkspace;
    }

    public static void putItemToCurrentHashMap(String key, BasicStoryComponent value) {
        ApplicationUtils.hashMap.put(key, value);
    }

    public static BasicStoryComponent getValueFromCurrentHashMap(String key) {
        return ApplicationUtils.hashMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static JSONObject getCurrentHashMapAsJSONObject() {
        JSONObject hashMapObject = new JSONObject();
        ApplicationUtils.hashMap.forEach((key, value) -> {
            if (value instanceof EventCard || value instanceof Storyline || value instanceof Chapter) {
                System.out.println("HashMap key: " + key + ", value = " + value.toString() + ", type = " + value.getClass().getName());
                hashMapObject.put(key, value.writeJSONObject());
            }
        });
        System.out.println();
        return hashMapObject;
    }

    public static void newProject() {
        // initialized application
        ApplicationUtils.initialize();

        // add template structure
        ApplicationUtils.getCurrentWorkspace().addDocument(new Document("New Document"));

        // render side bar
        ApplicationUtils.getCurrentWorkspace().getSideBar().renderSideBar(ApplicationUtils.getCurrentWorkspace().getActiveDocument());
    }

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

