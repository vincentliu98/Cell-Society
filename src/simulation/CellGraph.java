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
 * @author Inchan Hwang
 */

public class CellGraph<T> extends HashMap<Cell<T>, List<Cell<T>>> {
    public Set<Cell<T>> getCells() { return keySet(); }

    /**
     * returns cells, sorted according to model's priority
     */
    public List<Cell<T>> getOrderedCells(SimulationModel<T> model) {
        return keySet().stream()
                .sorted(Comparator.comparingInt(c -> model.updatePriority(c.value())))
                .collect(Collectors.toList());
    }

    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }

    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }
}
