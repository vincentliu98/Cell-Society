package xml.parser;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.factory.WaTor;
import simulation.models.GameOfLifeModel;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Returns a Simulator for a WaTor simulation based on the file being loaded
 * @author jgp17
 */
public class WaTorXMLParser extends ParentXMLParser {
    public static final String FISH_BREED_PERIOD_TAG = "fishBreedPeriod";
    public static final String SHARK_BREED_PERIOD_TAG = "sharkBreedPeriod";
    public static final String SHARK_STARVE_PERIOD_TAG = "sharkStarvePeriod";
    public static final String CELL_KIND_TAG = "kind";
    public static final String CELL_BREED_COUNTER_TAG = "breedCounter";
    public static final String CELL_STARVE_COUNTER_TAG = "starveCounter";
    public static final Map<String, Map<String, Object>> VAL_TAG_TO_RANGE_MAP = Map.ofEntries(
            Map.entry(FISH_BREED_PERIOD_TAG, Map.of(MIN_STRING, 0, MAX_STRING, Integer.MAX_VALUE, DEF_STRING, 10)),
            Map.entry(SHARK_BREED_PERIOD_TAG, Map.of(MIN_STRING, 0, MAX_STRING, Integer.MAX_VALUE, DEF_STRING, 10)),
            Map.entry(SHARK_STARVE_PERIOD_TAG, Map.of(MIN_STRING, 0, MAX_STRING, Integer.MAX_VALUE, DEF_STRING, 10)),
            Map.entry(CELL_KIND_TAG, Map.of(MIN_STRING, WaTorModel.FISH, MAX_STRING, WaTorModel.EMPTY, DEF_STRING, WaTorModel.EMPTY)),
            Map.entry(CELL_BREED_COUNTER_TAG, Map.of(MIN_STRING, 0, MAX_STRING, Integer.MAX_VALUE, DEF_STRING, 0)),
            Map.entry(CELL_STARVE_COUNTER_TAG, Map.of(MIN_STRING, 0, MAX_STRING, Integer.MAX_VALUE, DEF_STRING, 0))
    );

    /**
     * Create a parser for XML files of given type.
     *
     * @param language
     */
    public WaTorXMLParser(String language) {
        super(language);
    }

    /**
     *
     * @param datafile
     * @return
     */
    public Simulator<Fish> getSimulator(File datafile) {
        Element root = getRootElement(datafile);
        int fishBreedPeriod = getIntValue(root, FISH_BREED_PERIOD_TAG, VAL_TAG_TO_RANGE_MAP);
        int sharkBreedPeriod = getIntValue(root, SHARK_BREED_PERIOD_TAG, VAL_TAG_TO_RANGE_MAP);
        int sharkStarvePeriod = getIntValue(root, SHARK_STARVE_PERIOD_TAG, VAL_TAG_TO_RANGE_MAP);
        WaTorModel model = new WaTorModel(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
        CellGraph<Fish> graph = getCellGraph(root);
        return new Simulator<>(graph, model);
    }

    @Override
    public Fish getCellValue(Element e) {
        int kind = getIntValue(e, CELL_KIND_TAG, VAL_TAG_TO_RANGE_MAP);
        Fish val = null;
        if (kind == WaTorModel.FISH || kind == WaTorModel.SHARK) {
            int breedCounter = getIntValue(e, CELL_BREED_COUNTER_TAG, VAL_TAG_TO_RANGE_MAP);
            int starveCounter = getIntValue(e, CELL_STARVE_COUNTER_TAG, VAL_TAG_TO_RANGE_MAP);
            if (kind == WaTorModel.FISH)
                val = new Fish(breedCounter);
            else
                val = new Shark(breedCounter, starveCounter);
        }
        return val;
    }
}
