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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Vincent Liu
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class GameOfLifeXMLParser {
    // Readable error message that can be displayed by the GUI
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;


    /**
     * Create a parser for XML files of given type.
     */
    public GameOfLifeXMLParser(String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /**
     * Get the data contained in this XML file as an object
     */
    public List<GameOfLifeXML> getGameOfLife(File dataFile) {

            var root = getRootElement(dataFile);
            if (!isValidFile(root, GameOfLifeXML.DATA_TYPE)) {
                throw new XMLException(ERROR_MESSAGE, GameOfLifeXML.DATA_TYPE);
            }
            // put text node under this node
            root.normalize();
            NodeList nodeList = root.getElementsByTagName("cell");

            List<GameOfLifeXML> GameOfLifeXMLList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                GameOfLifeXMLList.add(getGameOfLifeXML(nodeList.item(i)));
            }
            for (GameOfLifeXML cell : GameOfLifeXMLList) {
                System.out.println(cell.toString());
            }

        return GameOfLifeXMLList;
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

    private static GameOfLifeXML getGameOfLifeXML(Node node) {
        //XMLReaderDOM domReader = new XMLReaderDOM();
        GameOfLifeXML cell = new GameOfLifeXML();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            cell.setMyX(Double.parseDouble(getTagValue("x", element).replaceAll(" ", "")));
            cell.setMyY(Double.parseDouble(getTagValue("y", element).replaceAll(" ", "")));
            cell.setMyUniqueID(Integer.parseInt(getTagValue("uniqueID", element).replaceAll(" ", "")));
            cell.setIsAlive(Integer.parseInt(getTagValue("isAlive", element).replaceAll(" ", "")));
            cell.setMyNeighbors(getNeighborValue("neighbors", element));
        }
        return cell;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private static List<Integer> getNeighborValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        String[] temp = node.getNodeValue().replaceAll(" ", "").split(",");
        Integer[] neighbors = new Integer[temp.length];
        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = Integer.parseInt(temp[i]);
        }
        return Arrays.stream(neighbors).collect(Collectors.toList());
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

    // Boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
