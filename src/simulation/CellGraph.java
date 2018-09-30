package simulation;

import javafx.scene.Node;
import simulation.models.SimulationModel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CellGraph is convenience class to represent a graph of containers with a Map
 *
 * @author Inchan Hwang
 */

public class CellGraph<T> extends HashMap<Cell<T>, List<Cell<T>>> {
    /**
     * @return a Set of the cells
     */
    public Set<Cell<T>> getCells() { return keySet(); }

    /**
     * @param model
     * @return a list of sorted cells
     */
    public List<Cell<T>> getOrderedCells(SimulationModel<T> model) {
        return keySet().stream()
                .sorted(Comparator.comparingInt(c -> model.getPriority(c.value())))
                .collect(Collectors.toList());
    }

    /**
     * @return a set of node to be displayed on user interface
     */
    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }

    /**
     * Get the neighbors of one cell
     *
     * @param cell
     * @return a list of neighbors
     */
    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }
}
