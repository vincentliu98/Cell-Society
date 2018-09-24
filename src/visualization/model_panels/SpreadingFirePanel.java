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
    public static final double DEFAULT_PROBCATCH = 0.7;
    private Slider probCatchBar = new Slider(0, 1, DEFAULT_PROBCATCH);
    private double probCatchVal;
    private boolean changeProbCatch;

    public static final Label probCatchCaption = new Label("probCatch:");
    private Label probCatchValue = new Label(
            Double.toString(probCatchBar.getValue()));

    public SpreadingFirePanel() {
        super();
        probCatchVal = DEFAULT_PROBCATCH;
        probCatchBar.setShowTickMarks(true);
        probCatchBar.setShowTickLabels(true);
        probCatchBar.setOnMouseReleased(e -> {
            changeProbCatch = true;
            probCatchVal = probCatchBar.getValue();
            probCatchValue.setText(String.format("%.2f", probCatchVal));
        });
        getChildren().addAll(probCatchCaption, probCatchValue, probCatchBar);

    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put(SpreadingFireModel.PARAM_CATCHPROB, Double.toString(probCatchVal));
        return ret;
    }

    @Override
    public boolean paramsChanged() {
        return changeProbCatch;
    }
}
