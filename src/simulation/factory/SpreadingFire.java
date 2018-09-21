package simulation.factory;

import javafx.scene.shape.Rectangle;
import simulation.Cell;
import simulation.Simulator;
import simulation.models.SimulationModel;
import simulation.models.SpreadingFireModel;

import java.util.ArrayList;

/**
 *  Convenience class to generate "Spreading of Fire" CellGraph
 *
 * @author Vincent Liu
 */

public class SpreadingFire {
    public static Simulator<Integer> generate(int row, int column, int[][] initial) {
        SimulationModel<Integer> model = new SpreadingFireModel();
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j];
                var cell = new Cell<>(value, model,
                        new Rectangle(width, height, model.chooseColor(value)), (j+0.5)*width, (i+0.5)*height);
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Integer>().graphWith4Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Integer> generate() {
        return SpreadingFire.generate(5, 5, new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 2, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        });
    }
}
