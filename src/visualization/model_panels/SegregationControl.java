package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.Simulator;
import simulation.factory.Segregation;

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

    private Simulator<Integer> simulator;

    public SegregationControl(String shape) {
        simulator = Segregation.generate(DEFAULT_CELL_NUM, shape);
    }

    public SegregationControl(Simulator<Integer> sim) {
        simulator = sim;

        thresholdBar.setShowTickMarks(true);
        thresholdBar.setShowTickLabels(true);
        thresholdBar.setOnMouseReleased(e -> {
            thresholdValue.setText(String.format("%.2f", thresholdBar.getValue()));
        });
        getChildren().addAll(thresholdCaption, thresholdValue, thresholdBar);
    }

    @Override
    public Simulator<Integer> simulator() { return simulator; }
}
