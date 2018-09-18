package simulation.rules;

import javafx.scene.paint.Color;

import java.util.List;

/**
 *  UpdateRules for the Game of Life
 */
public class GameOfLifeRule implements UpdateRule<Integer> {
    public static final int DEAD = 0;
    public static final int ALIVE = 1;

    @Override
    public Integer nextValue(Integer myVal, List<Integer> neighborVal) {
        long nLives = neighborVal.stream().filter(c -> c == ALIVE).count();
        if(nLives <= 2) return DEAD;
        else if(nLives == 3) return ALIVE;
        else if(nLives == 4) return myVal;
        else return DEAD;
    }

    @Override
    public Color chooseColor(Integer myVal) { return myVal == DEAD ? Color.WHITE : Color.BLACK; }
}
