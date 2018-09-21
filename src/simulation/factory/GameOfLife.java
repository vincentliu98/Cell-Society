package simulation.factory;

import javafx.scene.shape.Rectangle;
import simulation.Cell;
import simulation.Simulator;
import simulation.models.GameOfLifeModel;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Convenience class to generate "Game Of Life" CellGraph
 *  @author Inchan Hwang
 */
public class GameOfLife {
    public static Simulator<Integer> generate(int row, int column, int[][] initial) {
        var model = new GameOfLifeModel();
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = Simulator.SIMULATION_SX / column;
        double height = Simulator.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], new Rectangle(j*width, i*height, width, height), model);
                cell.view().setFill(model.chooseColor(cell.value()));
                cells.add(cell);
            }
        }

        var graph = new SquareGridUtils<Integer>().graphWith8Neighbors(cells, row, column);
        return new Simulator<>(graph, model);
    }

    public static Simulator<Integer> generate(int n) {
        var rng = new Random();
        int tmp[][] = new int[n][n];
        for(int i = 0 ; i < n ; i ++) {
            for(int j = 0 ; j < n ; j ++ ) {
                var x = rng.nextInt();
                tmp[i][j] = x < 0.5 ? 0 : 1;
            }
        }
        return GameOfLife.generate(n, n, tmp);
    }
}
