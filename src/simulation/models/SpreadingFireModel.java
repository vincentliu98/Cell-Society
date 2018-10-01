package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.SpreadingFireWriter;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.*;

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
    public int updatePriority(Integer myVal) { return 0; }

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
    public Map<String, Integer> getStatistics(List<Integer> values) {
        HashMap<String, Integer> myMap = new HashMap<>();
        int burnNum = 0;
        int treeNum = 0;
        int emptyNum = 0;
        for (Integer a : values) {
            if (a == BURNING) burnNum++;
            else if (a == TREE) treeNum++;
            else emptyNum++;
        }
        myMap.put("Empty", emptyNum);
        myMap.put("Burning", burnNum);
        myMap.put("Tree", treeNum);
        return myMap;
    }

    @Override
    public XMLWriter<Integer> getXMLWriter(CellGraph<Integer> graph, File outFile, String language) {
        return new SpreadingFireWriter(this,graph,outFile, language);
    }

    @Override
    public void updateModelParams(Map<String, String> params) {
        probCatch = Double.parseDouble(params.get(PARAM_CATCHPROB));
    }

    @Override
    public Integer getValFromCode(int code) {
        return code;
    }

    @Override
    public List<Integer> getCodes() {
        return List.of(EMPTY, TREE, BURNING);
    }

    public double getProbCatch() { return probCatch; }
}
