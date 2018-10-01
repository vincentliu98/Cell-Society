package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *  An interface for <b>all</b> model-specific functions;
 *  When adding a model, the <b>first</b> thing to do is to
 *  implement this interface, before moving on to UI/XML parts
 *  that creates a Simulator/SimulationModel. What methods must
 *  do should be evident from the method name and its
 *  input/output type signatures.
 *
 * @param <T> Type of the cell's value for a model
 * @author Inchan Hwang
 */
public interface SimulationModel<T> {
    /**
     *  MODEL-SPECIFIC UPDATE RULES
     */
    int updatePriority(T myVal);
    void localUpdate(Cell<T> me, List<Cell<T>> neighbors);
    void globalUpdate(CellGraph<T> graph);
    void updateModelParams(Map<String, String> params);
    T nextValue(T myVal);

    /**
     *  MODEL-SPECIFIC UI CONFIGURATIONS
     */
    Color chooseColor(T myVal);

    /**
     *  MODEL-SPECIFIC DATA
     */
    String modelName();
    Map<String, Integer> getStatistics(List<T> values);
    XMLWriter<T> getXMLWriter(CellGraph<T> graph, File outFile, String language);

    T getValFromCode(int code);
    List<Integer> getCodes();
}
