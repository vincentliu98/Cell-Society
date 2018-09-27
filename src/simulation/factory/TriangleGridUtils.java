package simulation.factory;

import simulation.Cell;
import simulation.CellGraph;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;

public class TriangleGridUtils<T> {
    /**
     * This neighborhood finder assumes VERY SPECIFIC layout of the triangle cells
     * <br>
     * -------------------------        <br>
     *  0/1\2/3\4/5\6/7                 <br>
     *  ----------------------          <br>
     *  8\9/\/\/\/\/\/\/                <br>
     * -----------------------          <br>
     * @param cells
     * @param r number of rows within this graph
     * @param c number of cells within this row
     * @return
     */
    public CellGraph<T> graphWith3Neighbors(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>();

        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            var neighbor = new ArrayList<Cell<T>>();

            var offset = (cur.shapeCode() == ShapeUtils.TRIANGLE ? c : -c);

            if(0 < i%c) neighbor.add(cells.get(i-1));
            if(i%c+1 < c) neighbor.add(cells.get(i+1));
            if(0 <= i+offset && i+offset < cells.size()) neighbor.add(cells.get(i+offset));

            neighbors.put(cur, neighbor);
        } return neighbors;
    }

    public CellGraph<T> graphWith3NeighborsWithNoBoundary(List<Cell<T>> cells, int r, int c) {
        CellGraph<T> neighbors = new CellGraph<>();

        for(int i = 0 ; i < cells.size() ; i ++) {
            var cur = cells.get(i);
            var neighbor = new ArrayList<Cell<T>>();

            var offset = (cur.shapeCode() == ShapeUtils.TRIANGLE ? c : -c);

            if(0 < i%c) neighbor.add(cells.get(i-1));
            else neighbor.add(cells.get(i-1+c));
            if(i%c+1 < c) neighbor.add(cells.get(i+1));
            else neighbor.add(cells.get(i+1-c));
            if(0 <= i+offset && i+offset < cells.size()) neighbor.add(cells.get(i+offset));
            else neighbor.add(cells.get(i+offset < 0 ? i+offset+cells.size() : i+offset-cells.size()));

            neighbors.put(cur, neighbor);
        } return neighbors;
    }
}
