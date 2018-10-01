package visualization.model_controls;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import simulation.Simulator;
import visualization.SimulationPanel;
import visualization.neighbor_chooser.NeighborChooser;

import java.util.List;

/**
 *  For various model-specific panels that defines uniform theme across different panels.
 *  <p>ModelControl contains a common parameter for all models - number of cells.
 *  It contains a Slider that dynamically change the number of cells and update the simulation.
 *  It is also able to detect any parameter change that occurs in the UI and pass the change to the model.
 *  </p>
 *  <p>ModelControl holds reference to a simulation panel, replacing it if
 *  number of cells change. Each subclasses must implement two methods
 *  handleCellNumChange(), handleParamChange(), each handling the changes in
 *  each sliders.</p>
 *
 * @author Vincent Liu
 * @author Inchan Hwang
 */
public abstract class ModelControl<T> extends VBox {
    public static final int DEFAULT_CELL_NUM = 10;
    public static final int MODELCONTROL_SPACING = 25;
    public static final double MIN_CELL_NUM = 1;
    public static final double MAX_CELL_NUM = 100;
    public static final int BLOCK_INCREMENT = 4;
    public static final int MAJOR_TICK_UNIT = 20;
    public static final String CELLNUM_TEXT = "Cell Number / Side: ";


    private Label cellNumCaption = new Label(CELLNUM_TEXT + "0");
    private Slider numberBar = new Slider(MIN_CELL_NUM, MAX_CELL_NUM, DEFAULT_CELL_NUM);
    private Button chooseNeighbor;
    private NeighborChooser chooseNeighborDialog;

    protected SimulationPanel<T> simPanel;
    protected boolean isDirty;

    /**
     * Constructs a ModelControl and adds a Slider to allow users to change the number of cells in each side of the model
     *
     * @param sim
     */
    public ModelControl(Simulator<T> sim) {
        super(MODELCONTROL_SPACING);
        getStyleClass().add("modelPanel");

        isDirty = false;
        simPanel = new SimulationPanel<>(sim);

        setupNumberBar();
        setupNeighborChooser();

        getChildren().addAll(cellNumCaption, numberBar, chooseNeighbor);
    }

    private void setupNumberBar() {
        numberBar.setShowTickMarks(true);
        numberBar.setShowTickLabels(true);
        numberBar.setMajorTickUnit(MAJOR_TICK_UNIT);
        numberBar.setSnapToTicks(true);
        numberBar.setBlockIncrement(BLOCK_INCREMENT);

        numberBar.setOnMouseReleased(e -> {
            cellNumCaption.setText(CELLNUM_TEXT + Integer.toString((int) numberBar.getValue()));
            handleNumCellChange((int) numberBar.getValue());
            handleParamChange();
        });

        numberBar.valueProperty().addListener((ov, old_val, new_val) -> {
            cellNumCaption.setText(CELLNUM_TEXT + String.valueOf(new_val.intValue()));
        });
    }

    private void setupNeighborChooser() {
        // TODO: REMOVE HARDCODE
        chooseNeighborDialog = new NeighborChooser(simulator().peekShape());
        chooseNeighbor = new Button("Select Neighbor");
        chooseNeighbor.getStyleClass().add("neighbor-button");
        chooseNeighbor.setOnMouseClicked(e -> {
            var optRes = chooseNeighborDialog.showAndWait();
            optRes.ifPresent(res -> handleNeighborChange((int) numberBar.getValue(), res));
            handleParamChange();
        });
    }

    public abstract void handleNeighborChange(int numCell, List<Pair<Integer, Integer>> neighborIndices);

    /**
     * Since various models have different factory methods, the reinitialization
     * is left for each model's controller
     *
     * @param numCell
     */
    public abstract void handleNumCellChange(int numCell);

    /**
     * Since various models have different set of parameters, they
     * handle the parameter changes in their own way
     */
    public abstract void handleParamChange();

    /**
     * The word "consume" is used to emphasize that isDirty is reset to its default state
     * once the variable has been seen by its parent
     * @return whether the model need to be updated or not
     */
    public boolean consumeIsDirty() {
        var ret = isDirty;
        isDirty = false;
        return ret;
    }
    public SimulationPanel<T> simPanel() { return simPanel; }
    public Simulator<T> simulator() { return simPanel.simulator(); }
}
