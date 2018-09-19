package xml;

import javafx.geometry.Point2D;
import simulation.Cell;
import simulation.CellGraph;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Simple immutable value object representing: .
 *
 * @author Robert C. Duvall
 */
public class SimulationData {
    // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "Simulation";
    // field names expected to appear in data file holding values for this object
    // NOTE: simple way to create an immutable list
    public static final List<String> DATA_FIELDS = List.of(
        "type",
        "cellMap",
        "speed"

    );

    // specific data values for this instance
    private String myType;
    private String myCellMap;
    private String mySpeed;
    private Map<String, String> myDataValues;


    /**
     * Create game data from given data.
     */
    public SimulationData(String type, String cellMap, String speed) {
        myType = type;
        myCellMap = cellMap;
        mySpeed = speed;
        // NOTE: this is useful so our code does not fail due to a NullPointerException
        myDataValues = new HashMap<>();
    }

    /**
     * Create game data from a data structure of Strings.
     *
     * @param dataValues map of field names to their values
     */
    public SimulationData(Map<String, String> dataValues) {
        this(dataValues.get(DATA_FIELDS.get(0)),
                dataValues.get(DATA_FIELDS.get(1)),
                dataValues.get(DATA_FIELDS.get(2)));
        myDataValues = dataValues;
    }

    // provide getters, not setters
    public String getType () {
        return myType;
    }

//    public Map<Point2D, Integer> getCellMap () {
//        return myCellMap;
//    }
//
//    public double getSpeed () {
//        return mySpeed;
//    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString () {
        var result = new StringBuilder();
        result.append(DATA_TYPE + " {\n");
        for (var e : myDataValues.entrySet()) {
            result.append("  ").append(e.getKey()).append("='").append(e.getValue()).append("',\n");
        }
        result.append("}\n");
        return result.toString();
    }
}
