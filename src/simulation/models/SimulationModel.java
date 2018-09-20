package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;

import java.util.List;

/**
 *  SimulationModels have to define
 *
 *  nextValue(myVal, neighborVal) which implements update rules for a model,
 *  updating one's next value based on itself and its neighbors.
 *
 *  nextValue(myVal) which determines the next possible value (for GUI modification on cells).
 *  chooseColor() which determine one's color based on its value.
 *  beforeCommit() which applis global-effects that happen based on nextValue().
 *  (i.e. moving cells around on Segregation model)
 *
 * @param <T> Type of the cell's value for a model
 * @author Inchan Hwang
 */
public interface SimulationModel<T> {
    T nextValue(T myVal, List<T> neighborVal);
    T nextValue(T myVal);
    Color chooseColor(T myVal);
    void beforeCommit(List<Cell<T>> cells);
    String modelName();
}
