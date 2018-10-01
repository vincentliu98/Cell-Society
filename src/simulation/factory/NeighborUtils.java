package simulation.factory;

import javafx.util.Pair;
import simulation.Cell;
import simulation.CellGraph;
import utility.ShapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NeighborUtils {
    private static <T> List<Cell<T>> findNeighbor(
            List<Cell<T>> cells, int cellIdx,
            List<Pair<Integer, Integer>> neighborIndices,
            int r, int c, boolean wrapCol, boolean wrapRow
    ) {
        var neighbor = new ArrayList<Cell<T>>();
        int curRow = cellIdx/c;

        for(var idx: neighborIndices) {
            int colOffset = idx.getValue();
            int rowOffset = idx.getKey();
            if(colOffset == 0 && rowOffset == 0) continue; // if it points to itself, ignore

            int next = cellIdx + c*rowOffset + colOffset;
            int nextRow = next/c;
            int nextCol = (next+c)%c;

            if(curRow + rowOffset != nextRow) { // column overflow
                if(wrapCol) nextRow = curRow + rowOffset;
                else continue; // if not, ignore this
            }

            if(!(0 <= nextRow && nextRow < r)) { // row overflow
                if(wrapRow) nextRow = (nextRow+r)%r;
                else continue; // if not, ignore this
            }

            neighbor.add(cells.get(nextRow*c+nextCol));
        }
        return neighbor;
    }

    public static <T> CellGraph<T> triangularGraph(List<Cell<T>> cells,
                                                   int r, int c, List<Pair<Integer, Integer>> indices) {
        var graph = new CellGraph<T>();

        var indicesFlipped =
                indices .stream()
                        .map(idx -> new Pair<>(-idx.getKey(), idx.getValue()))
                        .collect(Collectors.toList());

        for(int i = 0 ; i < cells.size() ; i ++) {
            var isUpright = cells.get(i).shapeCode() == ShapeUtils.TRIANGLE;
            graph.put(cells.get(i),
                    NeighborUtils.findNeighbor(cells, i,
                            isUpright ? indices : indicesFlipped, r, c, false, false));
        } return graph;
    }

    public static <T> CellGraph<T> triangularGraphWrap(List<Cell<T>> cells,
                                                   int r, int c, List<Pair<Integer, Integer>> indices) {
        var graph = new CellGraph<T>();

        var indicesFlipped =
                indices .stream()
                        .map(idx -> new Pair<>(-idx.getKey(), idx.getValue()))
                        .collect(Collectors.toList());

        for(int i = 0 ; i < cells.size() ; i ++) {
            var isUpright = cells.get(i).shapeCode() == ShapeUtils.TRIANGLE;
            graph.put(cells.get(i),
                    NeighborUtils.findNeighbor(cells, i,
                            isUpright ? indices : indicesFlipped, r, c, true, true));
        } return graph;
    }

    public static <T> CellGraph<T> rectangularGraph(List<Cell<T>> cells,
                                                   int r, int c, List<Pair<Integer, Integer>> indices) {
        var graph = new CellGraph<T>();
        for(int i = 0 ; i < cells.size() ; i ++) {
            graph.put(cells.get(i),
                    NeighborUtils.findNeighbor(cells, i, indices, r, c, false, false));
        } return graph;
    }

    public static <T> CellGraph<T> rectangularGraphWrap(List<Cell<T>> cells,
                                                    int r, int c, List<Pair<Integer, Integer>> indices) {
        var graph = new CellGraph<T>();
        for(int i = 0 ; i < cells.size() ; i ++) {
            graph.put(cells.get(i),
                    NeighborUtils.findNeighbor(cells, i, indices, r, c, true, true));
        } return graph;
    }

    public static List<Pair<Integer, Integer>> indicesFor12Triangle() {
        var indicesUpright = new ArrayList<Pair<Integer, Integer>>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -2; j <= 2; j++) {
                if (i == -1 && (j == -2 || j == 2)) continue;
                indicesUpright.add(new Pair<>(i, j));
            }
        } return indicesUpright;
    }

    public static List<Pair<Integer, Integer>> indicesFor8Rectangle() {
        var indices = new ArrayList<Pair<Integer, Integer>>();
        for(int i = -1 ; i <= 1 ; i ++) {
            for(int j = -1 ; j <= 1 ; j ++) {
                indices.add(new Pair<>(i, j));
            }
        } return indices;
    }

    public static List<Pair<Integer, Integer>> indicesFor4Rectangle() {
        var indices = new ArrayList<Pair<Integer, Integer>>();
        for(int i = -1 ; i <= 1 ; i ++) {
            for(int j = -1 ; j <= 1 ; j ++) {
                if(Math.abs(i)+Math.abs(j) <= 1) indices.add(new Pair<>(i, j));
            }
        } return indices;
    }
}
