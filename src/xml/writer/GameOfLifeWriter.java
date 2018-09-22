package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.GameOfLifeModel;

import java.io.File;
import java.util.List;

/**
 * @author Inchan Hwang
 */

public class GameOfLifeWriter extends XMLWriter<Integer> {
    public GameOfLifeWriter(CellGraph<Integer> graph_, File outFile_) {
        super(graph_, outFile_);
    }

    @Override
    protected String getModelName() { return GameOfLifeModel.MODEL_NAME; }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var isAlive = doc.createElement("isAlive");
        isAlive.appendChild(doc.createTextNode(value.toString()));
        return List.of(isAlive);
    }

    @Override
    protected List<Element> parseModelParams() { return List.of(); }
}
