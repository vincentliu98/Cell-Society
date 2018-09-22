package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.SegregationModel;

import java.io.File;
import java.util.List;

public class SegregationWriter extends XMLWriter<Integer> {
    private SegregationModel model;

    public SegregationWriter(SegregationModel sim_, CellGraph<Integer> graph_, File outFile_) {
        super(sim_, graph_, outFile_);
        model = sim_;
    }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var type = doc.createElement("type");
        type.appendChild(doc.createTextNode(Integer.toString(value)));
        return List.of(type);
    }

    @Override
    protected List<Element> parseModelParams() {
        var threshold = doc.createElement("satisfactionThreshold");
        threshold.appendChild(doc.createTextNode(Double.toString(model.getSatisfactionThreshold())));
        return List.of(threshold);
    }
}
