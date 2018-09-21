package simulation.factory;

import simulation.Cell;
import simulation.CellGraph;
import utility.IntegerPair;

import java.util.ArrayList;
import java.util.List;

/**
 *  Convenience class to generate "Segregation" CellGraph
 *  @author Inchan Hwang
 */
public class SquareGridUtils<T> {
    private IntegerPair lineToGrid(int x, int c) { return new IntegerPair(x%c, x/c); }
    private int gridToLine(int x, int y, int c) { return y * c + x; }

    public CellGraph<T> graphWith8Neighbors(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>();
        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            neighbors.put(cur, new ArrayList<>());
            var p = lineToGrid(i, c);
            var x = p.getKey();
            var y = p.getValue();

            if(x-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x-1, y, c)));
            if(x+1 < c) neighbors.get(cur).add(cells.get(gridToLine(x+1, y, c)));
            if(y-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x, y-1, c)));
            if(y+1 < r) neighbors.get(cur).add(cells.get(gridToLine(x, y+1, c)));
            if(x-1 >= 0 && y-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x-1, y-1, c)));
            if(x+1 < c && y-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x+1, y-1, c)));
            if(x-1 >= 0 && y+1 < r) neighbors.get(cur).add(cells.get(gridToLine(x-1, y+1, c)));
            if(x+1 < c && y+1 < r) neighbors.get(cur).add(cells.get(gridToLine(x+1, y+1, c)));
        } return neighbors;
    }

    public CellGraph<T> graphWith4Neighbors(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>();
        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            neighbors.put(cur, new ArrayList<>());
            var p = lineToGrid(i, c);
            var x = p.getKey();
            var y = p.getValue();

            if(x-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x-1, y, c)));
            if(x+1 < c) neighbors.get(cur).add(cells.get(gridToLine(x+1, y, c)));
            if(y-1 >= 0) neighbors.get(cur).add(cells.get(gridToLine(x, y-1, c)));
            if(y+1 < r) neighbors.get(cur).add(cells.get(gridToLine(x, y+1, c)));
        } return neighbors;
    }

    public CellGraph<T> graphWith8NeighborsNoBoundary(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>();
        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            neighbors.put(cur, new ArrayList<>());
            var p = lineToGrid(i, c);
            var x = p.getKey();
            var y = p.getValue();

            neighbors.get(cur).add(cells.get(gridToLine((x-1+c)%c, y, c)));
            neighbors.get(cur).add(cells.get(gridToLine((x+1+c)%c, y, c)));
            neighbors.get(cur).add(cells.get(gridToLine(x, (y-1+r)%r, c)));
            neighbors.get(cur).add(cells.get(gridToLine(x, (y+1+r)%r, c)));
            neighbors.get(cur).add(cells.get(gridToLine((x-1+c)%c, (y-1+r)%r, c)));
            neighbors.get(cur).add(cells.get(gridToLine((x+1+c)%c, (y-1+r)%r, c)));
            neighbors.get(cur).add(cells.get(gridToLine((x-1+c)%c, (y+1+r)%r, c)));
            neighbors.get(cur).add(cells.get(gridToLine((x+1+c)%c, (y+1+r)%r, c)));
        } return neighbors;
    }
}
