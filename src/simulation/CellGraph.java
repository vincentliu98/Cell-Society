package simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import simulation.rules.UpdateRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CellGraph class holds
 * 1. all the cells
 * 2. each of their neighborhoods
 * 3. a instance of an UpdateRule
 *
 * It applies necessary updates / commits / updateViews to all cells
 *
 * One might override the step() function to implement effects that happen
 * after the default steps.
 *
 * @param <T> Type of the Cell's value
 */
public class CellGraph<T> {
    public static final double SIMULATION_SX = 529.5;
    public static final double SIMULATION_SY = 435;

    protected List<Cell<T>> cells;
    private Map<Cell<T>, ArrayList<Cell<T>>> neighbors;
    protected UpdateRule<T> rule;
    private Group view;

    public CellGraph(
        List<Cell<T>> cells_,
        Map<Cell<T>, ArrayList<Cell<T>>> neighbors_,
        UpdateRule<T> rule_
    ) {
        cells = cells_;
        neighbors = neighbors_;
        rule = rule_;
        view = new Group();
        cells.forEach(c -> view.getChildren().add(c.view()));
    }

    public void tick() {
        updateAll();
        commitAll();
        updateView();
    }
    public Node view() { return view; }

    private void updateAll() { cells.forEach(c -> c.update(rule, extractValue(c))); }
    private void commitAll() { cells.forEach(Cell::commit); }
    private void updateView() { cells.forEach(c -> c.updateView(rule)); }
    private ArrayList<T> extractValue(Cell<T> cell) {
        var ret = new ArrayList<T>();
        neighbors.get(cell).forEach(c -> ret.add(c.value()));
        return ret;
    }
}
