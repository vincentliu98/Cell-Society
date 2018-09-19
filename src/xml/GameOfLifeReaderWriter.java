//package xml;
//
//import javafx.geometry.Point2D;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class GameOfLifeReaderWriter implements ReaderWriter {
//    public Map<String, Object> read(String filename) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        Map<Point2D, Integer> grid = new HashMap<Point2D, Integer>();
//        File file = new File(filename);
//        Scanner sc = null;
//        try {
//            sc = new Scanner(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String rowLine ="";
//        String colLine ="";
//        String valLine ="";
//        int row = 0;
//        int col = 0;
//        int val = 0;
//        String ints = "0123456789";
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            if (line.contains("<cell>")) {
//                rowLine = sc.nextLine();
//                colLine = sc.nextLine();
//                valLine = sc.nextLine();
//                for (char c: ints.toCharArray()) {
//                    if (rowLine.contains("" + c))
//                        row = Integer.parseInt("" + c);
//                    if (colLine.contains("" + c))
//                        col = Integer.parseInt("" + c);
//                    if (valLine.contains("" + c))
//                        val = Integer.parseInt("" + c);
//                    grid.put(new Point2D(row, col), val);
//                }
//            }
//        }
//        for (int r=0; r<10; r++) {
//            for (int c = 0; c < 10; c++) {
//                System.out.print(r);
//                System.out.print(c);
//                System.out.println(grid.get(new Point2D(r, c)));
//            }
//        }
//        double speed = 0.1;
//        params.put("grid", grid);
//        params.put("speed", speed);
//        return params;
//    }
//
//    public void write() {
//
//    }
//}
