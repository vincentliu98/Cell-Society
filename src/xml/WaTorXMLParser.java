//package xml;
//
//import org.w3c.dom.Element;
//import simulation.Cell;
//import simulation.CellGraph;
//import simulation.Simulator;
//import simulation.models.WaTorModel;
//import simulation.models.wator.Fish;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class WaTorXMLParser extends ParentXMLParser {
//    public static final String FISH_BREED_PERIOD_TAG = "fishBreedPeriod";
//    public static final String SHARK_BREED_PERIOD_TAG = "sharkBreedPeriod";
//    public static final String SHARK_STARVE_PERIOD_TAG = "sharkStarvePeriod";
//    public static final String KIND_TAG = "kind";
//
//    public static Simulator getModelSimulator(Element root) {
//        WaTorModel model = new WaTorModel(getIntValue(root, FISH_BREED_PERIOD_TAG),
//                getIntValue(root, SHARK_BREED_PERIOD_TAG), getIntValue(root, SHARK_STARVE_PERIOD_TAG));
//        CellGraph<Integer> graph = getIntegerCellGraph(root, LIVE_STATE_TAG);
//        return new Simulator(graph, model);
//    }
//
//    public static CellGraph<Fish> getIntegerCellGraph(Element root, String valTag) {
//        CellGraph<Fish> graph;
//        String shapeString = getTextValue(root, SHAPE_TAG).replaceAll("\\s","");
//        if (shapeString.equals(RECTANGLE_STRING)) {
//            graph = new CellGraph<Fish>(parseRectangle(root));
//        } else if (shapeString.equals(CIRCLE_STRING)) {
//            graph = new CellGraph<Fish>(parseCircle(root));
//        } else {
//            graph = null;
//        }
//        int numCells = root.getElementsByTagName("cell").getLength();
//        Map<Integer, Cell<Fish>> IDToCellMap = new HashMap<Integer, Cell<Fish>>();
//        for (int c = 0; c<numCells; c++) {
//            int uniqueID = getIntValueAtIndex(root, CELL_UNIQUE_ID_TAG, c);
//            Fish val = new Fish(getIntValueAtIndex(root, valTag, c));
//            double xPos = getDoubleValueAtIndex(root, CELL_XPOS_TAG, c);
//            double yPos = getDoubleValueAtIndex(root, CELL_YPOS_TAG, c);
//            IDToCellMap.put(uniqueID, new Cell<Fish>(val, xPos, yPos));
//        }
//        for (int c = 0; c<numCells; c++) {
//            int uniqueID = getIntValueAtIndex(root, CELL_UNIQUE_ID_TAG, c);
//            ArrayList<Integer> neighborIDs = parseNeighbors(root, c);
//            List<Cell<Fish>> neighborList = new ArrayList<>();
//            for (int n : neighborIDs)
//                neighborList.add(IDToCellMap.get(n));
//            graph.put(IDToCellMap.get(uniqueID), neighborList);
//        }
//        return graph;
//    }
//}
