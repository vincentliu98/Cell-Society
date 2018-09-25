package simulation.factory;

import simulation.Cell;
import simulation.Simulator;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Convenience class to generate "Wa-Tor" Simulator
 *
 * @author Inchan Hwang
 */

public class WaTor {
    public static final int DEFAULT_FISHBREEDPERIOD = 4;
    public static final int DEFAULT_SHARKBREEDPERIOD = 5;
    public static final int DEFAULT_SHARKSTARVEPERIOD = 5;

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
    public static Simulator<Fish> generate(int row, int column, int[][] initial, int fishBreedPeriod,
                                           int sharkBreedPeriod, int sharkStarvePeriod) {
        var model = new WaTorModel(fishBreedPeriod, sharkBreedPeriod, sharkStarvePeriod);
        ArrayList<Cell<Fish>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var value = initial[i][j] == WaTorModel.FISH ? new Fish() :
                         initial[i][j] == WaTorModel.SHARK ? new Shark() : null;
                var cell = new Cell<>(value, (j+0.5)*width, (i+0.5)*height);
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
    public static Simulator<Fish> generate(int n) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextDouble();
                tmp[i][j] = x < 0.5 ? WaTorModel.FISH :
                        x < 0.55 ? WaTorModel.SHARK : 2;
            }
        }
        return WaTor.generate(n, n, tmp,
                DEFAULT_FISHBREEDPERIOD, DEFAULT_SHARKBREEDPERIOD,DEFAULT_SHARKSTARVEPERIOD);
    }
}
