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
    public GameOfLifeXMLParser(String language) {
        super(language);
    }

    public static final String IS_ALIVE_TAG = "isAlive";

    /**
     *
     * @param datafile
     * @return
     */
    public Simulator<Integer> getSimulator(File datafile) {
        GameOfLifeModel model = new GameOfLifeModel();
        CellGraph<Integer> graph = getCellGraph(getRootElement(datafile));
        return new Simulator<>(graph, model);
    }

    @Override
    public Integer getCellValue(Element e) {
        return getIntValue(e, IS_ALIVE_TAG);
    }
}
