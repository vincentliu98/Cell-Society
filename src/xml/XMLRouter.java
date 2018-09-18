package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class XMLRouter {
    /*
     * Public void setParams(String filename, simulationModelPanel panel)
     * Read the desired simulation model from the file
     * Call the read method from the XMLReaderWriter for the simulation model, save the map that is returned in a local
     * variable
     * For each parameter name key in the Map, which , set the parameter in panel
     *
     */
    public static Map<String, Object> setParams(String filename, SimulationModelPanel panel) {
        ReaderWriter rw;
        File file = new File(filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        while (true) {
            line = sc.nextLine();
            if (line.contains("type"))
                break;
        }
        if (line.contains("Game of Life")){
             rw = new GameOfLifeReaderWriter();
             System.out.print(line);
             return rw.read(filename);
        }
        return null;
    }

    public static void callWriter(String modelName) {
    }

//    public static void main(String[] args) {
//        setParams("data/Game_of_Life_1.xml");
//    }
}
