package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.Simulator;
import simulation.factory.Segregation;
import simulation.models.SegregationModel;
import visualization.SimulationPanel;

import java.util.HashMap;

import static simulation.factory.Segregation.DEFAULT_THRESHOLD;

/**
 * SegregationControl extends the abstract class ModelControl.
 * It contains one extra parameter - threshold for satisfaction.
 *
 * @author Vincent Liu
 */

public class SegregationControl extends ModelControl<Integer> {
    public static final Label thresholdCaption = new Label("Threshold:");
    private Slider thresholdBar = new Slider(0, 1, DEFAULT_THRESHOLD);
    private Label thresholdValue = new Label(
            Double.toString(thresholdBar.getValue()));

    public SegregationControl(String shape) {
        this(Segregation.generate(DEFAULT_CELL_NUM, shape));
    }

    public SegregationControl(Simulator<Integer> sim) {
        super(sim);
        thresholdBar.setShowTickMarks(true);
        thresholdBar.setShowTickLabels(true);
        thresholdBar.setOnMouseReleased(e -> {
            thresholdValue.setText(String.format("%.2f", thresholdBar.getValue()));
            handleParamChange();
        });
        getChildren().addAll(thresholdCaption, thresholdValue, thresholdBar);
    }

    @Override
    public void handleNumCellChange(int numCell) {
        isDirty = true;
        simPanel = new SimulationPanel<>(Segregation.generate(numCell, simPanel.simulator().peekShape()));
    }

    @Override
    public void handleParamChange() {
        var pack = new HashMap<String, String>();
        pack.put(SegregationModel.PARAM_SATISFACTION, Double.toString(thresholdBar.getValue()));
        simPanel.simulator().updateSimulationModel(pack);
    }
}
