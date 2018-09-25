package simulation.factory;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import simulation.Cell;
import simulation.CellGraph;
import simulation.Simulator;

import java.util.ArrayList;
import java.util.List;

/**
 *  Convenience class to generate "Segregation" CellGraph
 *  @author Inchan Hwang
 */
public class SquareGridUtils<T> {
    private Pair<Integer, Integer> lineToGrid(int x, int c) { return new Pair<>(x%c, x/c); }
    private int gridToLine(int x, int y, int c) { return y * c + x; }

    /**
     *
     * @param cells
     * @param r
     * @param c
     * @return
     */
    public CellGraph<T> graphWith8Neighbors(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>(
                new Rectangle(Simulator.SIMULATION_SX/c, Simulator.SIMULATION_SY/r));

        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            var neighbor = new ArrayList<Cell<T>>();
            var p = lineToGrid(i, c);
            var x = p.getKey();
            var y = p.getValue();

            if(x-1 >= 0) neighbor.add(cells.get(gridToLine(x-1, y, c)));
            if(x+1 < c) neighbor.add(cells.get(gridToLine(x+1, y, c)));
            if(y-1 >= 0) neighbor.add(cells.get(gridToLine(x, y-1, c)));
            if(y+1 < r) neighbor.add(cells.get(gridToLine(x, y+1, c)));
            if(x-1 >= 0 && y-1 >= 0) neighbor.add(cells.get(gridToLine(x-1, y-1, c)));
            if(x+1 < c && y-1 >= 0) neighbor.add(cells.get(gridToLine(x+1, y-1, c)));
            if(x-1 >= 0 && y+1 < r) neighbor.add(cells.get(gridToLine(x-1, y+1, c)));
            if(x+1 < c && y+1 < r) neighbor.add(cells.get(gridToLine(x+1, y+1, c)));

            neighbors.put(cur, neighbor);
        } return neighbors;
    }

    /**
     *
     * @param cells
     * @param r
     * @param c
     * @return
     */
    public CellGraph<T> graphWith4Neighbors(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>(
                new Rectangle(Simulator.SIMULATION_SX/c, Simulator.SIMULATION_SY/r));
        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            var neighbor = new ArrayList<Cell<T>>();
            var p = lineToGrid(i, c);
            var x = p.getKey();
            var y = p.getValue();

            if(x-1 >= 0) neighbor.add(cells.get(gridToLine(x-1, y, c)));
            if(x+1 < c) neighbor.add(cells.get(gridToLine(x+1, y, c)));
            if(y-1 >= 0) neighbor.add(cells.get(gridToLine(x, y-1, c)));
            if(y+1 < r) neighbor.add(cells.get(gridToLine(x, y+1, c)));

            neighbors.put(cur, neighbor);
        } return neighbors;
    }

    /**
     *
     * @param cells
     * @param r
     * @param c
     * @return
     */
    public CellGraph<T> graphWith8NeighborsNoBoundary(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>(
                new Rectangle(Simulator.SIMULATION_SX/c, Simulator.SIMULATION_SY/r));

        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            var neighbor = new ArrayList<Cell<T>>();
            var p = lineToGrid(i, c);
            var x = p.getKey();
            var y = p.getValue();

            neighbor.add(cells.get(gridToLine((x-1+c)%c, y, c)));
            neighbor.add(cells.get(gridToLine((x+1+c)%c, y, c)));
            neighbor.add(cells.get(gridToLine(x, (y-1+r)%r, c)));
            neighbor.add(cells.get(gridToLine(x, (y+1+r)%r, c)));
            neighbor.add(cells.get(gridToLine((x-1+c)%c, (y-1+r)%r, c)));
            neighbor.add(cells.get(gridToLine((x+1+c)%c, (y-1+r)%r, c)));
            neighbor.add(cells.get(gridToLine((x-1+c)%c, (y+1+r)%r, c)));
            neighbor.add(cells.get(gridToLine((x+1+c)%c, (y+1+r)%r, c)));

            neighbors.put(cur, neighbor);
        } return neighbors;
    }
}
