package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.Simulator;
import simulation.factory.SpreadingFire;

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

    private Simulator<Integer> simulator;

    public SpreadingFireControl(String shape) {
        simulator = SpreadingFire.generate(DEFAULT_CELL_NUM, shape);
    }

    public SpreadingFireControl(Simulator<Integer> sim) {
        simulator = sim;

        probCatchBar.setShowTickMarks(true);
        probCatchBar.setShowTickLabels(true);
        probCatchBar.setOnMouseReleased(e -> {
            probCatchValue.setText(String.format("%.2f", probCatchBar.getValue()));
        });
        getChildren().addAll(probCatchCaption, probCatchValue, probCatchBar);

    }

    @Override
    public Simulator<Integer> simulator() { return simulator; }
}
