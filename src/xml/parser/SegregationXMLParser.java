package xml.parser;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.SegregationModel;

import java.io.File;

/**
 * Returns a Simulator for a Segregation simulation based on the file being loaded
 * @author jgp17
 */
public class SegregationXMLParser extends ParentXMLParser {
    public static final String THRESHOLD_TAG = "satisfactionThreshold";
    public static final String TYPE_VALUE_TAG = "type";

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
     * @param root
     * @return
     */
    private static Simulator<Integer> getModelSimulator(Element root) {
        SegregationModel model = new SegregationModel(getDoubleValue(root, THRESHOLD_TAG));
        CellGraph<Integer> graph = getIntegerCellGraph(root, TYPE_VALUE_TAG);
        return new Simulator<>(graph, model);
    }

    public Simulator<Integer> getSimulator(File datafile) {
        return SegregationXMLParser.getModelSimulator(getRootElement(datafile));
    }
}
