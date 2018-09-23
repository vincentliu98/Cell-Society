package xml.parser;

/**
 * Returns a Simulator for a Game Of Life simulation based on the file being loaded
 * @author jgp17
 */

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;

public class GameOfLifeXMLParser extends ParentXMLParser {
    public static final String IS_ALIVE_TAG = "isAlive";

    /**
     * Get the data contained in this XML file as an object
     */
    public static Simulator getModelSimulator(Element root) {
        GameOfLifeModel model = new GameOfLifeModel();
        CellGraph<Integer> graph = getIntegerCellGraph(root, IS_ALIVE_TAG);
        return new Simulator(graph, model);
    }
}