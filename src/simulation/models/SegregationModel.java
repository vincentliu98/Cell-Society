package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.SegregationWriter;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  SegregationModel implements the Segregation Model.
 * @author Inchan Hwang
 */
public class SegregationModel implements SimulationModel<Integer> {
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    public static final String MODEL_NAME = "Segregation";

    private double satisfactionThreshold;
    public SegregationModel(double threshold) { satisfactionThreshold = threshold; }

    @Override
    public int getPriority(Integer myVal) { return 0; }

    @Override
    public void localUpdate(Cell<Integer> me, List<Cell<Integer>> neighbors) { }

    @Override
    public void globalUpdate(CellGraph<Integer> graph) {
        var leave = new ArrayList<Cell<Integer>>();

        for(var cell : graph.getCells()) {
            if(isLeaving(cell, graph.getNeighbors(cell))) leave.add(cell);
            else cell.setNext(cell.value());
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for(var leaver: leave) colors.add(leaver.value());
        Collections.shuffle(colors);

        int idx = 0;
        for(var leaver: leave) leaver.setNext(colors.get(idx++));
    }

    private boolean isLeaving(Cell<Integer> cell, List<Cell<Integer>> neighbors) {
        int sameCnt = 0, neighborCnt = 0;
        for(var neighbor: neighbors) {
            if(neighbor.value().equals(cell.value())) sameCnt ++;
            if(neighbor.value() != EMPTY) neighborCnt ++;
        }

        return cell.value() == EMPTY || (sameCnt/((double) neighborCnt) < satisfactionThreshold);
    }

    @Override
    public Integer nextValue(Integer myVal) {
        return myVal == EMPTY ? BLUE :
                 myVal == BLUE ? RED : EMPTY;
    }

    @Override
    public Color chooseColor(Integer myVal) {
        return myVal == RED ? Color.RED:
                myVal == BLUE ? Color.BLUE : Color.WHITE;
    }

    @Override
    public String modelName() { return MODEL_NAME; }

    @Override
    public XMLWriter<Integer> getXMLWriter(CellGraph<Integer> graph, File outFile) {
        return new SegregationWriter(this, graph, outFile);
    }

    public double getSatisfactionThreshold() { return satisfactionThreshold; }
}
