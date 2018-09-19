package xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class XMLParser {
    // Readable error message that can be displayed by the GUI
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
    public SimulationData getSimulation(File dataFile) {
        var root = getRootElement(dataFile);
        if (! isValidFile(root, SimulationData.DATA_TYPE)) {
            throw new XMLException(ERROR_MESSAGE, SimulationData.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        var results = new HashMap<String, String>();
        for (var field : SimulationData.DATA_FIELDS) {
            System.out.print(field);
            if (field.equals("cellMap")) {
                getTextValue(root, "cell");

            }
            else {
                results.put(field, getTextValue(root, field));
            }
        }
        SimulationData sim = new SimulationData(results);
        return sim;
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

    // Get the chlidren of the i'th element with tag tagName
    private NodeList getChildren(Element e, int i, String tagName) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(i).getChildNodes();
        }
        else {
            // FIXME: empty string or null, is it an error to not find the text value?
            return null;
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
        XMLParser myParser = new XMLParser("media");
        myParser.getSimulation(test);
    }
}
