package xml;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import simulation.Simulator;
import simulation.factory.Segregation;
import simulation.models.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class ParentXMLParser {
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    // name of root attribute that notes the type of file expecting to parse
    private final String MODEL_ATTRIBUTE_STRING = "sim";
    public static final List<String> VALID_MODEL_NAMES = List.of(
            GameOfLifeModel.MODEL_NAME,
            SegregationModel.MODEL_NAME,
            SpreadingFireModel.MODEL_NAME,
            WaTorModel.MODEL_NAME
    );
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    public static final List<String> SIMULATION_FIELDS = List.of(
            "shapeWidth",
            "shapeHeight",
            "modelName",
            "shape"
    );

    public static final List<String> STANDARD_CELL_FIELDS = List.of(
            "uniqueID",
            "neighbors",
            "cx",
            "cy"
    );




    /**
     * Create a parser for XML files of given type.
     */
    public ParentXMLParser() {
        DOCUMENT_BUILDER = getDocumentBuilder();
    }

    /**
     * Get the data contained in this XML file as an object
     */

    public Simulator getSimulator(File datafile) {
        Element root = getRootElement(datafile);
        if (getAttribute(root, MODEL_ATTRIBUTE_STRING).equals(GameOfLifeModel.MODEL_NAME))
            return GameOfLifeXMLParser.getModelSimulator(root);
        else if (getAttribute(root, MODEL_ATTRIBUTE_STRING).equals(SegregationModel.MODEL_NAME))
            return GameOfLifeXMLParser.getModelSimulator(root);
        else if (getAttribute(root, MODEL_ATTRIBUTE_STRING).equals(SpreadingFireModel.MODEL_NAME))
            return GameOfLifeXMLParser.getModelSimulator(root);
        else if (getAttribute(root, MODEL_ATTRIBUTE_STRING).equals(SpreadingFireModel.MODEL_NAME))
            return GameOfLifeXMLParser.getModelSimulator(root);
        else
            throw new XMLException(ERROR_MESSAGE, MODEL_ATTRIBUTE_STRING);
    }

    public ArrayList<Integer> parseNeighbors(Element root, int cellIndex) {
        String neighborStr = getTextValueAtIndex(root, SimulationData.CELL_SUBFIELDS.get(1), cellIndex);
        ArrayList<Integer> neighborArrayList = new ArrayList<Integer>();
        String[] neighborStrArray = neighborStr.replace("\\s", "").split(",");
        for (String s : neighborStrArray)
            neighborArrayList.add(Integer.parseInt(s));
        return neighborArrayList;
    }

    public ArrayList<Integer> getValuesArrayList(Element root, int cellIndex) {
        ArrayList<Integer> valuesArrayList = new ArrayList<Integer>();
        for (String sub : SimulationData.VALUE_SUBFIELDS) {
            String attrStr = getTextValueAtIndex(root, sub, cellIndex);
            valuesArrayList.add(Integer.parseInt(attrStr.replaceAll("\\s", "")));
        }
        return valuesArrayList;
    }

    // Get root element of an XML file
    public Element getRootElement(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        } catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // Returns if this is a valid XML file for the specified object type
    public boolean isValidFile (Element root) {
        for (String typeAttr : VALID_MODEL_NAMES)
             if (getAttribute(root, MODEL_ATTRIBUTE_STRING).equals(typeAttr))
                 return true;
        return false;
    }

    // Get value of Element's attribute
    public String getAttribute(Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // Get value of Element's text
    public String getTextValue(Element e, String tagName) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    public String getTextValueAtIndex(Element e, String tagName, int i) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(i).getTextContent();
        } else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return "";
        }
    }

    // Boilerplate code needed to make a documentBuilder
    public DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
