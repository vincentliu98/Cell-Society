package xml;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import simulation.CellGraph;
import simulation.Simulator;

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
public class SegregationXMLParser extends XMLParser {
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    // name of root attribute that notes the type of file expecting to parse
//    private final String TYPE_ATTRIBUTE
    public static final List<String> DATA_FIELDS = List.of(

    )

    /**
     * Get the data contained in this XML file as an object
     */

    public Simulator getSimulator(File dataFile) {

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

    private ArrayList<Integer> getValuesArrayList(Element root, int cellIndex) {
        ArrayList<Integer> valuesArrayList = new ArrayList<Integer>();
        for (String sub : SimulationData.VALUE_SUBFIELDS) {
                String attrStr = getTextValueAtIndex(root, sub, cellIndex);
                valuesArrayList.add(Integer.parseInt(attrStr.replaceAll("\\s", "")));
        }
        return valuesArrayList;
    }




    public static void main(String[] args) {
        File test = new  File("data\\Game_of_Life_2.xml");
        SegregationXMLParser myParser = new SegregationXMLParser();
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
