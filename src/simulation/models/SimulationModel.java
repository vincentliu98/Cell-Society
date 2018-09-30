package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *  An interface for all simulation models.
 *  It has the methods to update individual or all value of cells and change the color accordingly.
 *  It is also changes the parameters of the model when the parameters from UI change.
 *  When saving XML file, SimulationModel returns a specific XMLWriter for the model.
 *
 * @param <T> Type of the cell's value for a model
 * @author Inchan Hwang
 */
public interface SimulationModel<T> {
    /**
     *
     * @param myVal
     * @return
     */
    int getPriority(T myVal);

    /**
     *
     * @param me
     * @param neighbors
     */
    void localUpdate(Cell<T> me, List<Cell<T>> neighbors);

    /**
     *
     * @param graph
     */
    void globalUpdate(CellGraph<T> graph);

    /**
     *
     * @param myVal
     * @return
     */
    T nextValue(T myVal);

    /**
     *
     * @param myVal
     * @return
     */
    Color chooseColor(T myVal);

    /**
     *
     * @return
     */
    String modelName();

    Map<String, Integer> getStatistics(List<T> values);

    /**
     *
     * @param graph
     * @param outFile
     * @return
     */
    XMLWriter<T> getXMLWriter(CellGraph<T> graph, File outFile, String language);

    /**
     *
     * @param params
     */
    void updateParams(Map<String, String> params);

    T getValFromCode(int code);

    List<Integer> getCodes();
}
