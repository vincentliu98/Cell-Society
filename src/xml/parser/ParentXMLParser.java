package xml.parser;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.factory.NeighborUtils;
import simulation.models.SimulationModel;
import utility.ShapeUtils;
import xml.XMLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.round;
import static xml.writer.XMLWriter.DELIMITER;

/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author jgp17 
 * @author Inchan Hwang
 */

public abstract class ParentXMLParser<T> {
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();
    public static final String VAL_CODE_TAG = "code";
    private static ResourceBundle myResources;
    public static final String DEFAULT_RESOURCES = "Errors";
    private static final String LOAD_AGAIN_KEY = "LoadAgainMsg";
    public static final String MODEL_ATTRIBUTE_TAG = "modelName";
    public static final String PROB_VALS_TAG = "probVals";
    public static final String CELLS_PER_SIDE_TAG = "cellsPerSide";
//    public static final String SHAPE_WIDTH_TAG = "shapeWidth";
//    public static final String SHAPE_HEIGHT_TAG = "shapeHeight";
    public static final String SHAPE_CODE_TAG = "shapeCode";
    public static final String CELL_TAG = "cell";
//    public static final String CELL_UNIQUE_ID_TAG = "uniqueID";
//    public static final String CELL_NEIGHBORS_TAG = "neighbors";
//    public static final String CELL_XPOS_TAG = "cx";
//    public static final String CELL_YPOS_TAG = "cy";
    public static final String MIN_STRING = "min";
    public static final String MAX_STRING = "max";
    public static final String DEF_STRING = "def";
    public static final Map<String, Map<String, Object>> STD_TAG_TO_RANGE_MAP = Map.ofEntries(
            Map.entry(SHAPE_CODE_TAG, Map.of(MIN_STRING, Collections.min(ShapeUtils.shapeCodes()), MAX_STRING,
            Collections.min(ShapeUtils.shapeCodes()))),
            Map.entry(CELLS_PER_SIDE_TAG, Map.of(MIN_STRING, 1, MAX_STRING, 100, DEF_STRING, 10))//,
//            Map.entry(SHAPE_WIDTH_TAG, Map.of(MIN_STRING, 0.1, MAX_STRING, Double.MAX_VALUE)),
//            Map.entry(SHAPE_HEIGHT_TAG, Map.of(MIN_STRING, 0.1, MAX_STRING, Double.MAX_VALUE)),
//            Map.entry(CELL_UNIQUE_ID_TAG, Map.of(MIN_STRING, Integer.MIN_VALUE, MAX_STRING, Integer.MAX_VALUE)),
//            Map.entry(CELL_XPOS_TAG, Map.of(MIN_STRING, 0.0, MAX_STRING, Double.MAX_VALUE)),
//            Map.entry(CELL_YPOS_TAG, Map.of(MIN_STRING, 0.0, MAX_STRING, Double.MAX_VALUE))
    );
    private static final double MARGIN = 0.5;
    public static final String WHITESPACE = "\\s";
    public static final String EMPTY = "";

    /**
     * Create a parser for XML files of given type.
     */
    public ParentXMLParser(String language) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCES + language);
    }

    /**
     * Get the data contained in this XML file as an object
     */

    public abstract Simulator<T> getSimulator(File datafile);

//    public abstract T getCellValue(Element e);

    public CellGraph<T> getCellGraph(Element root, SimulationModel<T> model) {
        int n = getIntValue(root, CELLS_PER_SIDE_TAG, 1, 100, 10);
        ArrayList<ArrayList<T>> cellVals;
        if (root.getElementsByTagName(PROB_VALS_TAG).getLength() != 0) {
            cellVals = cellValsFromProbs(root, model, n);
        } else {
            cellVals = cellValsFromList(root, model, n);
        }
        int shapeCode = getIntValue(root, SHAPE_CODE_TAG, Collections.min(ShapeUtils.shapeCodes()),
                Collections.max(ShapeUtils.shapeCodes()), 0);
        if (shapeCode == 0) {
            return generateRect(n, n, cellVals);
        } else {
            return generateTri(n, n, cellVals);
        }
    }

    public ArrayList<ArrayList<T>> cellValsFromList(Element root, SimulationModel<T> model, int n) {
        NodeList cells = root.getElementsByTagName(CELL_TAG);
        ArrayList<ArrayList<T>> cellVals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<T> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                Element curCell = (Element) cells.item(i*n+j);
                int code = getIntValue(curCell, VAL_CODE_TAG, Collections.min(model.getCodes()),
                        Collections.max(model.getCodes()), model.getDefaultCode());
                row.add(model.getValFromCode(code));
            }
            cellVals.add(row);
        }
        return cellVals;
    }

//    /**
//     *
//     * @param root
//     * @return
//     */
//    public CellGraph<T> getCellGraphFromLayout(Element root, SimulationModel<T> model, int n) {
//        NodeList cells = root.getElementsByTagName(CELL_TAG);
//        Map<Integer, Cell<T>> IDToCellMap = new HashMap<>();
//        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
//            Element curCell = (Element) cells.item(cIndex);
//            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG, STD_TAG_TO_RANGE_MAP);
//            int shapeCode = getIntValue(curCell, SHAPE_CODE_TAG, STD_TAG_TO_RANGE_MAP);
//            double shapeWidth = getDoubleValue(curCell, SHAPE_WIDTH_TAG, STD_TAG_TO_RANGE_MAP);
//            double shapeHeight = getDoubleValue(curCell, SHAPE_HEIGHT_TAG, STD_TAG_TO_RANGE_MAP);
//            double xPos = getDoubleValue(curCell, CELL_XPOS_TAG, STD_TAG_TO_RANGE_MAP);
//            double yPos = getDoubleValue(curCell, CELL_YPOS_TAG, STD_TAG_TO_RANGE_MAP);
//            IDToCellMap.put(uniqueID, new Cell<>(getCellValue(curCell), shapeCode, xPos, yPos, shapeWidth, shapeHeight));
//        }
//        CellGraph<T> graph = new CellGraph<>();
//        for (int cIndex = 0; cIndex < cells.getLength(); cIndex++) {
//            Element curCell = (Element) cells.item(cIndex);
//            int uniqueID = getIntValue(curCell, CELL_UNIQUE_ID_TAG, STD_TAG_TO_RANGE_MAP);
//            List<Integer> neighborIDs = parseNeighbors(curCell);
//            List<Cell<T>> neighborList = new ArrayList<>();
//            for (int neighborID : neighborIDs) {
//                neighborList.add(IDToCellMap.get(neighborID));
//            }
//            graph.put(IDToCellMap.get(uniqueID), neighborList);
//        }
//        return graph;
//    }

    public ArrayList<ArrayList<T>> cellValsFromProbs(Element root, SimulationModel<T> model, int n) {
        Random rng = new Random();
        ArrayList<ArrayList<T>> cellVals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<T> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                var val = getRandomVal(getValToProbMap(root, model), rng);
                row.add(val);
            }
            cellVals.add(row);
        }
        return cellVals;
    }

    private T getRandomVal(Map<T, Double> valToProbMap, Random rng) {
        var x = rng.nextDouble();
        for (T v : valToProbMap.keySet()) {
            if (x < valToProbMap.get(v)) {
                return v;
            }
        }
        return null;
    }

    public Map<T, Double> getValToProbMap(Element root, SimulationModel<T> model) {
            String probStr = getTextValue(root, PROB_VALS_TAG);
            List<Double> probList = new ArrayList<>();
            String[] probStrArray = probStr.split(DELIMITER);
            for (String s : probStrArray) {
                probList.add(Double.parseDouble(s));
            }
            List<Integer> codes = model.getCodes();
            if (codes.size() != probList.size()) {
                probList.clear();
                double inc = 1.0 / codes.size();
                for (int c = 0; c < codes.size(); c++) {
                    probList.add(inc*(c+1));
                }
            }
            Map<T, Double> valToProb = new HashMap<>();
            for (int c = 0; c < codes.size(); c++) {
                var val = model.getValFromCode(codes.get(c));
                valToProb.put(val, probList.get(c));
            }
            return valToProb;
    }

    public CellGraph<T> generateRect(int row, int column, ArrayList<ArrayList<T>> vals) {
        ArrayList<Cell<T>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(vals.get(i).get(j), ShapeUtils.RECTANGLE,
                        (j+MARGIN)*width, (i+MARGIN)*height,
                        width, height
                );
                cells.add(cell);
            }
        }
        return NeighborUtils.rectangularGraph(cells, row, column, NeighborUtils.indicesFor8Rectangle());
    }

    public CellGraph<T> generateTri(int row, int column, ArrayList<ArrayList<T>> vals) {
        ArrayList<Cell<T>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / ((column+1)/2);
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(vals.get(i).get(j), (i+j)%2==0 ? ShapeUtils.TRIANGLE : ShapeUtils.TRIANGLE_FLIP,
                        (MARGIN*j)*width, (i+MARGIN)*height,
                        width, height);
                cells.add(cell);
            }
        }
        return NeighborUtils.triangularGraph(cells, row, column, NeighborUtils.indicesFor12Triangle());
    }

//    /**
//     *
//     * @param root
//     * @return
//     */
//    public static List<Integer> parseNeighbors(Element root) {
//        String neighborStr = getTextValue(root, CELL_NEIGHBORS_TAG);
//        ArrayList<Integer> neighborArrayList = new ArrayList<>();
//        String[] neighborStrArray = neighborStr.split(DELIMITER);
//        for (String s : neighborStrArray) {
//            neighborArrayList.add(Integer.parseInt(s));
//        }
//        return neighborArrayList;
//    }

    /**
     * Get value of Element's text
     */
    public static String getTextValue(Element e, String tagName) {
        var nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            throw new XMLException(myResources.getString("MissingTagMsg")+ myResources.getString(LOAD_AGAIN_KEY),
                    e.toString(), tagName);
        }
    }

    /**
     *
     * @param e
     * @param tagName
     * @return
     */
    public static int getIntValue(Element e, String tagName, int min, int max, int def) {
        try {
            String str = getTextValue(e, tagName).replaceAll(WHITESPACE, EMPTY);
            try {
                int i = Integer.parseInt(str);
                if (i < min || i > max) {
                    throw new XMLException(myResources.getString("IntOutOfRangeMsg"), tagName, i, min, max);
                }
                return i;
            } catch (NumberFormatException ex) {
                throw new XMLException(myResources.getString("ValueNotIntMsg"), tagName, str);
            }
        } catch (XMLException ex){
            return def;
        }
    }

    /**
     *
     * @param e
     * @param tagName
     * @return
     */
    public static double getDoubleValue(Element e, String tagName, double min, double max, double def) {
        try {
            String str = getTextValue(e, tagName).replaceAll(WHITESPACE, EMPTY);
            try {
                double db = Double.parseDouble(str);
                if (db < min || db > max) {
                    throw new XMLException(myResources.getString("DoubleOutOfRangeMsg"), tagName, db, min, max);
                }
                return db;
            } catch (NumberFormatException ex) {
                throw new XMLException(myResources.getString("ValueNotDoubleMsg"), tagName, str);
            }
        } catch (XMLException ex){
            return def;
        }
    }

//    private static Object getDefault(String tagName, Map<String, Map<String, Object>> rangeMap, Exception ex) {
//            var def = rangeMap.get(tagName).get(DEF_STRING);
//            if (def == null) {
//                throw new XMLException(ex.getMessage() + myResources.getString("NoDefaultMsg") +
//                        myResources.getString(LOAD_AGAIN_KEY), tagName);
//            }
//            return rangeMap.get(tagName).get(DEF_STRING);
//    }

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
        return getTextValue(getRootElement(xmlFile), MODEL_ATTRIBUTE_TAG);
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