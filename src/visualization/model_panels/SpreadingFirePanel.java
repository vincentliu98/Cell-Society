package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import simulation.models.SpreadingFireModel;

import java.util.HashMap;
import java.util.Map;

/**
 * SpreadingFirePanel extends the abstract class ModelPanel.
 * It contains one extra parameter - the probability of catching fire, thus having an extra Slider.
 *
 * @author Vincent Liu
 */

public class SpreadingFirePanel extends ModelPanel {
    public static final double DEFAULT_PROBCATCH = 0.70;
    private Slider probCatchBar = new Slider(0, 1, DEFAULT_PROBCATCH);

    public static final Label probCatchCaption = new Label("probCatch:");
    private Label probCatchValue = new Label(
            Double.toString(probCatchBar.getValue()));

    public SpreadingFirePanel() {
        super();
        probCatchBar.setShowTickMarks(true);
        probCatchBar.setShowTickLabels(true);
        probCatchBar.setOnMouseReleased(e -> {
            paramChanged = true;
            probCatchValue.setText(String.format("%.2f", probCatchBar.getValue()));
        });
        getChildren().addAll(probCatchCaption, probCatchValue, probCatchBar);

    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put(SpreadingFireModel.PARAM_CATCHPROB, Double.toString(probCatchBar.getValue()));
        return ret;
    }
}
