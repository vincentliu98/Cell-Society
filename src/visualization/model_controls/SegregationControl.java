package visualization.model_controls;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Pair;
import simulation.Simulator;
import simulation.factory.Segregation;
import simulation.models.SegregationModel;
import visualization.SimulationPanel;

import java.util.HashMap;
import java.util.List;

import static simulation.factory.Segregation.DEFAULT_THRESHOLD;

/**
 * SegregationControl extends the abstract class ModelControl.
 * It contains one extra parameter - threshold for satisfaction.
 *
 * @author Vincent Liu
 */
public class SegregationControl extends ModelControl<Integer> {
    public static final double MIN_THRESHOLD = 0;
    public static final double MAX_THRESHOLD = 1;
    public static final Label thresholdCaption = new Label("Threshold:");
    private Slider thresholdBar = new Slider(MIN_THRESHOLD, MAX_THRESHOLD, DEFAULT_THRESHOLD);
    private Label thresholdValue = new Label(
            Double.toString(thresholdBar.getValue()));

    /**
     * Change the shape of cells based on the existing model
     *
     * @param shape
     */
    public SegregationControl(String shape) {
        this(Segregation.generate(DEFAULT_CELL_NUM, shape));
    }

    /**
     * Generate a SegregationControl that contains a Slider to adjust the parameter threshold.
     *
     * @param sim
     */
    public SegregationControl(Simulator<Integer> sim) {
        super(sim);
        thresholdBar.setShowTickMarks(true);
        thresholdBar.setShowTickLabels(true);
        thresholdBar.setOnMouseReleased(e -> {
            thresholdValue.setText(String.format("%.2f", thresholdBar.getValue()));
            handleParamChange();
        });
        thresholdBar.valueProperty().addListener((ov, old_val, new_val) ->
                thresholdValue.setText(String.valueOf(new_val.doubleValue())));
        getChildren().addAll(thresholdCaption, thresholdValue, thresholdBar);
    }

    @Override
    public void handleNeighborChange(int numCell, List<Pair<Integer, Integer>> neighborIndices) {
        isDirty = true;
        simPanel = new SimulationPanel<>(
                Segregation.generate(numCell, simPanel.simulator().peekShape(), neighborIndices)
        );
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
