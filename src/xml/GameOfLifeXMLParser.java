package xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simulation.Simulator;

import java.io.File;
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
public class GameOfLifeXMLParser extends ParentXMLParser {

    /**
     * Get the data contained in this XML file as an object
     */
    public List<GameOfLifeXML> getGameOfLife(File dataFile) {

            var root = getRootElement(dataFile);
            // put text node under this node
            root.normalize();

            List<GameOfLifeXML> GameOfLifeXMLList = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                GameOfLifeXMLList.add(getGameOfLifeXML(nodeList.item(i)));
            }
            for (GameOfLifeXML cell : GameOfLifeXMLList) {
                System.out.println(cell.toString());
            }

        return GameOfLifeXMLList;
    }

    public static Simulator getModelSimulator(Element root) {

        return null;
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

}
