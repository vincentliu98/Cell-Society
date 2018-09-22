package simulation;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
    private Shape shape;
    public CellGraph(Shape shape_) { shape = shape_; }

    @Override
    public List<Cell<T>> put(Cell<T> cell, List<Cell<T>> neighbors) {
        cell.setShape(Shape.subtract(shape, new Rectangle(0,0)));
        return super.put(cell, neighbors);
    }

    public Set<Cell<T>> getCells() { return keySet(); }
    public List<Cell<T>> getOrderedCells(SimulationModel<T> model) {
        return keySet()
                .stream()
                .sorted(Comparator.comparingInt(c -> model.getPriority(c.value())))
                .collect(Collectors.toList()); // TODO: Should cache this or something by setting dirty bit on put().
    }
    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }
    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }
    public double getShapeWidth() { return shape.getLayoutBounds().getWidth(); }
    public double getShapeHeight() { return shape.getLayoutBounds().getHeight(); }
}
