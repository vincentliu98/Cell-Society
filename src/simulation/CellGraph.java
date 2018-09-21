package simulation;

import javafx.scene.Node;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CellGraph is convenience class to represent a graph of containers with a Map
 * @author Inchan Hwang
 */
public class CellGraph<T> extends HashMap<Cell<T>, List<Cell<T>>> {
    // field names expected to appear in data file holding values for this object
    public static final List<String> DATA_FIELDS = List.of(
            "type",
            "cellArrayList"

    );
    //field names expected to appear within each cell
    public static final List<String> CELL_SUBFIELDS = List.of(
            "uniqueID",
            "neighbors",
            "x",
            "y",
            "values"
    );

    public Set<Cell<T>> getCells() { return keySet(); }
    public Set<Cell<T>> getCells(Predicate<Cell<T>> p) {
        return getCells().stream().filter(p).collect(Collectors.toSet());
    }
    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }
    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }
    public List<Cell<T>> getNeighbors(Cell<T> cell, Predicate<Cell<T>> p) {
        return get(cell).stream().filter(p).collect(Collectors.toList());
    }
}
