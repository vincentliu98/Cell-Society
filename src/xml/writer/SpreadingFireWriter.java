package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.SimulationModel;

import java.io.File;
import java.util.List;

/**
 *
 * @author Vincent Liu
 */

public class SpreadingFireWriter extends XMLWriter<Integer> {
    public SpreadingFireWriter(SimulationModel<Integer> sim_, CellGraph<Integer> graph_, File outFile_) {
        super(sim_, graph_, outFile_);
    }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var liveState = doc.createElement("liveState");
        liveState.appendChild(doc.createTextNode(value.toString()));
        return List.of(liveState);
    }

    @Override
    protected List<Element> parseModelParams() { return List.of(); }
}
