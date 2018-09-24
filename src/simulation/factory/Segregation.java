package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.SegregationModel;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Convenience class to generate "Segregation" Simulator
 *  @author Inchan Hwang
 */
public class Segregation {
    public static final double DEFAULT_THRESHOLD = 0.3;

    public static Simulator<Integer> generate(int row, int column, int[][] initial, double threshold) {
        var model = new SegregationModel(threshold);
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], (j+0.5)*width, (i+0.5)*height);
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Integer>().graphWith8Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Integer> generate(int n) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.4 ? 0 :
                            x < 0.7 ? 1 : 2;
            }
        }
        return generate(n, n, tmp, DEFAULT_THRESHOLD);
    }
}
