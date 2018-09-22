package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.GameOfLifeWriter;

import java.io.File;
import java.util.List;

/**
 *  UpdateRules for the Game of Life
 *  @author Inchan Hwang
 */
public class GameOfLifeModel implements SimulationModel<Integer> {
    public static final int DEAD = 0;
    public static final int ALIVE = 1;
    public static final String MODEL_NAME = "Game Of Life";

    @Override
    public void localUpdate(Cell<Integer> me, List<Cell<Integer>> neighbors) {
        long nLives = neighbors.stream().filter(c -> c.value() == ALIVE).count();
        me.setNext( me.value() == ALIVE ?
                        (nLives < 2 ? DEAD :
                          nLives < 4 ? ALIVE : DEAD) :
                        (nLives == 3 ? ALIVE : DEAD));
    }

    @Override
    public void globalUpdate(CellGraph<Integer> graph) { }

    @Override
    public Integer nextValue(Integer myVal) { return myVal == ALIVE ? DEAD : ALIVE; }

    @Override
    public Color chooseColor(Integer myVal) { return myVal == DEAD ? Color.WHITE : Color.BLACK; }

    @Override
    public String modelName() { return MODEL_NAME; }

    @Override
    public GameOfLifeWriter getXMLWriter(CellGraph<Integer> graph, File outFile) {
        return new GameOfLifeWriter(graph, outFile);
    }
}
