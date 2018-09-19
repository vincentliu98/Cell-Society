package simulation.rules;

import javafx.scene.paint.Color;

import java.util.List;

/**
 *  UpdateRules for the Spreading Of Fire
 *
 * @author Vincent Liu
 */

public class SpreadingFireRule implements UpdateRule<Integer> {
    public static final int EMPTY = 0;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final String MODEL_NAME = "Spreading Of Fire";
    private double probCatch = 0.9;

    @Override
    public Integer nextValue(Integer myVal, List<Integer> neighborVal) {
        if (myVal == TREE && (neighborVal.stream().filter(a -> a == BURNING).count() > 0)){
            return Math.random() < probCatch ? BURNING : TREE;
        }
        else if (myVal == EMPTY) return myVal;
        else if (myVal == BURNING) return EMPTY;
        else return TREE;
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
    public String modelName() { return MODEL_NAME; }

}
