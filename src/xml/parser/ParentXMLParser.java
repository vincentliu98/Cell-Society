package xml.parser;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author jgp17 
 * @author Inchan Hwang
 */
public abstract class ParentXMLParser<T> {
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    // keep only one documentBuilder because it is expensive to make and can numCellChanged it before parsing
    private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
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
    public static final String SHAPE_CODE_TAG = "shapeCode";

    public static final String CELL_TAG = "cell";
    public static final String CELL_UNIQUE_ID_TAG = "uniqueID";
    public static final String CELL_NEIGHBORS_TAG = "neighbors";
    public static final String CELL_XPOS_TAG = "cx";
    public static final String CELL_YPOS_TAG = "cy";

    /**
     * Get the data contained in this XML file as an object
     */
    public abstract Simulator<T> getSimulator(File datafile);

    /**
     *
     * @param root
     * @param valTag
     * @return
     */
    public static CellGraph<Integer> getIntegerCellGraph(Element root, String valTag) {
        CellGraph<Integer> graph = new CellGraph<>();
        NodeList cells = root.getElementsByTagName(CELL_TAG);
        Map<Integer, Cell<Integer>> IDToCellMap = new HashMap<Integer, Cell<Integer>>();
        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
            Element curCell = (Element) cells.item(cIndex);
            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG);
            int val = getIntValue(curCell, valTag);
            int shapeCode = getIntValue(curCell, SHAPE_CODE_TAG);
            double shapeWidth = getDoubleValue(curCell, SHAPE_WIDTH_TAG);
            double shapeHeight = getDoubleValue(curCell, SHAPE_HEIGHT_TAG);
            double xPos = getDoubleValue(curCell, CELL_XPOS_TAG);
            double yPos = getDoubleValue(curCell, CELL_YPOS_TAG);
            IDToCellMap.put(uniqueID, new Cell<>(val, shapeCode, xPos, yPos, shapeWidth, shapeHeight));
        }
        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
            Element curCell = (Element) cells.item(cIndex);
            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG);
            ArrayList<Integer> neighborIDs = parseNeighbors(curCell, 0);
            List<Cell<Integer>> neighborList = new ArrayList<>();
            for (int n : neighborIDs)
                neighborList.add(IDToCellMap.get(n));
            graph.put(IDToCellMap.get(uniqueID), neighborList);
        }
        return graph;
    }

//    public static Map<String, NodeList> parseTagToEltListMap(Element root, List<String> tagList) {
//        Map<String, NodeList> tagsToEltLists = new HashMap<String, NodeList>();
//        for (String tag : tagList)
//            tagsToEltLists.put(tag, root.getElementsByTagName(tag));
//        return tagsToEltLists;
//    }

    /**
     *
     * @param root
     * @param cellIndex
     * @return
     */
    public static ArrayList<Integer> parseNeighbors(Element root, int cellIndex) {
        String neighborStr = getTextValueAtIndex(root, CELL_NEIGHBORS_TAG, cellIndex);
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
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    /**
     *
     * @param e
     * @param tagName
     * @param i
     * @return
     */
    public static String getTextValueAtIndex(Element e, String tagName, int i) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(i).getTextContent();
        } else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    /**
     *
     * @param e
     * @param tagName
     * @return
     */
    public static int getIntValue(Element e, String tagName) {
        String str = getTextValue(e, tagName).replaceAll("\\s","");
        return Integer.parseInt(str);
    }

    /**
     *
     * @param e
     * @param tagName
     * @param i
     * @return
     */
    public static int getIntValueAtIndex(Element e, String tagName, int i) {
        String str = getTextValueAtIndex(e, tagName, i).replaceAll("\\s","");
        return Integer.parseInt(str);
    }

    /**
     *
     * @param e
     * @param tagName
     * @return
     */
    public static double getDoubleValue(Element e, String tagName) {
        String str = getTextValue(e, tagName).replaceAll("\\s","");
        return Double.parseDouble(str);
    }

    /**
     *
     * @param e
     * @param tagName
     * @param i
     * @return
     */
    public static double getDoubleValueAtIndex(Element e, String tagName, int i) {
        String str = getTextValueAtIndex(e, tagName, i).replaceAll("\\s","");
        return Double.parseDouble(str);
    }

    /**
     * Get root element of an XML file
     *
     * @param xmlFile
     * @return
     */
    public static Element getRootElement(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        } catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    public static String peekModelName(File xmlFile) {
        return getTextValue(getRootElement(xmlFile), MODEL_ATTRIBUTE_STRING);
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
    public static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
