package xml;

import javafx.geometry.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameOfLifeReaderWriter implements ReaderWriter {

    public Map<String, Object> read(Scanner sc) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<Point2D, Integer> grid = new HashMap<Point2D, Integer>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.contains("<cell>")) {
                String rowLine = sc.nextLine().replaceAll("\\D","");
                String colLine = sc.nextLine().replaceAll("\\D","");
                String valLine = sc.nextLine().replaceAll("\\D","");
                int row = Integer.parseInt(rowLine);
                int col = Integer.parseInt(colLine);
                int val = Integer.parseInt(valLine);
                grid.put(new Point2D(row, col), val);
            }
        }
//        for (int r=0; r<10; r++) {
//            for (int c = 0; c < 10; c++) {
//                System.out.print(r);
//                System.out.print(c);
//                System.out.println(grid.get(new Point2D(r, c)));
//            }
//        }
        double speed = 0.1;
        params.put("grid", grid);
        params.put("speed", speed);
        return params;
    }

    public void write() {

    }
}
