package simulation;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import simulation.models.SimulationModel;
import utility.ColorUtils;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javafx.scene.shape.StrokeType.INSIDE;
import static javafx.scene.shape.StrokeType.OUTSIDE;

/**
 * Simulator
 * @param <T> Type of the Cell's value
 * @author Inchan Hwang
 */
public class Simulator<T> {
    public static final double SIMULATION_SX = 525;
    public static final double SIMULATION_SY = 430;

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
            });

            v.setOnMouseExited(e -> {
                v.setFill(model.chooseColor(c.value()));
                v.setStroke(new Color(0,0,0,0));
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

    public void tick() {
        tickCount ++;
        localUpdate();
        globalUpdate();
        commitAll();
        updateView();
    }

    public Node view() {
        graph.getCells().forEach(c -> {

        });
        return view;
    }
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
