package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Liu
 */

public class SegregationPanel extends ModelPanel {
    public static final double DEFAULT_THRESHOLD = 0.3;
    private Slider thresholdBar = new Slider(0, 1, DEFAULT_THRESHOLD);
    private double thresholdVal = DEFAULT_THRESHOLD;
    private boolean changeThreshold;

    public static final Label thresholdCaption = new Label("Threshold:");
    private Label thresholdValue = new Label(
            Double.toString(thresholdBar.getValue()));

    public SegregationPanel() {
        super();
        thresholdBar.setShowTickMarks(true);
        thresholdBar.setShowTickLabels(true);
        thresholdBar.setOnMouseReleased(e -> {
            changeThreshold = true;
            thresholdVal = thresholdBar.getValue();
            thresholdValue.setText(String.format("%.2f", thresholdVal));
        });
        getChildren().addAll(thresholdCaption, thresholdValue, thresholdBar);
    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put("satisfactionThreshold", Double.toString(thresholdVal));
        return ret;
    }

    @Override
    public boolean paramsChanged() { return changeThreshold; }

}
