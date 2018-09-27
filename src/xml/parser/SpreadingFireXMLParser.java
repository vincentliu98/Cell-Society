package xml.parser;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.SpreadingFireModel;

import java.io.File;

/**
 * Returns a Simulator for a Spreading Of Fire simulation based on the file being loaded
 * @author jgp17
 */

public class SpreadingFireXMLParser extends ParentXMLParser {
    public static final String PROB_CATCH_TAG = "probCatch";
    public static final String LIVE_STATE_TAG = "liveState";

    /**
     * Create a parser for XML files of given type.
     *
     * @param language
     */
    public SpreadingFireXMLParser(String language) {
        super(language);
    }

    /**
     *
     * @param root
     * @return
     */
    private static Simulator<Integer> getModelSimulator(Element root) {
        SpreadingFireModel model = new SpreadingFireModel(getDoubleValue(root, PROB_CATCH_TAG));
        CellGraph<Integer> graph = getIntegerCellGraph(root, LIVE_STATE_TAG);
        return new Simulator<>(graph, model);
    }

    public Simulator<Integer> getSimulator(File datafile) {
        return SpreadingFireXMLParser.getModelSimulator(getRootElement(datafile));
    }
}
