package xml.parser;

import javafx.util.Pair;
import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.SegregationModel;

import java.io.File;
import java.util.Map;

/**
 * Returns a Simulator for a Segregation simulation based on the file being loaded
 * @author jgp17
 */
public class SegregationXMLParser extends ParentXMLParser {
    public static final String THRESHOLD_TAG = "satisfactionThreshold";
    public static final String TYPE_VALUE_TAG = "type";
    public static final Map<String, Pair> VAL_TAG_TO_RANGE_MAP = Map.ofEntries(
            Map.entry(THRESHOLD_TAG, new Pair<>(0.0, 1.0)));

    /**
     * Create a parser for XML files of given type.
     *
     * @param language
     */
    public SegregationXMLParser(String language) {
        super(language);
    }

    /**
     *
     * @param datafile
     * @return
     */

    public Simulator<Integer> getSimulator(File datafile) {
        Element root = getRootElement(datafile);
        SegregationModel model = new SegregationModel(getDoubleValue(root, THRESHOLD_TAG, VAL_TAG_TO_RANGE_MAP));
        CellGraph<Integer> graph = getCellGraph(root);
        return new Simulator<>(graph, model);
    }

    @Override
    public Integer getCellValue(Element e) {
        return getIntValue(e, TYPE_VALUE_TAG);
    }
}
