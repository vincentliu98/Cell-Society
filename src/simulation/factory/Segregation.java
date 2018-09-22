package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.SegregationModel;

import java.util.ArrayList;

/**
 *  Convenience class to generate "Segregation" Simulator
 *  @author Inchan Hwang
 */
public class Segregation {
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

    public static Simulator<Integer> generate() {
        return Segregation.generate(5, 5, new int[][]{
                {1, 2, 2, 2, 1},
                {2, 0, 0, 0, 2},
                {2, 0, 2, 0, 2},
                {2, 0, 0, 0, 2},
                {1, 2, 2, 2, 1}
        }, 0.6);
    }
}
