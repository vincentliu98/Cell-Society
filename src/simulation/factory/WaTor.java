package simulation.factory;

import javafx.util.Pair;
import simulation.Cell;
import simulation.Simulator;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Convenience class to generate "Wa-Tor" Simulator.
 *
 * @author Inchan Hwang
 */

public class WaTor {
    private static double MARGIN = 0.5;
    public static final int DEFAULT_FISHBREED_PERIOD = 4;
    public static final int DEFAULT_SHARKBREED_PERIOD = 5;
    public static final int DEFAULT_SHARKSTARVE_PERIOD = 5;

    public static Simulator<Fish> generateTri(
            int row, int column, int[][] initial,
            int fishBreedPeriod, int sharkBreedPeriod, int sharkStarvePeriod,
            List<Pair<Integer, Integer>> indices
    ) {
        var model = new WaTorModel(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
        ArrayList<Cell<Fish>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / ((column+1)/2);
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j] == WaTorModel.FISH ? new Fish() :
                        initial[i][j] == WaTorModel.SHARK ? new Shark() : null;
                var cell = new Cell<>(value, (i+j)%2==0 ? ShapeUtils.TRIANGLE : ShapeUtils.TRIANGLE_FLIP,
                        (MARGIN*j)*width, (i+MARGIN)*height,
                        width, height
                );
                cells.add(cell);
            }
        }

        var graph = NeighborUtils.triangularGraphWrap(cells, row, column, indices);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Fish> generateRect(
            int row, int column, int[][] initial,
            int fishBreedPeriod, int sharkBreedPeriod, int sharkStarvePeriod,
            List<Pair<Integer, Integer>> indices
    ) {
        var model = new WaTorModel(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
        ArrayList<Cell<Fish>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j] == WaTorModel.FISH ? new Fish() :
                         initial[i][j] == WaTorModel.SHARK ? new Shark() : null;
                var cell = new Cell<>(value, ShapeUtils.RECTANGLE,
                        (j+MARGIN)*width, (i+MARGIN)*height,
                        width, height
                );
                cells.add(cell);
            }
        }

        var graph = NeighborUtils.rectangularGraphWrap(cells, row, column, indices);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Fish> generate(int n, String shape, List<Pair<Integer, Integer>> indices) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.5 ? WaTorModel.FISH :
                        x < 0.55 ? WaTorModel.SHARK : 2;
            }
        }

        if(shape.equals(ShapeUtils.RECTANGULAR)) return WaTor.generateRect(n, n, tmp,
                DEFAULT_FISHBREED_PERIOD, DEFAULT_SHARKBREED_PERIOD, DEFAULT_SHARKSTARVE_PERIOD,
                indices == null ? NeighborUtils.indicesFor8Rectangle() : indices);
        else if(shape.equals(ShapeUtils.TRIANGULAR)) return WaTor.generateTri(n, n, tmp,
                DEFAULT_FISHBREED_PERIOD, DEFAULT_SHARKBREED_PERIOD, DEFAULT_SHARKSTARVE_PERIOD,
                indices == null ? NeighborUtils.indicesFor12Triangle() : indices);
        else return null;
    }

    public static Simulator<Fish> generate(int n, String shape) { return generate(n, shape, null); }
}
