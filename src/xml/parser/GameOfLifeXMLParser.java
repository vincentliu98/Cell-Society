package xml.parser;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;

import java.io.File;
import java.util.Map;

/**
 * @author jgp17
 * @author Inchan Hwang
 */
public class GameOfLifeXMLParser extends ParentXMLParser {
    public static final String IS_ALIVE_TAG = "isAlive";
    public static final Map<String, Map<String, Object>> VAL_TAG_TO_RANGE_MAP = Map.ofEntries(
            Map.entry(IS_ALIVE_TAG, Map.of(MIN_STRING, GameOfLifeModel.DEAD, MAX_STRING, GameOfLifeModel.ALIVE,
                    DEF_STRING, GameOfLifeModel.DEAD))
    );

    public GameOfLifeXMLParser(String language) {
        super(language);
    }

    /**
     *
     * @param datafile
     * @return
     */
    public Simulator<Integer> getSimulator(File datafile) {
        GameOfLifeModel model = new GameOfLifeModel();
        CellGraph<Integer> graph = getCellGraph(getRootElement(datafile), model);
        return new Simulator<>(graph, model);
    }

    @Override
    public Integer getCellValue(Element e) {
        return getIntValue(e, IS_ALIVE_TAG, VAL_TAG_TO_RANGE_MAP);
    }
}
