package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import utility.IntegerPair;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  SegregationModel implements Segregation Model.
 *  The integer pair represents (STAY/LEAVE, RED/BLUE)
 * @author Inchan Hwang
 */
public class SegregationModel implements SimulationModel<IntegerPair> {
    public static final int STAY = 0;
    public static final int LEAVE = 1;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;

    public static final String MODEL_NAME = "Segregation";

    private double tolerance;
    public SegregationModel(double tolerance_) { tolerance = tolerance_; }

    @Override
    public void localUpdate(Cell<IntegerPair> me, List<Cell<IntegerPair>> neighbors) {
        long nDiff = neighbors.stream().filter(c ->
                c.value().getValue() > 0 && (!c.value().getValue().equals(me.value().getValue()))).count();
        me.setNext(new IntegerPair(
                (me.value().getValue() == EMPTY || (nDiff/((double) neighbors.size()) > tolerance)) ? LEAVE : STAY,
                me.value().getValue()));
    }

    @Override
    public void globalUpdate(CellGraph<IntegerPair> graph) {
        var leavers = graph.getCells(c -> c.next().getKey() == LEAVE);
        ArrayList<Integer> colors = new ArrayList<>();
        for(var leaver: leavers) colors.add(leaver.next().getValue());
        Collections.shuffle(colors);

        int idx = 0;
        for(var leaver: leavers) {
            leaver.setNext(new IntegerPair(leaver.next().getKey(), colors.get(idx++)));
        }
    }

    @Override
    public IntegerPair nextValue(IntegerPair myVal) {
        return new IntegerPair(myVal.getKey(),
                myVal.getValue() == EMPTY ? BLUE :
                 myVal.getValue() == BLUE ? RED : EMPTY);
    }

    @Override
    public Color chooseColor(IntegerPair myVal) {
        return myVal.getValue() == RED ? Color.RED:
                myVal.getValue() == BLUE ? Color.BLUE : Color.WHITE;
    }

    @Override
    public String modelName() { return MODEL_NAME; }

    @Override
    public XMLWriter<IntegerPair> getXMLWriter(CellGraph<IntegerPair> graph, File outFile) {
        return null; // TODO: Implement!!
    }
}
