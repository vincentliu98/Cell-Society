package simulation.factory;

import javafx.util.Pair;
import simulation.Cell;
import simulation.Simulator;
import simulation.models.SegregationModel;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Convenience class to generate "Segregation" Simulator
 *  @author Inchan Hwang
 */
public class Segregation {
    public static final double DEFAULT_THRESHOLD = 0.3;

    public static Simulator<Integer> generateTri(
            int row, int column,
            int[][] initial, double threshold,
            List<Pair<Integer, Integer>> indices
    ) {
        var model = new SegregationModel(threshold);
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / ((column+1)/2);
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], (i+j)%2==0 ? ShapeUtils.TRIANGLE : ShapeUtils.TRIANGLE_FLIP,
                        (0.5*j)*width, (i+0.5)*height,
                        width, height
                );
                cells.add(cell);
            }
        }

        var graph = NeighborUtils.triangularGraph(cells, row, column, indices);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Integer> generateRect(
            int row, int column,
            int[][] initial, double threshold,
            List<Pair<Integer, Integer>> indices
    ) {
        var model = new SegregationModel(threshold);
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

        var graph = NeighborUtils.rectangularGraph(cells, row, column, indices);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Integer> generate(int n, String shape, List<Pair<Integer, Integer>> indices) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.4 ? 0 :
                            x < 0.7 ? 1 : 2;
            }
        }

        if (shape.equals(ShapeUtils.RECTANGULAR)) return Segregation.generateRect(
                n, n, tmp, DEFAULT_THRESHOLD,
                indices == null ? NeighborUtils.indicesFor8Rectangle() : indices);
        else if (shape.equals(ShapeUtils.TRIANGULAR)) return Segregation.generateTri(
                n, n, tmp, DEFAULT_THRESHOLD,
                indices == null ? NeighborUtils.indicesFor12Triangle() : indices);
        else return null;
    }

    public static Simulator<Integer> generate(int n, String shape) { return generate(n, shape, null); }
}
