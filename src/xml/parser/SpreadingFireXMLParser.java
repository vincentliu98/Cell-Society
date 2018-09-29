package xml.parser;

import javafx.util.Pair;
import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.SpreadingFireModel;

import java.io.File;
import java.util.Map;

/**
 * Returns a Simulator for a Spreading Of Fire simulation based on the file being loaded
 * @author jgp17
 */

public class SpreadingFireXMLParser extends ParentXMLParser {
    public static final String PROB_CATCH_TAG = "probCatch";
    public static final String LIVE_STATE_TAG = "liveState";
    public static final Map<String, Map<String, Object>> VAL_TAG_TO_RANGE_MAP = Map.ofEntries(
            Map.entry(PROB_CATCH_TAG, Map.of(MIN_STRING, 0.0, MAX_STRING, 1.0, DEF_STRING, 0.5)),
            Map.entry(LIVE_STATE_TAG, Map.of(MIN_STRING, SpreadingFireModel.EMPTY, MAX_STRING, SpreadingFireModel.BURNING,
                    DEF_STRING, SpreadingFireModel.EMPTY))
    );

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
     * @param datafile
     * @return
     */

    public Simulator<Integer> getSimulator(File datafile) {
        Element root = getRootElement(datafile);
        SpreadingFireModel model = new SpreadingFireModel(getDoubleValue(root, PROB_CATCH_TAG, VAL_TAG_TO_RANGE_MAP));
        CellGraph<Integer> graph = getCellGraph(root);
        return new Simulator<>(graph, model);
    }

    @Override
    public Integer getCellValue(Element e) {
        return getIntValue(e, LIVE_STATE_TAG, VAL_TAG_TO_RANGE_MAP);
    }
}
