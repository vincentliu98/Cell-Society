package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Convenience class to generateRect "Wa-Tor" Simulator
 *
 * @author Inchan Hwang
 */

public class WaTor {
    public static final int DEFAULT_FISHBREEDPERIOD = 4;
    public static final int DEFAULT_SHARKBREEDPERIOD = 5;
    public static final int DEFAULT_SHARKSTARVEPERIOD = 5;

    public static Simulator<Fish> generateTri(int row, int column, int[][] initial, int fishBreedPeriod,
                                               int sharkBreedPeriod, int sharkStarvePeriod) {
        var model = new WaTorModel(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
        ArrayList<Cell<Fish>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / ((column+1)/2);
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j] == WaTorModel.FISH ? new Fish() :
                        initial[i][j] == WaTorModel.SHARK ? new Shark() : null;
                var cell = new Cell<>(value, (i+j)%2==0 ? ShapeUtils.TRIANGLE : ShapeUtils.TRIANGLE_FLIP,
                        (0.5*j)*width, (i+0.5)*height,
                        width, height
                );
                cells.add(cell);
            }
        }

        var graph = new TriangleGridUtils<Fish>().graphWith3Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }
    /**
     *
     * @param row
     * @param column
     * @param initial
     * @param fishBreedPeriod
     * @param sharkBreedPeriod
     * @param sharkStarvePeriod
     * @return
     */
    public static Simulator<Fish> generateRect(int row, int column, int[][] initial, int fishBreedPeriod,
                                               int sharkBreedPeriod, int sharkStarvePeriod) {
        var model = new WaTorModel(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
        ArrayList<Cell<Fish>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j] == WaTorModel.FISH ? new Fish() :
                         initial[i][j] == WaTorModel.SHARK ? new Shark() : null;
                var cell = new Cell<>(value, ShapeUtils.RECTANGLE,
                        (j+0.5)*width, (i+0.5)*height,
                        width, height
                );
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Fish>().graphWith8NeighborsNoBoundary(cells, row, column);
        return new Simulator<>(graph, model);
    }

    /**
     *
     * @param n
     * @return
     */
    public static Simulator<Fish> generate(int n, String shape) {
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
                DEFAULT_FISHBREEDPERIOD, DEFAULT_SHARKBREEDPERIOD,DEFAULT_SHARKSTARVEPERIOD);
        else if(shape.equals(ShapeUtils.TRIANGULAR)) return WaTor.generateTri(n, n, tmp,
                DEFAULT_FISHBREEDPERIOD, DEFAULT_SHARKBREEDPERIOD,DEFAULT_SHARKSTARVEPERIOD);
        return null;
    }
}
