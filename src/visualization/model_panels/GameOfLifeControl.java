package visualization.model_panels;

import simulation.Simulator;
import simulation.factory.GameOfLife;

/**
 * GameOfLifeControl extends the abstract class ModelControl.
 * It has no extra parameters.
 *
 * @author Vincent Liu
 */

public class GameOfLifeControl extends ModelControl<Integer> {
    private Simulator<Integer> simulator;

    public GameOfLifeControl(Simulator<Integer> sim) {
        simulator = sim;
    }

    public GameOfLifeControl(String shape) {
        simulator = GameOfLife.generate(DEFAULT_CELL_NUM, shape);
    }

    @Override
    public Simulator<Integer> simulator() { return simulator; }
}
