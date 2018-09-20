package xml;

import javafx.geometry.Point2D;
import javafx.util.Pair;
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
    public static final List<String> DATA_FIELDS = List.of(
        "type",
        "cellMap",
        "speed"

    );
    //field names expected to appear within each cell, do not currently need to access this list
    public static final List<String> CELLMAP_SUBFIELDS = List.of(
            "row",
            "column",
            "value"
    );

    // specific data values for this instance
    private String myType;
    private Map<Pair, Integer> myCellMap;
    private double mySpeed;
    private Map<String, Object> myDataValues;


    /**
     * Create game data from given data.
     */
    public SimulationData(String type, Map<Pair, Integer> cellMap, double speed) {
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
    public SimulationData(Map<String, Object> dataValues) {
        this(((String) dataValues.get(DATA_FIELDS.get(0))),
                ((Map<Pair, Integer>) dataValues.get(DATA_FIELDS.get(1))),
                ((double) dataValues.get(DATA_FIELDS.get(2))));
        myDataValues = dataValues;
    }

    // provide getters, not setters
    public String getType () {
        return myType;
    }

    public Map<Pair, Integer> getCellMap () {
        return myCellMap;
    }

    public double getSpeed () {
        return mySpeed;
    }

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
