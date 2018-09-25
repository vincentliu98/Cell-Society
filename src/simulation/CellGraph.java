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
    public CellGraph(Shape shape_) { shape = shape_; }

    /**
     *
     * @param cell
     * @param neighbors
     * @return
     */
    @Override
    public List<Cell<T>> put(Cell<T> cell, List<Cell<T>> neighbors) {
        cell.setShape(Shape.subtract(shape, new Rectangle(0,0)));
        return super.put(cell, neighbors);
    }

    /**
     *
     * @return
     */
    public Set<Cell<T>> getCells() { return keySet(); }

    /**
     *
     * @param model
     * @return
     */
    public List<Cell<T>> getOrderedCells(SimulationModel<T> model) {
        return keySet().stream()
                .sorted(Comparator.comparingInt(c -> model.getPriority(c.value())))
                .collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    public Set<Node> getViews() { return getCells().stream().map(Cell::view).collect(Collectors.toSet()); }

    /**
     *
     * @param cell
     * @return
     */
    public List<Cell<T>> getNeighbors(Cell<T> cell) { return get(cell); }

    /**
     *
     * @return
     */
    public double getShapeWidth() { return shape.getLayoutBounds().getWidth(); }

    /**
     *
     * @return
     */
    public double getShapeHeight() { return shape.getLayoutBounds().getHeight(); }
}
