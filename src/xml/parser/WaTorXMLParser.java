package xml.parser;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Returns a Simulator for a WaTor simulation based on the file being loaded
 * @author jgp17
 */
public class WaTorXMLParser extends ParentXMLParser {
    public static final String FISH_BREED_PERIOD_TAG = "fishBreedPeriod";
    public static final String SHARK_BREED_PERIOD_TAG = "sharkBreedPeriod";
    public static final String SHARK_STARVE_PERIOD_TAG = "sharkStarvePeriod";
    public static final String CELL_KIND_TAG = "kind";
    public static final String CELL_BREED_COUNTER_TAG = "breedCounter";
    public static final String CELL_STARVE_COUNTER_TAG = "starveCounter";

    /**
     * Create a parser for XML files of given type.
     *
     * @param language
     */
    public WaTorXMLParser(String language) {
        super(language);
    }

    /**
     *
     * @param root
     * @return
     */
    private static Simulator<Fish> getModelSimulator(Element root) {
        WaTorModel model = new WaTorModel(getIntValue(root, FISH_BREED_PERIOD_TAG),
                getIntValue(root, SHARK_BREED_PERIOD_TAG), getIntValue(root, SHARK_STARVE_PERIOD_TAG));
        CellGraph<Fish> graph = getWaTorCellGraph(root);
        return new Simulator<>(graph, model);
    }

    /**
     *
     * @param root
     * @return
     */
    private static CellGraph<Fish> getWaTorCellGraph(Element root) {
        CellGraph<Fish> graph = new CellGraph<>();
        NodeList cells = root.getElementsByTagName(CELL_TAG);
        Map<Integer, Cell<Fish>> IDToCellMap = new HashMap<Integer, Cell<Fish>>();
        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
            Element curCell = (Element) cells.item(cIndex);
            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG);
            int kind = getIntValue(curCell, CELL_KIND_TAG);
            Fish val = null;
            if (kind == WaTorModel.FISH || kind == WaTorModel.SHARK) {
                int breedCounter = getIntValue(curCell, CELL_BREED_COUNTER_TAG);
                int starveCounter = getIntValue(curCell, CELL_STARVE_COUNTER_TAG);
                if (kind == WaTorModel.FISH)
                    val = new Fish(breedCounter);
                else
                    val = new Shark(breedCounter, starveCounter);
            }
            int shapeCode = getIntValue(curCell, SHAPE_CODE_TAG);
            double xPos = getDoubleValue(curCell, CELL_XPOS_TAG);
            double yPos = getDoubleValue(curCell, CELL_YPOS_TAG);
            double width = getDoubleValue(curCell, SHAPE_WIDTH_TAG);
            double height = getDoubleValue(curCell, SHAPE_HEIGHT_TAG);
            IDToCellMap.put(uniqueID, new Cell<>(val, shapeCode, xPos, yPos, width, height));
        }
        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
            Element curCell = (Element) cells.item(cIndex);
            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG);
            ArrayList<Integer> neighborIDs = parseNeighbors(curCell);
            List<Cell<Fish>> neighborList = new ArrayList<>();
            for (int n : neighborIDs)
                neighborList.add(IDToCellMap.get(n));
            graph.put(IDToCellMap.get(uniqueID), neighborList);
        }
        return graph;
    }

    public Simulator<Fish> getSimulator(File datafile) {
        return WaTorXMLParser.getModelSimulator(getRootElement(datafile));
    }
}
