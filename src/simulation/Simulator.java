package simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import simulation.models.SimulationModel;
import xml.writer.XMLWriter;

import java.io.File;

/**
 * Simulator
 * @param <T> Type of the Cell's value
 * @author Inchan Hwang
 */
public class Simulator<T> {
    public static final double SIMULATION_SX = 529.5;
    public static final double SIMULATION_SY = 432.5;


    private CellGraph<T> graph;
    protected SimulationModel<T> model;

    private Group view;
    private int tickCount;

    public Simulator(CellGraph<T> graph_, SimulationModel<T> model_) {
        graph = graph_; model = model_;
        graph.getCells().forEach(c -> {
            c.view().setOnMouseClicked(e -> c.handleClick(model));
            c.updateView(model);
        });
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
    public String modelName() { return model.modelName(); }
    public XMLWriter<T> getWriter(File outFile) { return model.getXMLWriter(graph, outFile); }

    private void localUpdate() {
        graph.getOrderedCells(model).forEach(c -> model.localUpdate(c, graph.getNeighbors(c)));
    }
    private void globalUpdate() { model.globalUpdate(graph); }
    private void commitAll() { for(var c: graph.getCells()) c.commit(); }
    private void updateView() { graph.getCells().forEach(c -> c.updateView(model)); }
    public void setSimulationModel(SimulationModel<T> model_) { model = model_; }
}
