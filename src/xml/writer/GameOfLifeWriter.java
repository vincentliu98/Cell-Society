package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.GameOfLifeModel;

import java.io.File;
import java.util.List;

/**
 * GameOfLifeWriter extends the abstract class XMLWriter,
 * defining encoding methods for cell values and model parameters.
 *
 * @author Inchan Hwang
 */

public class GameOfLifeWriter extends XMLWriter<Integer> {
    public GameOfLifeWriter(CellGraph<Integer> graph_, File outFile_, String language) {
        super(graph_, outFile_, language);
    }

    @Override
    protected String getModelName() { return GameOfLifeModel.MODEL_NAME; }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var isAlive = doc.createElement("code");
        isAlive.appendChild(doc.createTextNode(value.toString()));
        return List.of(isAlive);
    }

    @Override
    protected List<Element> parseModelParams() { return List.of(); }
}
