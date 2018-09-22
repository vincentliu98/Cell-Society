package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.SimulationModel;
import simulation.models.SpreadingFireModel;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Convenience class to generate "Spreading of Fire" CellGraph
 *
 * @author Vincent Liu
 */

public class SpreadingFire {
    public static Simulator<Integer> generate(int row, int column, int[][] initial) {
        SimulationModel<Integer> model = new SpreadingFireModel(0.7);
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j];
                var cell = new Cell<>(value, (j+0.5)*width, (i+0.5)*height);
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Integer>().graphWith4Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Integer> generate(int n) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.7 ? SpreadingFireModel.TREE :
                             x < 0.75 ? SpreadingFireModel.BURNING : SpreadingFireModel.EMPTY;
            }
        }
        return SpreadingFire.generate(n, n, tmp);
    }
}
