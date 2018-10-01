package visualization.model_controls;

import javafx.util.Pair;
import simulation.Simulator;
import simulation.factory.GameOfLife;
import visualization.SimulationPanel;

import java.util.List;

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
    public void handleStructureChange(int numCell, List<Pair<Integer, Integer>> neighborIndices) {
        isDirty = true;
        simPanel = new SimulationPanel<>(
                GameOfLife.generate(numCell, simPanel.simulator().peekShape(), neighborIndices)
        );
    }

    @Override
    public void handleParamChange() {
        // game of life doesn't have any model parameters
    }


}
