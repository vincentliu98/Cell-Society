package xml.parser;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;

import java.io.File;

/**
 * @author Inchan Hwang
 */
public class GameOfLifeXMLParser extends ParentXMLParser {
    public static final String IS_ALIVE_TAG = "isAlive";

    /**
     *
     * @param root
     * @return
     */
    private static Simulator<Integer> getModelSimulator(Element root) {
        GameOfLifeModel model = new GameOfLifeModel();
        CellGraph<Integer> graph = getIntegerCellGraph(root, IS_ALIVE_TAG);
        return new Simulator<>(graph, model);
    }

    public Simulator<Integer> getSimulator(File datafile) {
        return GameOfLifeXMLParser.getModelSimulator(getRootElement(datafile));
    }
}
