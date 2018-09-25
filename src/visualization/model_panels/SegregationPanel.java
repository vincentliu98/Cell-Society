package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.models.SegregationModel;

import java.util.HashMap;
import java.util.Map;

/**
 * SegregationPanel extends the abstract class ModelPanel.
 * It contains one extra parameter - threshold for satisfaction.
 *
 * @author Vincent Liu
 */

public class SegregationPanel extends ModelPanel {
    public static final double DEFAULT_THRESHOLD = 0.30;
    private Slider thresholdBar = new Slider(0, 1, DEFAULT_THRESHOLD);

    public static final Label thresholdCaption = new Label("Threshold:");
    private Label thresholdValue = new Label(
            Double.toString(thresholdBar.getValue()));

    public SegregationPanel() {
        super();
        thresholdBar.setShowTickMarks(true);
        thresholdBar.setShowTickLabels(true);
        thresholdBar.setOnMouseReleased(e -> {
            paramChanged = true;
            thresholdValue.setText(String.format("%.2f", thresholdBar.getValue()));
        });
        getChildren().addAll(thresholdCaption, thresholdValue, thresholdBar);
    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put(SegregationModel.PARAM_SATISFACTION, Double.toString(thresholdBar.getValue()));
        return ret;
    }
}
