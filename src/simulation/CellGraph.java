package simulation;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import simulation.models.SimulationModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CellGraph is convenience class to represent a graph of containers with a Map
 * @author Inchan Hwang
 */
public class CellGraph<T> extends HashMap<Cell<T>, List<Cell<T>>> {
    private Shape shape;
    private boolean dirty;
    private List<Cell<T>> cachedList;
    public CellGraph(Shape shape_) { shape = shape_; dirty = true; cachedList = new ArrayList<>(); }

    @Override
    public List<Cell<T>> put(Cell<T> cell, List<Cell<T>> neighbors) {
        dirty = true;
        cell.setShape(Shape.subtract(shape, new Rectangle(0,0)));
        return super.put(cell, neighbors);
    }

    public Set<Cell<T>> getCells() { return keySet(); }
    public List<Cell<T>> getOrderedCells(SimulationModel<T> model) {
        if(dirty) cachedList =
                keySet().stream()
                        .sorted(Comparator.comparingInt(c -> model.getPriority(c.value())))
                        .collect(Collectors.toList());
        dirty = false;
        return cachedList;
    }
    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }
    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }
    public double getShapeWidth() { return shape.getLayoutBounds().getWidth(); }
    public double getShapeHeight() { return shape.getLayoutBounds().getHeight(); }
}
