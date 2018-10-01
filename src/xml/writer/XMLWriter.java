package xml.writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import simulation.Cell;
import simulation.CellGraph;
import xml.XMLException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  XMLWriter to write properties of a simulation that are common to
 *  all particular models. Subclasses must provide the XML Elements
 *  that are bound to specific models.
 *
 * @author Inchan Hwang
 */
public abstract class XMLWriter<T> {
    public static final String DEFAULT_RESOURCES = "Errors";
    public static final String DELIMITER = ",";

    private CellGraph<T> graph;
    private Map<Cell<T>, Integer> uniqueId;
    protected Document doc;
    private File outFile;
    private static ResourceBundle myResources;

    public XMLWriter(CellGraph<T> graph_, File outFile_, String language) {
        graph = graph_; outFile = outFile_;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCES + language);
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            throw new XMLException(myResources.getString("ConfigErrorMsg"));
        }
        uniqueId = new HashMap<>();
        var orderedCells = new ArrayList<>(graph.getCells());
        for(int i = 0 ; i < orderedCells.size() ; i ++) uniqueId.put(orderedCells.get(i), i);
    }

    /**
     *
     */
    public void generate() {
        try {
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            var modelName = doc.createElement("modelName");
            modelName.appendChild(doc.createTextNode(getModelName()));
            rootElement.appendChild(modelName);
            var shapeCode = doc.createElement("shapeCode");
            shapeCode.appendChild(doc.createTextNode(Integer.toString(graph.shapeCode())));
            rootElement.appendChild(shapeCode);

            graph.getCells().forEach(c -> rootElement.appendChild(encodeCell(c)));

            parseModelParams().forEach(p -> rootElement.appendChild(p));

            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(doc), new StreamResult(outFile));
        } catch (TransformerException tfe) {
            throw new XMLException(myResources.getString("TransformErrorMsg"));
        }
    }

    /**
     *
     * @param cell
     * @return
     */
    private Element encodeCell(Cell<T> cell) {
        var parent = doc.createElement("cell");

        var id = doc.createElement("uniqueID");
        id.appendChild(doc.createTextNode(uniqueId.get(cell).toString()));
        var neighbors = doc.createElement("neighbors");
        neighbors.appendChild(doc.createTextNode(
                String.join(DELIMITER, graph.getNeighbors(cell).stream().map(c ->
                        uniqueId.get(c).toString()).collect(Collectors.toList()))
        ));

        var shapeCode = doc.createElement("shapeCode");
        shapeCode.appendChild(doc.createTextNode(Integer.toString(cell.shapeCode())));
        var width = doc.createElement("shapeWidth");
        width.appendChild(doc.createTextNode(Double.toString(cell.width())));
        var height = doc.createElement("shapeHeight");
        height.appendChild(doc.createTextNode(Double.toString(cell.height())));
        var cx = doc.createElement("cx");
        cx.appendChild(doc.createTextNode(Double.toString(cell.cx())));
        var cy = doc.createElement("cy");
        cy.appendChild(doc.createTextNode(Double.toString(cell.cy())));

        parent.appendChild(id);
        parent.appendChild(neighbors);
        parent.appendChild(shapeCode);
        parent.appendChild(cx);
        parent.appendChild(cy);
        parent.appendChild(width);
        parent.appendChild(height);

        encodeCellValue(cell.value()).forEach(parent::appendChild);

        return parent;
    }

    protected abstract String getModelName();

    /**
     * Encodes model-specific values inside a cell into XML format.
     */
    protected abstract List<Element> encodeCellValue(T value);

    /**
     * Encodes model-specific parameters into XML format.
     */
    protected abstract List<Element> parseModelParams();
}
