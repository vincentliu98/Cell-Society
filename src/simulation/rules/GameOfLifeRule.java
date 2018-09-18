package simulation.rules;

import javafx.scene.paint.Color;
import simulation.Cell;

import java.util.ArrayList;

public class GameOfLifeRule implements UpdateRule<Integer> {
    public static final int DEAD = 0;
    public static final int ALIVE = 1;

    @Override
    public Integer nextValue(Cell<Integer> me, ArrayList<Cell<Integer>> neighbor) {
        long nLives = neighbor.stream().filter(c -> c.value() == ALIVE).count();
        if(nLives <= 2) return DEAD;
        else if(nLives == 3) return ALIVE;
        else if(nLives == 4) return me.value();
        else return DEAD;
    }

    @Override
    public Color chooseColor(Cell<Integer> me) { return me.value() == DEAD ? Color.WHITE : Color.BLACK; }
}
