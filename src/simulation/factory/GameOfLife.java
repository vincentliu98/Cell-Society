package simulation.factory;

import javafx.util.Pair;
import simulation.Cell;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Convenience class to generate "Game Of Life" Simulator
 *  @author Inchan Hwang
 */
public class GameOfLife {
    private static double MARGIN = 0.5;

    /**
     * @param row
     * @param column
     * @param initial
     * @return
     */
    public static Simulator<Integer> generateTri(int row, int column,
                                                 int[][] initial, List<Pair<Integer, Integer>> indices) {
        var model = new GameOfLifeModel();
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

    /**
     * @param row
     * @param column
     * @param initial
     * @return
     */
    public static Simulator<Integer> generateRect(int row, int column,
                                                  int[][] initial, List<Pair<Integer, Integer>> indices) {
        var model = new GameOfLifeModel();
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

    /**
     *
     * @param n
     * @return
     */
    public static Simulator<Integer> generate(int n, String shape, List<Pair<Integer, Integer>> indices) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.5 ? 0 : 1;
            }
        }

        if (shape.equals(ShapeUtils.RECTANGULAR)) return GameOfLife.generateRect(
                n, n, tmp,
                indices == null ? NeighborUtils.indicesFor8Rectangle() : indices);
        else if (shape.equals(ShapeUtils.TRIANGULAR)) return GameOfLife.generateTri(
                n, n, tmp,
                indices == null ? NeighborUtils.indicesFor12Triangle() : indices);
        else return null;
    }

    public static Simulator<Integer> generate(int n, String shape) { return generate(n, shape, null); }
}
