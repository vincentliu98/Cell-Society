package visualization.model_controls;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.Simulator;
import simulation.factory.SpreadingFire;
import simulation.models.SpreadingFireModel;
import visualization.SimulationPanel;

import java.util.HashMap;

/**
 * SpreadingFireControl extends the abstract class ModelControl.
 * It contains one extra parameter - the probability of catching fire, thus having an extra Slider.
 *
 * @author Vincent Liu
 */

public class SpreadingFireControl extends ModelControl<Integer> {
    public static final double DEFAULT_PROBCATCH = 0.70;
    public static final Label probCatchCaption = new Label("probCatch:");
    private Slider probCatchBar = new Slider(0, 1, DEFAULT_PROBCATCH);
    private Label probCatchValue = new Label(
            Double.toString(probCatchBar.getValue()));

    public SpreadingFireControl(String shape) { this(SpreadingFire.generate(DEFAULT_CELL_NUM, shape)); }

    public SpreadingFireControl(Simulator<Integer> sim) {
        super(sim);

        probCatchBar.setShowTickMarks(true);
        probCatchBar.setShowTickLabels(true);
        probCatchBar.setOnMouseReleased(e -> {
            probCatchValue.setText(String.format("%.2f", probCatchBar.getValue()));
            handleParamChange();
        });
        probCatchBar.valueProperty().addListener((ov, old_val, new_val) ->
                probCatchValue.setText(String.valueOf(new_val.intValue())));
        getChildren().addAll(probCatchCaption, probCatchValue, probCatchBar);
    }

    @Override
    public void handleNumCellChange(int numCell) {
        isDirty = true;
        simPanel = new SimulationPanel<>(SpreadingFire.generate(numCell, simPanel.simulator().peekShape()));
    }

    @Override
    public void handleParamChange() {
        var pack = new HashMap<String, String>();
        pack.put(SpreadingFireModel.PARAM_CATCHPROB, Double.toString(probCatchBar.getValue()));
        simPanel.simulator().updateSimulationModel(pack);
    }
}
