package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;

import java.util.List;

/**
 *  UpdateRules for the Spreading Of Fire
 *
 * @author Vincent Liu
 */

public class SpreadingFireModel implements SimulationModel<Integer> {
    public static final int EMPTY = 0;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final String MODEL_NAME = "Spreading Of Fire";
    private double probCatch = 0.9;

    @Override
    public Integer nextValue(Integer myVal, List<Integer> neighborVal) {
        if (myVal == TREE && (neighborVal.stream().anyMatch(a -> a == BURNING))){
            return Math.random() < probCatch ? BURNING : TREE;
        }
        else if (myVal == EMPTY) return myVal;
        else if (myVal == BURNING) return EMPTY;
        else return TREE;
    }

    @Override
    public Integer nextValue(Integer myVal) {
        return myVal == EMPTY ? TREE :
                myVal == TREE ? BURNING :
                  myVal == BURNING ? EMPTY : EMPTY;
    }

    @Override
    public Color chooseColor(Integer myVal) {
        switch(myVal){
            case EMPTY:
                return Color.YELLOW;
            case TREE:
                return Color.GREEN;
            case BURNING:
                return Color.RED;
        }
        return null;
    }

    @Override
    public void beforeCommit(List<Cell<Integer>> cells) { }

    @Override
    public String modelName() { return MODEL_NAME; }

}
