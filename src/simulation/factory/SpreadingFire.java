package simulation.factory;

        import javafx.scene.shape.Rectangle;
        import javafx.util.Pair;
        import simulation.Cell;
        import simulation.CellGraph;
        import simulation.rules.GameOfLifeRule;
        import simulation.rules.SpreadingFireRule;
        import simulation.rules.UpdateRule;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

/**
 *  Convenience class to generate "Spreading of Fire" CellGraph
 *
 * @author Vincent Liu
 */

public class SpreadingFire {
    private static Pair<Integer, Integer> lineToGrid(int x, int c) { return new Pair<>(x%c, x/c); }
    private static int gridToLine(int x, int y, int c) { return y * c + x; }

    public static CellGraph<Integer> generate(
            int row, int column, int[][] initial
    ) {
        UpdateRule<Integer> rule = new SpreadingFireRule();
        ArrayList<Cell<Integer>> cells = new ArrayList<>();
        double width = CellGraph.SIMULATION_SX / column;
        double height = CellGraph.SIMULATION_SY / row;

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                var cell = new Cell<>(initial[i][j], new Rectangle(j*width, i*height, width, height));
                cell.view().setFill(rule.chooseColor(cell.value()));
                cells.add(cell);
            }
        }

        Map<Cell<Integer>, ArrayList<Cell<Integer>>> neighbors = new HashMap<>();
        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            neighbors.put(cur, new ArrayList<>());
            var p = lineToGrid(i, column);
            var x = p.getKey();
            var y = p.getValue();

            // only check 4 neighbors
            if(x-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x-1, y, column)));
            if(x+1 < column) neighbors.get(cur).add(cells.get(gridToLine(x+1, y, column)));
            if(y-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x, y-1, column)));
            if(y+1 < row) neighbors.get(cur).add(cells.get(gridToLine(x, y+1, column)));
        }

        return new CellGraph<>(cells, neighbors, rule);
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
