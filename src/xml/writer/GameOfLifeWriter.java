package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.SimulationModel;

import java.io.File;
import java.util.List;

public class GameOfLifeWriter extends XMLWriter<Integer> {
    public GameOfLifeWriter(SimulationModel<Integer> sim_, CellGraph<Integer> graph_, File outFile_) {
        super(sim_, graph_, outFile_);
    }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var isAlive = doc.createElement("isAlive");
        isAlive.appendChild(doc.createTextNode(value.toString()));
        return List.of(isAlive);
    }

    @Override
    protected List<Element> parseModelParams() { return List.of(); }
}
