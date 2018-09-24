package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Liu
 */

public class SpreadingFirePanel extends ModelPanel {
    public static final double DEFAULT_PROBCATCH = 0.7;
    private Slider probCatchBar = new Slider(0, 1, DEFAULT_PROBCATCH);
    private double probCatch;
    private boolean changeProbCatch;

    public static final Label probCatchCaption = new Label("probCatch:");
    private Label probCatchValue = new Label(
            Double.toString(probCatchBar.getValue()));

    public SpreadingFirePanel() {
        super();
        probCatchBar.setShowTickMarks(true);
        probCatchBar.setShowTickLabels(true);
        probCatchBar.setOnMouseReleased(e -> {
            changeProbCatch = true;
            probCatch = probCatchBar.getValue();
            probCatchValue.setText(String.format("%.2f", probCatch));
        });
        getChildren().addAll(probCatchCaption, probCatchValue, probCatchBar);
        probCatch = DEFAULT_PROBCATCH;
    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put("probCatch", Double.toString(probCatch));
        return ret;
    }

    @Override
    public boolean paramsChanged() {
        return changeProbCatch;
    }
}
