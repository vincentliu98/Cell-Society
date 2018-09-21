package xml.writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import simulation.Cell;
import simulation.CellGraph;
import simulation.models.SimulationModel;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
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
            // root elements
            Element rootElement = doc.createElement("data");
            rootElement.setAttribute("sim", sim.modelName());
            doc.appendChild(rootElement);

            graph.getCells().forEach(c -> rootElement.appendChild(parseCell(c)));
            parseModelParams().forEach(rootElement::appendChild);

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outFile);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace(); // Do some user-friendly things
        }
    }

    private Element parseCell(Cell<T> cell) {
        var parent = doc.createElement("cell");

        var id = doc.createElement("uniqueID");
        id.appendChild(doc.createTextNode(uniqueId.get(cell).toString()));
        var neighbors = doc.createElement("neighbors");
        neighbors.appendChild(doc.createTextNode(
                String.join(",", graph.getNeighbors(cell).stream().map(c ->
                        uniqueId.get(c).toString()).collect(Collectors.toList()))
        ));
        var cx = doc.createElement("x");
        cx.appendChild(doc.createTextNode(Double.toString(cell.cx())));
        var cy = doc.createElement("y");
        cy.appendChild(doc.createTextNode(Double.toString(cell.cy())));

        parent.appendChild(id);
        parent.appendChild(neighbors);
        parent.appendChild(cx);
        parent.appendChild(cy);
        parseCellStatus(cell).forEach(parent::appendChild);

        return parent;
    }

    protected abstract List<Element> parseCellStatus(Cell<T> cell);

    protected abstract List<Element> parseModelParams();
}
