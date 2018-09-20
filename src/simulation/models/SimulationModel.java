package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;

import java.util.List;

/**
 *  SimulationModels
 *
 * @param <T> Type of the cell's value for a model
 * @author Inchan Hwang
 */
public interface SimulationModel<T> {
    void localUpdate(Cell<T> me, List<Cell<T>> neighbors);
    void globalUpdate(CellGraph<T> graph);

    T nextValue(T myVal);
    Color chooseColor(T myVal);
    String modelName();
}
