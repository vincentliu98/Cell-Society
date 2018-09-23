package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

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

        thresholdBar.valueProperty().addListener((ov, old_val, new_val) -> {
            changeThreshold = true;
            thresholdVal = new_val.doubleValue();
            thresholdValue.setText(String.format("%.2f", thresholdVal));
        });
        getChildren().addAll(thresholdCaption, thresholdValue, thresholdBar);
    }

    public double getThresholdVal() {
        return thresholdVal;
    }

    public void setChangeThreshold(boolean changeThreshold) {
        this.changeThreshold = changeThreshold;
    }

    public boolean getChangeThreshold() {
        return changeThreshold;
    }
}
