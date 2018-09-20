package simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import simulation.models.SimulationModel;

/**
 * Simulator
 * @param <T> Type of the Cell's value
 * @author Inchan Hwang
 */
public class Simulator<T> {
    public static final double SIMULATION_SX = 529.5;
    public static final double SIMULATION_SY = 435;

    private CellGraph<T> graph;
    protected SimulationModel<T> model;

    private Group view;
    private int tickCount;

    public Simulator(CellGraph<T> graph_, SimulationModel<T> model_) {
        graph = graph_; model = model_;
        view = new Group(graph.getViews());
        tickCount = 0;
    }

    public void tick() {
        tickCount ++;
        localUpdate();
        globalUpdate();
        commitAll();
        updateView();
    }

    public Node view() { return view; }
    public int tickCount() { return tickCount; }
    public void setSimulationModel(SimulationModel<T> model_) { model = model_; }
    public String modelName() { return model.modelName(); }

    private void localUpdate() { graph.getCells().forEach(c -> c.localUpdate(model, graph.getNeighbors(c))); }
    private void globalUpdate() { model.globalUpdate(graph); }
    private void commitAll() { graph.getCells().forEach(Cell::commit); }
    private void updateView() { graph.getCells().forEach(c -> c.updateView(model)); }
}
