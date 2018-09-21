package xml.writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.SimulationModel;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class XMLWriter<T> {
    public static final String DELIMITER = ",";
    public static final int SHAPE_RECTANGLE = 0;
    public static final int SHAPE_TRIANGLE = 1;

    protected SimulationModel<T> sim;
    private CellGraph<T> graph;
    private Map<Cell<T>, Integer> uniqueId;
    protected Document doc;
    private File outFile;

    public XMLWriter(SimulationModel<T> sim_, CellGraph<T> graph_, File outFile_) {
        sim = sim_; graph = graph_; outFile = outFile_;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace(); // Do some user-friendly things
        }
        uniqueId = new HashMap<>();
        var orderedCells = new ArrayList<>(graph.getCells());
        for(int i = 0 ; i < orderedCells.size() ; i ++) uniqueId.put(orderedCells.get(i), i);
    }

    public void generate() {
        try {
            Element rootElement = doc.createElement("data");
            rootElement.setAttribute("sim", sim.modelName());
            rootElement.setAttribute("shape", Integer.toString(SHAPE_RECTANGLE)); // TODO:
            rootElement.setAttribute("width", Double.toString(graph.getShapeWidth()));
            rootElement.setAttribute("height", Double.toString(graph.getShapeHeight()));
            doc.appendChild(rootElement);

            graph.getCells().forEach(c -> rootElement.appendChild(encodeCell(c)));
            parseModelParams().forEach(p -> rootElement.appendChild(p));

            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(doc), new StreamResult(outFile));
        } catch (TransformerException tfe) {
            tfe.printStackTrace(); // Do some user-friendly things
        }
    }

    private Element encodeCell(Cell<T> cell) {
        var parent = doc.createElement("cell");

        var id = doc.createElement("uniqueID");
        id.appendChild(doc.createTextNode(uniqueId.get(cell).toString()));
        var neighbors = doc.createElement("neighbors");
        neighbors.appendChild(doc.createTextNode(
                String.join(DELIMITER, graph.getNeighbors(cell).stream().map(c ->
                        uniqueId.get(c).toString()).collect(Collectors.toList()))
        ));
        var cx = doc.createElement("cx");
        cx.appendChild(doc.createTextNode(Double.toString(cell.cx())));
        var cy = doc.createElement("cy");
        cy.appendChild(doc.createTextNode(Double.toString(cell.cy())));

        parent.appendChild(id);
        parent.appendChild(neighbors);
        parent.appendChild(cx);
        parent.appendChild(cy);
        encodeCellValue(cell.value()).forEach(parent::appendChild);

        return parent;
    }

    protected abstract List<Element> encodeCellValue(T value);

    protected abstract List<Element> parseModelParams();
}
