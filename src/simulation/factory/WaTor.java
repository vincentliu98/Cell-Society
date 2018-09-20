package simulation.factory;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;
import simulation.models.SimulationModel;
import simulation.models.WaTorModel;
import simulation.models.wator.Fish;
import simulation.models.wator.Shark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Convenience class to generate "Wa-Tor" Simulator
 *
 * @author Vincent Liu
 */

public class WaTor {
    public static Simulator<Fish> generate(int row, int column, int[][] initial) {
        var model = new WaTorModel(2, 5, 5);
        ArrayList<Cell<Fish>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(
                        initial[i][j] == WaTorModel.FISH ? new Fish() :
                         initial[i][j] == WaTorModel.SHARK ? new Shark() : null,
                        new Rectangle(j*width, i*height, width, height),
                        model);
                cell.view().setFill(model.chooseColor(cell.value()));
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Fish>().graphWith8Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Fish> generate() {
        return WaTor.generate(5, 5, new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1}
        });
    }
}
