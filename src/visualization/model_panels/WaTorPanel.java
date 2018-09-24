package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import simulation.models.WaTorModel;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Liu
 */

public class WaTorPanel extends ModelPanel {
    public static final double DEFAULT_FISHBREED = 2;
    public static final double DEFAULT_SHARKBREED = 5;
    public static final double DEFAULT_SHARKSTARVE = 5;

    private Slider fishBreedBar = new Slider(0, 20, DEFAULT_FISHBREED);
    private Slider sharkBreedBar = new Slider(0, 20, DEFAULT_SHARKBREED);
    private Slider sharkStarveBar = new Slider(0, 20, DEFAULT_SHARKSTARVE);

    private int fishBreed, sharkBreed, sharkStarve;

    public static final Label fishBreedCaption = new Label("Fish Breeding Period:");
    public static final Label sharkBreedCaption = new Label("Shark Breeding Period:");
    public static final Label sharkStarveCaption = new Label("Shark Starve Period:");

    private Label fishBreedValue = new Label(
            Double.toString(fishBreedBar.getValue()));
    private Label sharkBreedValue = new Label(
            Double.toString(sharkBreedBar.getValue()));
    private Label sharkStarveValue = new Label(
            Double.toString(sharkStarveBar.getValue()));

    private boolean paramChanged;

    public WaTorPanel() {
        super();

        var scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        var wrapper = new VBox(10);

        fishBreedBar.setShowTickMarks(true);
        fishBreedBar.setShowTickLabels(true);
        fishBreedBar.setOnMouseReleased(e -> {
            paramChanged = true;
            fishBreed = (int) fishBreedBar.getValue();
            fishBreedValue.setText(String.valueOf(fishBreed));
        });

        sharkBreedBar.setShowTickMarks(true);
        sharkBreedBar.setShowTickLabels(true);
        sharkBreedBar.setOnMouseReleased(e -> {
            paramChanged = true;
            sharkBreed = (int) sharkBreedBar.getValue();
            sharkBreedValue.setText(String.valueOf(sharkBreed));
        });

        sharkStarveBar.setShowTickMarks(true);
        sharkStarveBar.setShowTickLabels(true);
        sharkStarveBar.setOnMouseReleased(e -> {
            paramChanged = true;
            sharkStarve = (int) sharkStarveBar.getValue();
            sharkStarveValue.setText(String.valueOf(sharkStarve));
        });

		wrapper.getChildren().addAll(fishBreedCaption, fishBreedValue, fishBreedBar,
        sharkBreedCaption, sharkBreedValue, sharkBreedBar,
        sharkStarveCaption, sharkStarveValue, sharkStarveBar);
        
        scrollPane.setContent(wrapper);
        getChildren().add(scrollPane);
    }

    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put(WaTorModel.PARAM_FISHBREED, Integer.toString(fishBreed));
        ret.put(WaTorModel.PARAM_SHARKBREED, Integer.toString(sharkBreed));
        ret.put(WaTorModel.PARAM_SHARKSTARVE, Integer.toString(sharkStarve));
        return ret;
    }

    @Override
    public boolean paramsChanged() {
        return paramChanged;
    }

}
