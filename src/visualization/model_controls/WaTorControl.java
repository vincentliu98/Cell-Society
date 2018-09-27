package visualization.model_controls;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import simulation.Simulator;
import simulation.factory.WaTor;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import visualization.SimulationPanel;

import java.util.HashMap;

/**
 * WaTorControl extends the abstract class ModelControl.
 * It has three extra parameters - fish's breeding period, shark's breeding period,
 * and shark's starving period. Thus, it contains three extra Slider for each parameters.
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */

public class WaTorControl extends ModelControl<Fish> {
    public static final double DEFAULT_FISHBREED = 2;
    public static final double DEFAULT_SHARKBREED = 5;
    public static final double DEFAULT_SHARKSTARVE = 5;

    private Slider fishBreedBar = new Slider(1, 20, DEFAULT_FISHBREED);
    private Slider sharkBreedBar = new Slider(1, 20, DEFAULT_SHARKBREED);
    private Slider sharkStarveBar = new Slider(1, 20, DEFAULT_SHARKSTARVE);

    public static final Label fishBreedCaption = new Label("Fish Breeding Period:");
    public static final Label sharkBreedCaption = new Label("Shark Breeding Period:");
    public static final Label sharkStarveCaption = new Label("Shark Starve Period:");

    private Label fishBreedValue = new Label(
            Double.toString(fishBreedBar.getValue()));
    private Label sharkBreedValue = new Label(
            Double.toString(sharkBreedBar.getValue()));
    private Label sharkStarveValue = new Label(
            Double.toString(sharkStarveBar.getValue()));

    public WaTorControl(String shape) { this(WaTor.generate(DEFAULT_CELL_NUM, shape)); }

    public WaTorControl(Simulator<Fish> sim) {
        super(sim);

        var scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        var wrapper = new VBox(10);

        fishBreedBar.setShowTickMarks(true);
        fishBreedBar.setShowTickLabels(true);
        fishBreedBar.setOnMouseReleased(e -> {
            fishBreedValue.setText(String.valueOf((int) fishBreedBar.getValue()));
            handleParamChange();
        });
        fishBreedBar.valueProperty().addListener((ov, old_val, new_val) ->
                fishBreedValue.setText(String.valueOf(new_val.intValue())));

        sharkBreedBar.setShowTickMarks(true);
        sharkBreedBar.setShowTickLabels(true);
        sharkBreedBar.setOnMouseReleased(e -> {
            sharkBreedValue.setText(String.valueOf((int) sharkBreedBar.getValue()));
            handleParamChange();
        });
        sharkBreedBar.valueProperty().addListener((ov, old_val, new_val) ->
                sharkBreedValue.setText(String.valueOf(new_val.intValue())));

        sharkStarveBar.setShowTickMarks(true);
        sharkStarveBar.setShowTickLabels(true);
        sharkStarveBar.setOnMouseReleased(e -> {
            sharkStarveValue.setText(String.valueOf((int) sharkStarveBar.getValue()));
            handleParamChange();
        });
        sharkStarveBar.valueProperty().addListener((ov, old_val, new_val) ->
                sharkStarveValue.setText(String.valueOf(new_val.intValue())));

		wrapper.getChildren().addAll(fishBreedCaption, fishBreedValue, fishBreedBar,
        sharkBreedCaption, sharkBreedValue, sharkBreedBar,
        sharkStarveCaption, sharkStarveValue, sharkStarveBar);
        
        scrollPane.setContent(wrapper);
        getChildren().add(scrollPane);
    }

    @Override
    public void handleNumCellChange(int numCell) {
        isDirty = true;
        simPanel = new SimulationPanel<>(WaTor.generate(numCell, simPanel.simulator().peekShape()));
    }

    @Override
    public void handleParamChange() {
        var pack = new HashMap<String, String>();
        pack.put(WaTorModel.PARAM_FISHBREED, Integer.toString((int) fishBreedBar.getValue()));
        pack.put(WaTorModel.PARAM_SHARKBREED, Integer.toString((int) sharkBreedBar.getValue()));
        pack.put(WaTorModel.PARAM_SHARKSTARVE, Integer.toString((int) sharkStarveBar.getValue()));
        simPanel.simulator().updateSimulationModel(pack);
    }
}
