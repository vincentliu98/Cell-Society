package xml.parser;

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
    public static final Map<String, Map<String, Object>> VAL_TAG_TO_RANGE_MAP = Map.ofEntries(
            Map.entry(THRESHOLD_TAG, Map.of(MIN_STRING, 0.0, MAX_STRING, 1.0, DEF_STRING, 0.5)),
            Map.entry(TYPE_VALUE_TAG, Map.of(MIN_STRING, SegregationModel.EMPTY, MAX_STRING, SegregationModel.RED,
                    DEF_STRING, SegregationModel.EMPTY))
    );

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
        CellGraph<Integer> graph = getCellGraph(root, model);
        return new Simulator<>(graph, model);
    }

    @Override
    public Integer getCellValue(Element e) {
        return getIntValue(e, TYPE_VALUE_TAG, VAL_TAG_TO_RANGE_MAP);
    }
}
