package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.List;

/**
 *  UpdateRules for the Spreading Of Fire
 *
 * @author Vincent Liu
 */

public class SpreadingFireModel implements SimulationModel<Integer> {
    public static final int EMPTY = 0;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final String MODEL_NAME = "Spreading Of Fire";
    private double probCatch = 0.9;

    @Override
    public void localUpdate(Cell<Integer> me, List<Cell<Integer>> neighbors) {
        if (me.value() == TREE && (neighbors.stream().anyMatch(a -> a.value() == BURNING))){
            me.setNext(Math.random() < probCatch ? BURNING : TREE);
        }
        else if (me.value() == EMPTY) me.setNext(me.value());
        else if (me.value() == BURNING) me.setNext(EMPTY);
        else me.setNext(TREE);
    }

    @Override
    public void globalUpdate(CellGraph<Integer> graph) { }

    @Override
    public Integer nextValue(Integer myVal) {
        return myVal == EMPTY ? TREE :
                myVal == TREE ? BURNING : EMPTY;
    }

    @Override
    public Color chooseColor(Integer myVal) {
        return myVal == EMPTY ? Color.YELLOW :
                myVal == TREE ? Color.GREEN : Color.RED;
    }

    @Override
    public String modelName() { return MODEL_NAME; }

    @Override
    public XMLWriter<Integer> getXMLWriter(CellGraph<Integer> graph, File outFile) {
        return null; // TODO: IMPLEMENT!!!
    }

}
