package xml;

import javafx.util.Pair;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.GameOfLifeModel;

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
 */
public class XMLParser<T> {
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    
    /**
     * Create a parser for XML files of given type.
     */
    public XMLParser (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
     * Get the data contained in this XML file as an object
     */
    public SimulationData getSimulationModel(File dataFile) {
        var root = getRootElement(dataFile);
        if (! isValidFile(root, SimulationData.DATA_TYPE)) {
            throw new XMLException(ERROR_MESSAGE, SimulationData.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        var results = new HashMap<String, Object>();
        for (var field : SimulationData.DATA_FIELDS) {
            if (field.equals(SimulationData.DATA_FIELDS.get(1))) {
                ArrayList<ArrayList<Object>> cellArrayList = makeCellArrayList(root);
                results.put(field, cellArrayList);
            }
            else {
                results.put(field, getTextValue(root, field));
            }
            System.out.print(results.get(field).getClass());
        }
        SimulationData sim = new SimulationData(results);
        return sim;
    }

    private CellGraph makeCellGraph(Element root) {
        CellGraph graph = new CellGraph<Integer>();
        int numCells = root.getElementsByTagName("cell").getLength();
        for (int c = 0; c<numCells; c++) {
            ArrayList<Object> attrArrayList = new ArrayList<Object>();
            for (String sub : SimulationData.CELL_SUBFIELDS) {
                if (sub.equals(SimulationData.CELL_SUBFIELDS.get(1))) {
                    attrArrayList.add(parseNeighbors(root, c));
                }
                else if (sub.equals(SimulationData.CELL_SUBFIELDS.get(4))) {
                    attrArrayList.add(getValuesArrayList(root, c));
                }
                else {
                    String attrStr = getTextValueAtIndex(root, sub, c);
                    attrArrayList.add(Integer.parseInt(attrStr.replaceAll("\\s", "")));
                }
            }
//            cellArrayList.add(attrArrayList);
        }
        return graph;
    }

    private ArrayList<ArrayList<Object>> makeCellArrayList(Element root) {
        ArrayList<ArrayList<Object>> cellArrayList = new ArrayList<ArrayList<Object>>();
        int numCells = root.getElementsByTagName("cell").getLength();
        for (int c = 0; c<numCells; c++) {
            ArrayList<Object> attrArrayList = new ArrayList<Object>();
            for (String sub : SimulationData.CELL_SUBFIELDS) {
                if (sub.equals(SimulationData.CELL_SUBFIELDS.get(1))) {
                    attrArrayList.add(parseNeighbors(root, c));
                }
                else if (sub.equals(SimulationData.CELL_SUBFIELDS.get(4))) {
                    attrArrayList.add(getValuesArrayList(root, c));
                }
                else {
                    String attrStr = getTextValueAtIndex(root, sub, c);
                    attrArrayList.add(Integer.parseInt(attrStr.replaceAll("\\s", "")));
                }
            }
            cellArrayList.add(attrArrayList);
        }
        return cellArrayList;
    }

    private ArrayList<Integer> parseNeighbors(Element root, int cellIndex) {
        String neighborStr = getTextValueAtIndex(root, SimulationData.CELL_SUBFIELDS.get(1), cellIndex);
        ArrayList<Integer> neighborArrayList = new ArrayList<Integer>();
        String[] neighborStrArray = neighborStr.replace("\\s","").split(",");
        for (String s : neighborStrArray)
            neighborArrayList.add(Integer.parseInt(s));
        return neighborArrayList;
    }

    private ArrayList<Integer> getValuesArrayList(Element root, int cellIndex) {
        ArrayList<Integer> valuesArrayList = new ArrayList<Integer>();
        for (String sub : SimulationData.VALUE_SUBFIELDS) {
                String attrStr = getTextValueAtIndex(root, sub, cellIndex);
                valuesArrayList.add(Integer.parseInt(attrStr.replaceAll("\\s", "")));
        }
        return valuesArrayList;
    }

    // Get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // Returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // Get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // Get value of Element's text
    private String getTextValue (Element e, String tagName) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    private String getTextValueAtIndex (Element e, String tagName, int i) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(i).getTextContent();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    // Boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }

    public static void main(String[] args) {
        File test = new  File("data\\Game_of_Life_2.xml");
        XMLParser myParser = new XMLParser("sim");
        SimulationData mySimulationData = myParser.getSimulationModel(test);
        ArrayList<ArrayList<Integer>> cells = mySimulationData.getMyCellArrayList();
        for (ArrayList<Integer> attrList : cells) {
            for (int attr : attrList) {
                System.out.printf("%d ", attr);
            }
            System.out.printf("%s%n","");
        }
    }
}
