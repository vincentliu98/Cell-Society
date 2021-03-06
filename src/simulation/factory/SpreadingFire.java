package simulation.factory;

import javafx.util.Pair;
import simulation.Cell;
import simulation.Simulator;
import simulation.models.SimulationModel;
import simulation.models.SpreadingFireModel;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Convenience class to generate "Spreading of Fire" Simulator.
 *
 * @author Vincent Liu
 */

public class SpreadingFire {
    private static double MARGIN = 0.5;
    public static final double DEFAULT_PROBCATCH = 0.7;

    public static Simulator<Integer> generateTri(
            int row, int column,
            int[][] initial, double probCatch,
            List<Pair<Integer, Integer>> indices
    ) {
        var model = new SpreadingFireModel(probCatch);
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / ((column+1)/2);
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], (i+j)%2==0 ? ShapeUtils.TRIANGLE : ShapeUtils.TRIANGLE_FLIP,
                        (MARGIN*j)*width, (i+MARGIN)*height,
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
            int[][] initial, double probCatch,
            List<Pair<Integer, Integer>> indices
    ) {
        SimulationModel<Integer> model = new SpreadingFireModel(probCatch);
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], ShapeUtils.RECTANGLE,
                        (j+MARGIN)*width, (i+MARGIN)*height,
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
                tmp[i][j] = x < 0.7 ? SpreadingFireModel.TREE :
                        x < 0.75 ? SpreadingFireModel.BURNING : SpreadingFireModel.EMPTY;
            }
        }

        if (shape.equals(ShapeUtils.RECTANGULAR)) return SpreadingFire.generateRect(
                n, n, tmp, DEFAULT_PROBCATCH,
                indices == null ?  NeighborUtils.indicesFor4Rectangle() : indices);
        else if (shape.equals(ShapeUtils.TRIANGULAR)) return SpreadingFire.generateTri(
                n, n, tmp, DEFAULT_PROBCATCH,
                indices == null ?  NeighborUtils.indicesFor12Triangle() : indices);
        else return null;
    }

    public static Simulator<Integer> generate(int n, String shape) { return generate(n, shape, null); }
}
