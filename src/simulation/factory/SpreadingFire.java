package simulation.factory;

import javafx.scene.shape.Rectangle;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.SimulationModel;
import simulation.models.SpreadingFireModel;

import java.util.ArrayList;

/**
 *  Convenience class to generate "Spreading of Fire" CellGraph
 *
 * @author Vincent Liu
 */

public class SpreadingFire {
    public static CellGraph<Integer> generate(
            int row, int column, int[][] initial
    ) {
        SimulationModel<Integer> model = new SpreadingFireModel();
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = CellGraph.SIMULATION_SX / column;
        double height = CellGraph.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], new Rectangle(j*width, i*height, width, height));
                cell.view().setFill(model.chooseColor(cell.value()));
                cells.add(cell);
            }
        }

        var neighbors = new SquareGridUtils<Integer>().get4Neighbors(cells, row, column);
        return new CellGraph<>(cells, neighbors, model);
    }

    public static CellGraph<Integer> generate() {
        return SpreadingFire.generate(5, 5, new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 2, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        });
    }
}
