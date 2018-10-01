package xml.writer;

import org.w3c.dom.Element;
import simulation.CellGraph;
import simulation.models.SegregationModel;

import java.io.File;
import java.util.List;

/**
 * SegregationWriter extends the abstract class XMLWriter,
 * defining encoding methods for cell values and model parameters.
 *
 * @author Inchan Hwang
 */
public class SegregationWriter extends XMLWriter<Integer> {
    private SegregationModel model;

    public SegregationWriter(SegregationModel model_, CellGraph<Integer> graph_, File outFile_, String language) {
        super(graph_, outFile_, language);
        model = model_;
    }

    @Override
    protected String getModelName() { return SegregationModel.MODEL_NAME; }

    @Override
    protected List<Element> encodeCellValue(Integer value) {
        var type = doc.createElement("code");
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
