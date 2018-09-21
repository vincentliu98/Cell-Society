package simulation;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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
    private Shape shape;
    public CellGraph(Shape shape_) { shape = shape_; }

    @Override
    public List<Cell<T>> put(Cell<T> cell, List<Cell<T>> neighbors) {
        cell.setShape(Shape.subtract(shape, new Rectangle(0,0)));
        return super.put(cell, neighbors);
    }
    public Set<Cell<T>> getCells() { return keySet(); }
    public Set<Cell<T>> getCells(Predicate<Cell<T>> p) {
        return getCells().stream().filter(p).collect(Collectors.toSet());
    }
    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }
    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }
    public double getShapeWidth() { return shape.getLayoutBounds().getWidth(); }
    public double getShapeHeight() { return shape.getLayoutBounds().getHeight(); }
}
