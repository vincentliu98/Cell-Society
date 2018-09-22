package simulation.models;

import javafx.scene.paint.Color;
import simulation.Cell;
import simulation.CellGraph;
import xml.writer.XMLWriter;

import java.io.File;
import java.util.List;

/**
 *  SimulationModels
 *
 * @param <T> Type of the cell's value for a model
 * @author Inchan Hwang
 */
public interface SimulationModel<T> {
    int getPriority(T myVal);
    void localUpdate(Cell<T> me, List<Cell<T>> neighbors);
    void globalUpdate(CellGraph<T> graph);

    T nextValue(T myVal);
    Color chooseColor(T myVal);
    String modelName();

    XMLWriter<T> getXMLWriter(CellGraph<T> graph, File outFile);
}
