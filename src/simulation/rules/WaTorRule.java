package simulation.rules;

import javafx.scene.paint.Color;
import simulation.Cell;

import java.util.List;

/**
 *  UpdateRules for the Spreading Of Fire
 *
 * @author Vincent Liu
 */

public class WaTorRule implements UpdateRule<Integer> {
    public static final int EMPTY = 0;
    public static final int FISH = 1;
    public static final int SHARK = 2;

    public static final String MODEL_NAME = "Wa-Tor";

    @Override
    public Integer nextValue(Integer myVal, List<Integer> neighborVal) {
        if (myVal == SHARK) {
            return 0;
        } else if (myVal == FISH) {
            return 0;
        } else return 0;
    }

    @Override
    public Color chooseColor(Integer myVal) {
        return null;
    }

    @Override
    public void beforeCommit(List<Cell<Integer>> cells) { }

    @Override
    public String modelName() { return MODEL_NAME; }

}
