package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.SimulationModel;
import simulation.models.SpreadingFireModel;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Convenience class to generateRect "Spreading of Fire" CellGraph
 *
 * @author Vincent Liu
 */

public class SpreadingFire {
    public static final double DEFAULT_PROBCATCH = 0.7;

    /**
     *
     * @param row
     * @param column
     * @param initial
     * @param probCatch
     * @return
     */
    public static Simulator<Integer> generateRect(int row, int column, int[][] initial, double probCatch) {
        SimulationModel<Integer> model = new SpreadingFireModel(probCatch);
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], ShapeUtils.RECTANGLE,
                        (j+0.5)*width, (i+0.5)*height,
                        width, height
                );
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Integer>().graphWith4Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    /**
     *
     * @param n
     * @return
     */
    public static Simulator<Integer> generateRect(int n) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.7 ? SpreadingFireModel.TREE :
                             x < 0.75 ? SpreadingFireModel.BURNING : SpreadingFireModel.EMPTY;
            }
        }
        return SpreadingFire.generateRect(n, n, tmp, DEFAULT_PROBCATCH);
    }
}
