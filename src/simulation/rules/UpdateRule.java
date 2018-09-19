package simulation.rules;

import javafx.scene.paint.Color;
import simulation.Cell;

import java.util.List;

/**
 *  UpdateRules have to define
 *  nextValue() which determine one's next value based on itself and its neighbors
 *  chooseColor() which determine one's color based on its value
 * @param <T>
 */
public interface UpdateRule<T> {
    T nextValue(T myVal, List<T> neighborVal);
    Color chooseColor(T myVal);
    void beforeCommit(List<Cell<T>> cells);
    String modelName();
}
