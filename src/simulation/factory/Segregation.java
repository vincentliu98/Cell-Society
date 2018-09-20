package simulation.factory;

import javafx.scene.shape.Rectangle;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.SegregationModel;
import utility.IntegerPair;

import java.util.ArrayList;

/**
 *  Convenience class to generate "Segregation" CellGraph
 *  @author Inchan Hwang
 */
public class Segregation {
    public static CellGraph<IntegerPair> generate(
            int row, int column, int[][] initial, double tolerance
    ) {
        var model = new SegregationModel(tolerance);
        ArrayList<Cell<IntegerPair>> cells = new ArrayList<>();
        double width = CellGraph.SIMULATION_SX / column;
        double height = CellGraph.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(
                        new IntegerPair(SegregationModel.STAY, initial[i][j]),
                        new Rectangle(j * width, i * height, width, height)
                );
                cell.view().setFill(model.chooseColor(cell.value()));
                cells.add(cell);
            }
        }

        var neighbors = new SquareGridUtils<IntegerPair>().get8Neighbors(cells, row, column);
        return new CellGraph<>(cells, neighbors, model);
    }

    public static CellGraph<IntegerPair> generate() {
        return Segregation.generate(5, 5, new int[][]{
                {1, 2, 2, 2, 1},
                {2, 0, 0, 0, 2},
                {2, 0, 2, 0, 2},
                {2, 0, 0, 0, 2},
                {1, 2, 2, 2, 1}
        }, 0.2);
    }
}
