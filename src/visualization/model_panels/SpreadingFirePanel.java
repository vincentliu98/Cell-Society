package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.models.SpreadingFireModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Liu
 */

public class SpreadingFirePanel extends ModelPanel {
    public static final double DEFAULT_PROBABILITY = 0.8;
    private Slider probCatchBar = new Slider(0, 1, DEFAULT_PROBABILITY);
    private double probCatchVal = DEFAULT_PROBABILITY;

    public static final Label probCatchCaption = new Label("ProbCatch:");
    private Label thresholdValue = new Label(
            Double.toString(probCatchBar.getValue()));

    public SpreadingFirePanel() {
        super();
        probCatchBar.setShowTickMarks(true);
        probCatchBar.setShowTickLabels(true);
        probCatchBar.setOnMouseReleased(e -> {
            paramChanged = true;
            probCatchVal = probCatchBar.getValue();
            thresholdValue.setText(String.format("%.2f", probCatchVal));
        });
        getChildren().addAll(probCatchCaption, thresholdValue, probCatchBar);
    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put(SpreadingFireModel.PARAM_CATCHPROB, Double.toString(probCatchVal));
        return ret;
    }
}
