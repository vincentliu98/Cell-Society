package app;

import simulation.CellGraph;
import simulation.factory.GameOfLife;

public class TestApp {
    public static void main(String[] args) {
        int row = 5;
        int column = 5;
        int initial[][] = {
                {1, 0, 0, 1, 1},
                {1, 1, 1, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0}
        };

        CellGraph<Integer> cg = GameOfLife.generate(row, column, initial);


        var list = cg.getCells();
        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                System.out.print(list.get(i*column+j).value() + " ");
            } System.out.println();
        }

        System.out.println();
        cg.tick();

        for(int i = 0 ; i < row ; i ++) {
            for(int j = 0 ; j < column ; j ++) {
                System.out.print(list.get(i*column+j).value() + " ");
            } System.out.println();
        }
    }
}
