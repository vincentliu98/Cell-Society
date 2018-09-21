package xml;

import javafx.util.Pair;

import java.util.ArrayList;
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
    public static final String DATA_TYPE = "Game Of Life";

    // field names expected to appear in data file holding values for this object
    public static final List<String> DATA_FIELDS = List.of(
        "type",
        "cellArrayList"

    );
    //field names expected to appear within each cell
    public static final List<String> CELL_SUBFIELDS = List.of(
            "uniqueID",
            "neighbors",
            "x",
            "y",
            "values"
    );

    public static final List<String> VALUE_SUBFIELDS = List.of(
            "isAlive"
    );

    // specific data values for this instance
    private String myType;
    private ArrayList<ArrayList<Integer>> myCellArrayList;
    private Map<String, Object> myDataValues;


    /**
     * Create game data from given data.
     */
    public SimulationData(String type, ArrayList<ArrayList<Integer>> cellArrayList) {
        myType = type;
        myCellArrayList = cellArrayList;
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
                ((ArrayList<ArrayList<Integer>>) dataValues.get(DATA_FIELDS.get(1))));
        myDataValues = dataValues;
    }

    // provide getters, not setters
    public String getType () {
        return myType;
    }

    public ArrayList<ArrayList<Integer>> getMyCellArrayList() {
        return myCellArrayList;
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
