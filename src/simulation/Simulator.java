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
 * Simulator
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
     *
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
     * @return
     */
    public Node view() {
        graph.getCells().forEach(c -> {

        });
        return view;
    }

    /**
     *
     * @return
     */
    public int tickCount() { return tickCount; }

    /**
     *
     * @return
     */
    public String modelName() { return model.modelName(); }

    /**
     *
     * @param outFile
     * @return
     */
    public XMLWriter<T> getWriter(File outFile) { return model.getXMLWriter(graph, outFile); }

    /**
     *
     */
    private void localUpdate() {
        graph.getOrderedCells(model).forEach(c -> model.localUpdate(c, graph.getNeighbors(c)));
    }

    /**
     *
     */
    private void globalUpdate() { model.globalUpdate(graph); }

    /**
     *
     */
    private void commitAll() { for(var c: graph.getCells()) c.commit(); }

    /**
     *
     */
    private void updateView() { graph.getCells().forEach(c -> c.updateView(model)); }

    /**
     *
     * @param params
     */
    public void updateSimulationModel(Map<String, String> params) { model.updateParams(params); }
}
