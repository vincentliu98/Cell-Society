package simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import simulation.rules.UpdateRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CellGraph<T> {
    public static final int SIMULATION_SX = 400;
    public static final int SIMULATION_SY = 400;

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

    private void updateAll() { cells.forEach(c -> c.update(rule, neighbors.get(c))); }
    private void commitAll() { cells.forEach(Cell::commit); }
    private void updateView() { cells.forEach(c -> c.updateView(rule)); }

    // TODO: DELETE ONCE DONE TESTING
    public List<Cell<T>> getCells() { return cells; }
}
