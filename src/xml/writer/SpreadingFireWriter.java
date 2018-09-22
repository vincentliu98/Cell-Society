package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.SimulationModel;
import simulation.models.SpreadingFireModel;

import java.io.File;
import java.util.List;

/**
 *
 * @author Vincent Liu
 */

public class SpreadingFireWriter extends XMLWriter<Integer> {
    private SpreadingFireModel model;

    public SpreadingFireWriter(SpreadingFireModel model_, CellGraph<Integer> graph_, File outFile_) {
        super(graph_, outFile_);
        model = model_;
    }

    @Override
    protected String getModelName() { return SpreadingFireModel.MODEL_NAME; }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var liveState = doc.createElement("liveState");
        liveState.appendChild(doc.createTextNode(value.toString()));
        return List.of(liveState);
    }

    @Override
    protected List<Element> parseModelParams() {
        var probCatch = doc.createElement("probCatch");
        probCatch.appendChild(doc.createTextNode(Double.toString(model.getProbCatch())));
        return List.of(probCatch);
    }
}