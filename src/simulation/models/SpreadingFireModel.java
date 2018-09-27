package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.SpreadingFireWriter;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SpreadingFireModel implements SimulationModel interface.
 *
 * @author Vincent Liu
 */

public class SpreadingFireModel implements SimulationModel<Integer> {
    public static final int EMPTY = 0;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final String MODEL_NAME = "Spreading Of Fire";
    public static final String PARAM_CATCHPROB = "probCatch";

    private double probCatch;

    public SpreadingFireModel(double probCatch_) {
        probCatch = probCatch_;
    }

    @Override
    public int getPriority(Integer myVal) { return 0; }

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
    public void globalUpdate(CellGraph<Integer> graph) {
        // spreading fire model doesn't require global update
    }

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
    public Map<String, Integer> getStatisitcs(List<Integer> values) {
        return null;
    }

    @Override
    public XMLWriter<Integer> getXMLWriter(CellGraph<Integer> graph, File outFile) {
        return new SpreadingFireWriter(this,graph,outFile);
    }

    @Override
    public void updateParams(Map<String, String> params) {
        probCatch = Double.parseDouble(params.get(PARAM_CATCHPROB));
    }

    public double getProbCatch() { return probCatch; }
}
