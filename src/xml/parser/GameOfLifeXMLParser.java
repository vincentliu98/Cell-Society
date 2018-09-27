package xml.parser;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;

/**
 * @author Inchan Hwang
 */
public class GameOfLifeXMLParser extends ParentXMLParser {

    public GameOfLifeXMLParser(String language) {
        super(language);
    }

    public static final String IS_ALIVE_TAG = "isAlive";

    /**
     *
     * @param root
     * @return
     */
    public static Simulator getModelSimulator(Element root) {
        GameOfLifeModel model = new GameOfLifeModel();
        CellGraph<Integer> graph = getIntegerCellGraph(root, IS_ALIVE_TAG);
        return new Simulator(graph, model);
    }
}
