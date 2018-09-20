package simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import simulation.models.SimulationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CellGraph class holds
 * 1. all the cells
 * 2. each of their neighborhoods
 * 3. a instance of an SimulationModel
 *
 * It applies necessary / updates / beforeCommit actions / commits / updateViews to all cells
 *
 * @param <T> Type of the Cell's value
 * @author Inchan Hwang
 */
public class CellGraph<T> {
    public static final double SIMULATION_SX = 529.5;
    public static final double SIMULATION_SY = 435;

    protected List<Cell<T>> cells;
    private Map<Cell<T>, List<Cell<T>>> neighbors;
    protected SimulationModel<T> model;
    private Group view;
    private int tickCount;

    public CellGraph(
        List<Cell<T>> cells_,
        Map<Cell<T>, List<Cell<T>>> neighbors_,
        SimulationModel<T> model_
    ) {
        cells = cells_;
        neighbors = neighbors_;
        model = model_;
        view = new Group();
        cells.forEach(c -> view.getChildren().add(c.view()));
        cells.forEach(c -> c.view().setOnMouseClicked(e -> c.handleClick(model)));
        tickCount = 0;
    }

    public void tick() {
        tickCount ++;
        updateAll();
        beforeCommit();
        commitAll();
        updateView();
    }
    public Node view() { return view; }
    public int tickCount() { return tickCount; }
    public void setUpdateRule(SimulationModel<T> model_) {
        // TODO: We might need to update the clickHandler's model as well.
        model = model_;
    }
    public String modelName() { return model.modelName(); }

    private void updateAll() { cells.forEach(c -> c.update(model, extractValue(c))); }
    private void beforeCommit() { model.beforeCommit(cells); }
    private void commitAll() { cells.forEach(Cell::commit); }
    private void updateView() { cells.forEach(c -> c.updateView(model)); }
    private ArrayList<T> extractValue(Cell<T> cell) {
        var ret = new ArrayList<T>();
        neighbors.get(cell).forEach(c -> ret.add(c.value()));
        return ret;
    }
}
