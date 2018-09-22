package xml;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.SpreadingFireModel;

public class WaTorXMLParser extends ParentXMLParser {
    public static final String PROB_CATCH_TAG = "probCatch";
    public static final String LIVE_STATE_TAG = "liveState";

    public static Simulator getModelSimulator(Element root) {
        SpreadingFireModel model = new SpreadingFireModel(getDoubleValue(root, PROB_CATCH_TAG));
        CellGraph<Integer> graph = getIntegerCellGraph(root, LIVE_STATE_TAG);
        return new Simulator(graph, model);
    }
}
