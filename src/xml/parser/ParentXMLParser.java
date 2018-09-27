package xml.parser;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;
import simulation.models.SegregationModel;
import simulation.models.SpreadingFireModel;
import simulation.models.WaTorModel;
import xml.XMLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author jgp17 
 * @author Inchan Hwang
 */
public class ParentXMLParser {
    public static final String DEFAULT_RESOURCES = "Errors";
    private static final String LOAD_AGAIN_KEY = "LoadAgainMsg";
    private static ResourceBundle myResources;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;
    // name of root attribute that notes the type of file expecting to parse
    public static final String MODEL_ATTRIBUTE_STRING = "modelName";
    public static final List<String> VALID_MODEL_NAMES = List.of(
            GameOfLifeModel.MODEL_NAME,
            SegregationModel.MODEL_NAME,
            SpreadingFireModel.MODEL_NAME,
            WaTorModel.MODEL_NAME
    );

    public static final String WHITESPACE = "\\s";
    public static final String SHAPE_WIDTH_TAG = "shapeWidth";
    public static final String SHAPE_HEIGHT_TAG = "shapeHeight";
    public static final String SHAPE_RADIUS_TAG = "shapeRadius";
    public static final String SHAPE_TAG = "shape";
    public static final String RECTANGLE_STRING = "rectangle";
    public static final String CIRCLE_STRING = "circle";

    public static final String CELL_TAG = "cell";
    public static final String CELL_UNIQUE_ID_TAG = "uniqueID";
    public static final String CELL_NEIGHBORS_TAG = "neighbors";
    public static final String CELL_XPOS_TAG = "cx";
    public static final String CELL_YPOS_TAG = "cy";

    /**
     * Create a parser for XML files of given type.
     */
    public ParentXMLParser(String language) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        // use resources for labels
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCES + language);
    }

    /**
     * Get the data contained in this XML file as an object
     */
    public Simulator getSimulator(File datafile) {
        Element root = getRootElement(datafile);
        String fileModelName = getTextValue(root, MODEL_ATTRIBUTE_STRING);
        switch (fileModelName) {
            case GameOfLifeModel.MODEL_NAME:
                return GameOfLifeXMLParser.getModelSimulator(root);
            case SegregationModel.MODEL_NAME:
                return SegregationXMLParser.getModelSimulator(root);
            case SpreadingFireModel.MODEL_NAME:
                return SpreadingFireXMLParser.getModelSimulator(root);
            case WaTorModel.MODEL_NAME:
                return WaTorXMLParser.getModelSimulator(root);
            default:
                throw new XMLException(myResources.getString("ModelNameErrorMsg"), fileModelName);
        }
    }

    /**
     *
     * @param root
     * @param valTag
     * @return
     */
    public static CellGraph<Integer> getIntegerCellGraph(Element root, String valTag) {
        CellGraph<Integer> graph;
        String shapeString = getTextValue(root, SHAPE_TAG).replaceAll("\\s","");
        if (shapeString.equals(RECTANGLE_STRING)) {
            graph = new CellGraph<Integer>(parseRectangle(root));
        } else if (shapeString.equals(CIRCLE_STRING)) {
            graph = new CellGraph<Integer>(parseCircle(root));
        } else {
            throw new XMLException(myResources.getString("ShapeErrorMsg")+ myResources.getString(LOAD_AGAIN_KEY), shapeString);
        }
        NodeList cells = root.getElementsByTagName(CELL_TAG);
        Map<Integer, Cell<Integer>> IDToCellMap = new HashMap<Integer, Cell<Integer>>();
        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
            Element curCell = (Element) cells.item(cIndex);
            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG);
            int val = getIntValue(curCell, valTag);
            double xPos = getDoubleValue(curCell, CELL_XPOS_TAG);
            double yPos = getDoubleValue(curCell, CELL_YPOS_TAG);
            IDToCellMap.put(uniqueID, new Cell<Integer>(val, xPos, yPos));
        }
        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
            Element curCell = (Element) cells.item(cIndex);
            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG);
            ArrayList<Integer> neighborIDs = parseNeighbors(curCell);
            List<Cell<Integer>> neighborList = new ArrayList<>();
            for (int n : neighborIDs)
                neighborList.add(IDToCellMap.get(n));
            graph.put(IDToCellMap.get(uniqueID), neighborList);
        }
        return graph;
    }

    /**
     *
     * @param root
     * @return
     */
    public static Rectangle parseRectangle(Element root) {
        double shapeWidth = getDoubleValue(root, SHAPE_WIDTH_TAG);
        double shapeHeight = getDoubleValue(root, SHAPE_HEIGHT_TAG);
        return new Rectangle(shapeWidth, shapeHeight);
    }

    /**
     *
     * @param root
     * @return
     */
    public static Circle parseCircle(Element root) {
        double shapeRadius = getDoubleValue(root, SHAPE_RADIUS_TAG);
        return new Circle(shapeRadius);
    }

    /**
     *
     * @param root
     * @return
     */
    public static ArrayList<Integer> parseNeighbors(Element root) {
        String neighborStr = getTextValue(root, CELL_NEIGHBORS_TAG);
        ArrayList<Integer> neighborArrayList = new ArrayList<Integer>();
        String[] neighborStrArray = neighborStr.replaceAll("\\s", "").split(",");
        for (String s : neighborStrArray)
            neighborArrayList.add(Integer.parseInt(s));
        return neighborArrayList;
    }

    /**
     * Get value of Element's attribute
     */
    public static String getAttribute(Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    /**
     * Get value of Element's text
     */
    public static String getTextValue(Element e, String tagName) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            throw new XMLException(myResources.getString("MissingTagMsg")+ myResources.getString(LOAD_AGAIN_KEY),
                    e.toString(), tagName);
        }
    }

    /**
     *
     * @param e
     * @param tagName
     * @return
     */
    public static int getIntValue(Element e, String tagName) {
        String str = getTextValue(e, tagName).replaceAll("\\s", "");
        try {

            return Integer.parseInt(str);
        }
        catch (NumberFormatException ex){
            throw new XMLException(myResources.getString("ValueNotIntMsg") + myResources.getString(LOAD_AGAIN_KEY),
                    tagName, str);
        }
    }

    /**
     *
     * @param e
     * @param tagName
     * @return
     */
    public static double getDoubleValue(Element e, String tagName) {
        String str = getTextValue(e, tagName).replaceAll("\\s", "");
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException ex){
            throw new XMLException(myResources.getString("ValueNotDoubleMsg") + myResources.getString(LOAD_AGAIN_KEY),
                    tagName, str);
        }
    }

    /**
     * Get root element of an XML file
     *
     * @param xmlFile
     * @return
     */
    public Element getRootElement(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        } catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    /**
     * Returns if this is a valid XML file for the specified object type
     *
     * @param root
     * @return
     */
    public static boolean isValidFile (Element root) {
        for (String typeAttr : VALID_MODEL_NAMES)
            if (getAttribute(root, MODEL_ATTRIBUTE_STRING).equals(typeAttr))
                return true;
        return false;
    }

    /**
     * Boilerplate code needed to make a documentBuilder
     */
    public DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
