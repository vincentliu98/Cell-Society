package xml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;
import simulation.models.SimulationModel;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Vincent Liu
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class GameOfLifeXMLParser extends ParentXMLParser {
    public static final String IS_ALIVE_TAG = "isAlive";

    /**
     * Get the data contained in this XML file as an object
     */
//    public List<GameOfLifeXML> getGameOfLife(File dataFile) {
//
//            var root = getRootElement(dataFile);
//            // put text node under this node
//            root.normalize();
//
//            List<GameOfLifeXML> GameOfLifeXMLList = new ArrayList<>();
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                GameOfLifeXMLList.add(getGameOfLifeXML(nodeList.item(i)));
//            }
//            for (GameOfLifeXML cell : GameOfLifeXMLList) {
//                System.out.println(cell.toString());
//            }
//
//        return GameOfLifeXMLList;
//    }

    public static Simulator getModelSimulator(Element root) {
        GameOfLifeModel model = new GameOfLifeModel();
        CellGraph<Integer> graph;
        String shapeString = getTextValue(root, SHAPE_TAG).replaceAll("\\s","");
        if (shapeString.equals(RECTANGLE_STRING)) {
            graph = new CellGraph<Integer>(parseRectangle(root));
        } else if (shapeString.equals(CIRCLE_STRING)) {
            graph = new CellGraph<Integer>(parseCircle(root));
        } else {
            graph = null;
        }
        int numCells = root.getElementsByTagName("cell").getLength();
        Map<Integer, Cell<Integer>> IDToCellMap = new HashMap<Integer, Cell<Integer>>();
        for (int c = 0; c<numCells; c++) {
            int uniqueID = getIntValueAtIndex(root, CELL_UNIQUE_ID_TAG, c);
            int val = getIntValueAtIndex(root, IS_ALIVE_TAG, c);
            double xPos = getDoubleValueAtIndex(root, CELL_XPOS_TAG, c);
            double yPos = getDoubleValueAtIndex(root, CELL_YPOS_TAG, c);
            IDToCellMap.put(uniqueID, new Cell<Integer>(val, xPos, yPos));
        }
        for (int c = 0; c<numCells; c++) {
            int uniqueID = getIntValueAtIndex(root, CELL_UNIQUE_ID_TAG, c);
            ArrayList<Integer> neighborIDs = parseNeighbors(root, c);
            List<Cell<Integer>> neighborList = new ArrayList<>();
            for (int n : neighborIDs)
                neighborList.add(IDToCellMap.get(n));
            graph.put(IDToCellMap.get(uniqueID), neighborList);
        }
        return new Simulator(graph, model);
    }

//    private static GameOfLifeXML getGameOfLifeXML(Node node) {
//        //XMLReaderDOM domReader = new XMLReaderDOM();
//        GameOfLifeXML cell = new GameOfLifeXML();
//        if (node.getNodeType() == Node.ELEMENT_NODE) {
//            Element element = (Element) node;
//            cell.setMyX(Double.parseDouble(getTagValue("x", element).replaceAll(" ", "")));
//            cell.setMyY(Double.parseDouble(getTagValue("y", element).replaceAll(" ", "")));
//            cell.setMyUniqueID(Integer.parseInt(getTagValue("uniqueID", element).replaceAll(" ", "")));
//            cell.setIsAlive(Integer.parseInt(getTagValue("isAlive", element).replaceAll(" ", "")));
//            cell.setMyNeighbors(getNeighborValue("neighbors", element));
//        }
//        return cell;
//    }
//
//    private static String getTagValue(String tag, Element element) {
//        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//        Node node = (Node) nodeList.item(0);
//        return node.getNodeValue();
//    }
//
//    private static List<Integer> getNeighborValue(String tag, Element element) {
//        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//        Node node = (Node) nodeList.item(0);
//        String[] temp = node.getNodeValue().replaceAll(" ", "").split(",");
//        Integer[] neighbors = new Integer[temp.length];
//        for (int i = 0; i < neighbors.length; i++) {
//            neighbors[i] = Integer.parseInt(temp[i]);
//        }
//        return Arrays.stream(neighbors).collect(Collectors.toList());
//    }

}
