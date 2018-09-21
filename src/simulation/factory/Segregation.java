package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.SegregationModel;
import utility.IntegerPair;

import java.util.ArrayList;

/**
 *  Convenience class to generate "Segregation" Simulator
 *  @author Inchan Hwang
 */
public class Segregation {
    public static Simulator<IntegerPair> generate(int row, int column, int[][] initial, double tolerance) {
        var model = new SegregationModel(tolerance);
        ArrayList<Cell<IntegerPair>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = new IntegerPair(SegregationModel.STAY, initial[i][j]);
                var cell = new Cell<>(value, (j+0.5)*width, (i+0.5)*height);
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<IntegerPair>().graphWith8Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    public static Simulator<IntegerPair> generate() {
        return Segregation.generate(5, 5, new int[][]{
                {1, 2, 2, 2, 1},
                {2, 0, 0, 0, 2},
                {2, 0, 2, 0, 2},
                {2, 0, 0, 0, 2},
                {1, 2, 2, 2, 1}
        }, 0.3);
    }
}
