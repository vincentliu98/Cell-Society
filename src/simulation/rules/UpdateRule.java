package simulation.rules;

import javafx.scene.paint.Color;
import simulation.Cell;

import java.util.ArrayList;

public interface UpdateRule<T> {
    T nextValue(Cell<T> me, ArrayList<Cell<T>> neighbor);
    Color chooseColor(Cell<T> me);
}
