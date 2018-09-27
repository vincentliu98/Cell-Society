package visualization.model_controls;

import simulation.Simulator;
import simulation.factory.GameOfLife;
import visualization.SimulationPanel;

/**
 * GameOfLifeControl extends the abstract class ModelControl.
 * It has no extra parameters.
 *
 * @author Vincent Liu
 */

public class GameOfLifeControl extends ModelControl<Integer> {
    public GameOfLifeControl(Simulator<Integer> sim) { super(sim); }
    public GameOfLifeControl(String shape) { this(GameOfLife.generate(DEFAULT_CELL_NUM, shape)); }

    @Override
    public void handleNumCellChange(int numCell) {
        isDirty = true;
        simPanel = new SimulationPanel<>(GameOfLife.generate(numCell, simPanel.simulator().peekShape()));
    }

    @Override
    public void handleParamChange() {
        // game of life doesn't have any model parameters
    }
}
