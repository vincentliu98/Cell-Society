package simulation;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import simulation.models.SimulationModel;
import utility.ColorUtils;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.Map;

import static javafx.scene.shape.StrokeType.INSIDE;

/**
 * A simulator that display and update the cells from the CellGraph
 *
 * @param <T> Type of the Cell's value
 * @author Inchan Hwang
 */
public class Simulator<T> {
    public static final double SIMULATION_SX = 636;
    public static final double SIMULATION_SY = 579;

    private CellGraph<T> graph;
    protected SimulationModel<T> model;

    private Pane view;
    private int tickCount;

    public Simulator(CellGraph<T> graph_, SimulationModel<T> model_) {
        graph = graph_; model = model_;
        graph.getCells().forEach(c -> {
            var v = c.view();
            v.setStrokeType(INSIDE);

            v.setOnMouseEntered(e -> {
                v.toFront();
                v.setFill(ColorUtils
                        .mix(model.chooseColor(c.value()), model.chooseColor(model.nextValue(c.value())), 0.1));
                v.setStroke(model.chooseColor(c.value()));
                v.setStrokeDashOffset(5);
                v.getStrokeDashArray().addAll(10d);
                v.setOpacity(0.7);
            });

            v.setOnMouseExited(e -> {
                v.setFill(model.chooseColor(c.value()));
                v.setStroke(new Color(0,0,0,0));
                v.setOpacity(1);
            });
            v.setOnMouseClicked(e -> {
                c.handleClick(model);
                v.getOnMouseEntered().handle(e);
            });
            c.updateView(model);
        });
        view = new Pane();
        graph.getViews().forEach(view.getChildren()::add);

        tickCount = 0;
    }

    /**
     * Increment the count of the tick and update the cells
     */
    public void tick() {
        tickCount ++;
        localUpdate();
        globalUpdate();
        commitAll();
        updateView();
    }

    /**
     *
     * @return the graph consisting cells as a Node
     */
    public Node view() {
        graph.getCells().forEach(c -> {

        });
        return view;
    }

    /**
     *
     * @return current count of the rounds that passed
     */
    public int tickCount() { return tickCount; }

    /**
     * Get the model name
     *
     * @return the name of the model
     */
    public String modelName() { return model.modelName(); }

    /**
     * Call the corresponding XML writer for each model to generateRect XML file
     *
     * @param outFile
     * @return an XML file that will be saved into user's directory
     */
    public XMLWriter<T> getWriter(File outFile) { return model.getXMLWriter(graph, outFile); }

    /**
     * Update all the cells' nextVal in the simulator
     */
    private void localUpdate() {
        graph.getOrderedCells(model).forEach(c -> model.localUpdate(c, graph.getNeighbors(c)));
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    private void globalUpdate() { model.globalUpdate(graph); }

    /**
     * Replace the current value with the value in the next round
     */
    private void commitAll() { for(var c: graph.getCells()) c.commit(); }

    /**
     * Change the color of the cells on the simulator according to the new value
     */
    private void updateView() { graph.getCells().forEach(c -> c.updateView(model)); }

    /**
     * Update the parameters with the new value passed from the UI's ModelPanel
     *
     * @param params
     */
    public void updateSimulationModel(Map<String, String> params) { model.updateParams(params); }
}
